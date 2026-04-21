package com.westside.controledecarros.service;


import com.westside.controledecarros.dto.request.ProprietarioRequest;
import com.westside.controledecarros.dto.response.ProprietarioResponse;
import com.westside.controledecarros.entity.Proprietario;
import com.westside.controledecarros.enums.Acao;
import com.westside.controledecarros.mapper.ProprietarioMapper;
import com.westside.controledecarros.repository.ProprietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;
    private final LogService logService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public ProprietarioResponse cadastrar(ProprietarioRequest request) {
        boolean jaExiste = proprietarioRepository
                .findByUnidadeAndBloco(request.getUnidade(), request.getBloco())
                .isPresent();

        if (jaExiste) {
            throw new IllegalArgumentException("Já existe um proprietário cadastrado nesta unidade e bloco.");
        }

        Proprietario proprietario = ProprietarioMapper.toEntity(request);
        Proprietario salvo = proprietarioRepository.save(proprietario);

        // Registra log de criação com os dados do novo proprietário
        logService.registrar(Acao.CREATE, "Proprietario", salvo.getId(),
                objectMapper.writeValueAsString(ProprietarioMapper.toResponse(salvo)));

        return ProprietarioMapper.toResponse(salvo);
    }

    @SneakyThrows
    public List<ProprietarioResponse> listarTodos() {
        List<Proprietario> proprietarios = proprietarioRepository.findAll();

        // Registra log de consulta geral
        logService.registrar(Acao.READ, "Proprietario", 0L,
                "Listagem de todos os proprietários. Total: " + proprietarios.size());

        return proprietarios.stream()
                .map(ProprietarioMapper::toResponse)
                .toList();
    }

    @SneakyThrows
    public ProprietarioResponse buscarPorId(Long id) {
        Proprietario proprietario = buscarEntidadePorId(id);

        // Registra log de consulta por ID
        logService.registrar(Acao.READ, "Proprietario", id,
                objectMapper.writeValueAsString(ProprietarioMapper.toResponse(proprietario)));

        return ProprietarioMapper.toResponse(proprietario);
    }

    @SneakyThrows
    public ProprietarioResponse atualizar(Long id, ProprietarioRequest request) {
        Proprietario existente = buscarEntidadePorId(id);

        // Captura o estado anterior para o log
        String antes = objectMapper.writeValueAsString(ProprietarioMapper.toResponse(existente));

        existente.setNome(request.getNome());
        existente.setUnidade(request.getUnidade());
        existente.setBloco(request.getBloco());
        existente.setTelefone(request.getTelefone());

        Proprietario atualizado = proprietarioRepository.save(existente);

        // Registra log de atualização com antes e depois
        String depois = objectMapper.writeValueAsString(ProprietarioMapper.toResponse(atualizado));
        logService.registrar(Acao.UPDATE, "Proprietario", id,
                "{\"antes\": " + antes + ", \"depois\": " + depois + "}");

        return ProprietarioMapper.toResponse(atualizado);
    }

    @SneakyThrows
    public void deletar(Long id) {
        Proprietario existente = buscarEntidadePorId(id);

        // Captura os dados antes de deletar para o log
        String dados = objectMapper.writeValueAsString(ProprietarioMapper.toResponse(existente));
        logService.registrar(Acao.DELETE, "Proprietario", id, dados);

        proprietarioRepository.deleteById(id);
    }

    // Método interno — retorna a entidade para uso em outras services (ex: VeiculoService)
    public Proprietario buscarEntidadePorId(Long id) {
        return proprietarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado."));
    }
}