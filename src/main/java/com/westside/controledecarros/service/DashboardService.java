package com.westside.controledecarros.service;

import com.westside.controledecarros.dto.response.DashboardResumoResponse;
import com.westside.controledecarros.enums.StatusVeiculo;
import com.westside.controledecarros.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VeiculoRepository veiculoRepository;

    public DashboardResumoResponse getResumo() {
        Long totalCadastrados = veiculoRepository.count();
        Long totalAtivos = veiculoRepository.countByStatus(StatusVeiculo.ATIVO);
        Long totalExpirados = veiculoRepository.countByStatus(StatusVeiculo.EXPIRADO);
        Long totalAVencer = veiculoRepository.countAVencer(LocalDate.now(), LocalDate.now().plusDays(5));

        return new DashboardResumoResponse(totalCadastrados, totalAtivos, totalExpirados, totalAVencer);
    }
}