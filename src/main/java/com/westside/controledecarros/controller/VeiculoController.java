package com.westside.controledecarros.controller;

import com.westside.controledecarros.dto.request.VeiculoRequest;
import com.westside.controledecarros.dto.response.VeiculoResponse;
import com.westside.controledecarros.enums.StatusVeiculo;
import com.westside.controledecarros.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<VeiculoResponse> cadastrar(
            @PathVariable Long proprietarioId,
            @RequestBody VeiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(veiculoService.cadastrar(proprietarioId, request));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<VeiculoResponse> buscarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(veiculoService.buscarPorPlaca(placa));
    }

    @GetMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<List<VeiculoResponse>> listarPorProprietario(@PathVariable Long proprietarioId) {
        return ResponseEntity.ok(veiculoService.listarPorProprietario(proprietarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<VeiculoResponse>> listarPorStatus(@PathVariable StatusVeiculo status) {
        return ResponseEntity.ok(veiculoService.listarPorStatus(status));
    }
}