package com.westside.controledecarros.service;


import com.westside.controledecarros.dto.request.VeiculoRequest;
import com.westside.controledecarros.dto.response.VeiculoResponse;
import com.westside.controledecarros.entity.Proprietario;
import com.westside.controledecarros.entity.Veiculo;
import com.westside.controledecarros.enums.Acao;
import com.westside.controledecarros.enums.StatusVeiculo;
import com.westside.controledecarros.mapper.VeiculoMapper;
import com.westside.controledecarros.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ProprietarioService proprietarioService;
    private final LogService logService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public VeiculoResponse cadastrar(Long proprietarioId, VeiculoRequest request) {
        veiculoRepository.findByPlaca(request.getPlaca()).ifPresent(v -> {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com esta placa.");
        });

        Proprietario proprietario = proprietarioService.buscarEntidadePorId(proprietarioId);
        Veiculo veiculo = VeiculoMapper.toEntity(request);
        veiculo.setProprietario(proprietario);

        Veiculo salvo = veiculoRepository.save(veiculo);

        // Registra log de criação com os dados do novo veículo
        logService.registrar(Acao.CREATE, "Veiculo", salvo.getId(),
                objectMapper.writeValueAsString(VeiculoMapper.toResponse(salvo)));

        return VeiculoMapper.toResponse(salvo);
    }

    @SneakyThrows
    public List<VeiculoResponse> listarTodos() {
        List<Veiculo> veiculos = veiculoRepository.findAll();

        // Registra log de consulta geral
        logService.registrar(Acao.READ, "Veiculo", 0L,
                "Listagem de todos os veículos. Total: " + veiculos.size());

        return veiculos.stream()
                .map(VeiculoMapper::toResponse)
                .toList();
    }

    @SneakyThrows
    public VeiculoResponse buscarPorId(Long id) {
        Veiculo veiculo = buscarEntidadePorId(id);

        // Registra log de consulta por ID
        logService.registrar(Acao.READ, "Veiculo", id,
                objectMapper.writeValueAsString(VeiculoMapper.toResponse(veiculo)));

        return VeiculoMapper.toResponse(veiculo);
    }

    @SneakyThrows
    public VeiculoResponse buscarPorPlaca(String placa) {
        Veiculo veiculo = veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));

        // Registra log de consulta por placa
        logService.registrar(Acao.READ, "Veiculo", veiculo.getId(),
                objectMapper.writeValueAsString(VeiculoMapper.toResponse(veiculo)));

        return VeiculoMapper.toResponse(veiculo);
    }

    @SneakyThrows
    public List<VeiculoResponse> listarPorProprietario(Long proprietarioId) {
        List<Veiculo> veiculos = veiculoRepository.findByProprietarioId(proprietarioId);

        // Registra log de consulta por proprietário
        logService.registrar(Acao.READ, "Veiculo", proprietarioId,
                "Consulta de veículos do proprietário ID: " + proprietarioId + ". Total: " + veiculos.size());

        return veiculos.stream()
                .map(VeiculoMapper::toResponse)
                .toList();
    }

    @SneakyThrows
    public List<VeiculoResponse> listarPorStatus(StatusVeiculo status) {
        List<Veiculo> veiculos = veiculoRepository.findByStatus(status);

        // Registra log de consulta por status
        logService.registrar(Acao.READ, "Veiculo", 0L,
                "Consulta de veículos por status: " + status + ". Total: " + veiculos.size());

        return veiculos.stream()
                .map(VeiculoMapper::toResponse)
                .toList();
    }

    public void expirarVeiculosVencidos() {
        List<Veiculo> vencidos = veiculoRepository
                .findByDataExpiracaoBeforeAndStatus(LocalDate.now(), StatusVeiculo.ATIVO);

        vencidos.forEach(v -> v.setStatus(StatusVeiculo.EXPIRADO));
        veiculoRepository.saveAll(vencidos);
    }

    // Método interno — retorna a entidade para uso dentro da própria service
    public Veiculo buscarEntidadePorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));
    }
}