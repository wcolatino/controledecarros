package com.westside.controledecarros.scheduler;

import com.westside.controledecarros.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VeiculoScheduler {

    private final VeiculoService veiculoService;

    @Scheduled(cron = "0 0 0 * * *") // Toda meia-noite
    public void expirarVeiculosVencidos() {
        log.info("Iniciando verificação de veículos expirados...");
        veiculoService.expirarVeiculosVencidos();
        log.info("Verificação concluída.");
    }
}