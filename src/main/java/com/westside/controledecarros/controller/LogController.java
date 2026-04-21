package com.westside.controledecarros.controller;

import com.westside.controledecarros.entity.Log;
import com.westside.controledecarros.enums.Acao;
import com.westside.controledecarros.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Apenas ADMIN pode consultar os logs
@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Log>> listarTodos() {
        return ResponseEntity.ok(logService.listarTodos());
    }

    @GetMapping("/usuario/{login}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Log>> listarPorUsuario(@PathVariable String login) {
        return ResponseEntity.ok(logService.listarPorUsuario(login));
    }

    @GetMapping("/entidade/{entidade}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Log>> listarPorEntidade(@PathVariable String entidade) {
        return ResponseEntity.ok(logService.listarPorEntidade(entidade));
    }

    @GetMapping("/acao/{acao}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Log>> listarPorAcao(@PathVariable Acao acao) {
        return ResponseEntity.ok(logService.listarPorAcao(acao));
    }

    @GetMapping("/entidade/{entidade}/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Log>> listarPorEntidadeEId(
            @PathVariable String entidade,
            @PathVariable Long id) {
        return ResponseEntity.ok(logService.listarPorEntidadeEId(entidade, id));
    }
}