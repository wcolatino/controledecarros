package com.westside.controledecarros.service;

import com.westside.controledecarros.dto.request.ProprietarioRequest;
import com.westside.controledecarros.dto.response.ProprietarioResponse;
import com.westside.controledecarros.entity.Proprietario;
import com.westside.controledecarros.mapper.ProprietarioMapper;
import com.westside.controledecarros.repository.ProprietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    public ProprietarioResponse cadastrar(ProprietarioRequest request) {
        boolean jaExiste = proprietarioRepository
                .findByUnidadeAndBloco(request.getUnidade(), request.getBloco())
                .isPresent();

        if (jaExiste) {
            throw new IllegalArgumentException("Já existe um proprietário cadastrado nesta unidade e bloco.");
        }

        Proprietario proprietario = ProprietarioMapper.toEntity(request);
        return ProprietarioMapper.toResponse(proprietarioRepository.save(proprietario));
    }

    public List<ProprietarioResponse> listarTodos() {
        return proprietarioRepository.findAll()
                .stream()
                .map(ProprietarioMapper::toResponse)
                .toList();
    }

    public ProprietarioResponse buscarPorId(Long id) {
        return ProprietarioMapper.toResponse(buscarEntidadePorId(id));
    }

    public ProprietarioResponse atualizar(Long id, ProprietarioRequest request) {
        Proprietario existente = buscarEntidadePorId(id);
        existente.setNome(request.getNome());
        existente.setUnidade(request.getUnidade());
        existente.setBloco(request.getBloco());
        existente.setTelefone(request.getTelefone());
        return ProprietarioMapper.toResponse(proprietarioRepository.save(existente));
    }

    public void deletar(Long id) {
        buscarEntidadePorId(id);
        proprietarioRepository.deleteById(id);
    }

    // Metodo interno — retorna a entidade para uso em outras services (ex: VeiculoService)
    public Proprietario buscarEntidadePorId(Long id) {
        return proprietarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado."));
    }
}