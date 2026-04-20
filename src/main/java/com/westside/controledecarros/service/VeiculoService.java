package com.westside.controledecarros.service;

import com.westside.controledecarros.entity.Proprietario;
import com.westside.controledecarros.entity.Veiculo;
import com.westside.controledecarros.enums.StatusVeiculo;
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

    public Veiculo cadastrar(Long proprietarioId, Veiculo veiculo) {
        veiculoRepository.findByPlaca(veiculo.getPlaca()).ifPresent(v -> {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com esta placa.");
        });

        Proprietario proprietario = proprietarioService.buscarPorId(proprietarioId);
        veiculo.setProprietario(proprietario);

        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public List<Veiculo> listarPorProprietario(Long proprietarioId) {
        return veiculoRepository.findByProprietarioId(proprietarioId);
    }

    public List<Veiculo> listarPorStatus(StatusVeiculo status) {
        return veiculoRepository.findByStatus(status);
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));
    }

    public Veiculo buscarPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));
    }

    public void expirarVeiculosVencidos() {
        List<Veiculo> vencidos = veiculoRepository
                .findByDataExpiracaoBeforeAndStatus(LocalDate.now(), StatusVeiculo.ATIVO);

        vencidos.forEach(v -> v.setStatus(StatusVeiculo.EXPIRADO));
        veiculoRepository.saveAll(vencidos);
    }
}