package com.westside.controledecarros.controller;

import com.westside.controledecarros.dto.response.DashboardResumoResponse;
import com.westside.controledecarros.scheduler.VeiculoScheduler;
import com.westside.controledecarros.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final VeiculoScheduler veiculoScheduler;

    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoResponse> getResumo() {
        return ResponseEntity.ok(dashboardService.getResumo());
    }

    @GetMapping("/scheduler/executar")
    public ResponseEntity<Void> executar() {
        veiculoScheduler.expirarVeiculosVencidos();
        return ResponseEntity.ok().build();
    }
}