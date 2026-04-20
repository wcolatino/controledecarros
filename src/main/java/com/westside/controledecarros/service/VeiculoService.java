package com.westside.controledecarros.service;

import com.westside.controledecarros.dto.request.VeiculoRequest;
import com.westside.controledecarros.dto.response.VeiculoResponse;
import com.westside.controledecarros.entity.Proprietario;
import com.westside.controledecarros.entity.Veiculo;
import com.westside.controledecarros.enums.StatusVeiculo;
import com.westside.controledecarros.mapper.VeiculoMapper;
import com.westside.controledecarros.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ProprietarioService proprietarioService;

    public VeiculoResponse cadastrar(Long proprietarioId, VeiculoRequest request) {
        veiculoRepository.findByPlaca(request.getPlaca()).ifPresent(v -> {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com esta placa.");
        });

        Proprietario proprietario = proprietarioService.buscarEntidadePorId(proprietarioId);
        Veiculo veiculo = VeiculoMapper.toEntity(request);
        veiculo.setProprietario(proprietario);

        return VeiculoMapper.toResponse(veiculoRepository.save(veiculo));
    }

    public List<VeiculoResponse> listarTodos() {
        return veiculoRepository.findAll()
                .stream()
                .map(VeiculoMapper::toResponse)
                .toList();
    }

    public VeiculoResponse buscarPorId(Long id) {
        return VeiculoMapper.toResponse(buscarEntidadePorId(id));
    }

    public VeiculoResponse buscarPorPlaca(String placa) {
        Veiculo veiculo = veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));
        return VeiculoMapper.toResponse(veiculo);
    }

    public List<VeiculoResponse> listarPorProprietario(Long proprietarioId) {
        return veiculoRepository.findByProprietarioId(proprietarioId)
                .stream()
                .map(VeiculoMapper::toResponse)
                .toList();
    }

    public List<VeiculoResponse> listarPorStatus(StatusVeiculo status) {
        return veiculoRepository.findByStatus(status)
                .stream()
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
    private Veiculo buscarEntidadePorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));
    }
}