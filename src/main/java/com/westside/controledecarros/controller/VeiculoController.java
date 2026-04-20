package com.westside.controledecarros.controller;

import com.westside.controledecarros.entity.Veiculo;
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
    public ResponseEntity<Veiculo> cadastrar(
            @PathVariable Long proprietarioId,
            @RequestBody Veiculo veiculo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(veiculoService.cadastrar(proprietarioId, veiculo));
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Veiculo> buscarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(veiculoService.buscarPorPlaca(placa));
    }

    @GetMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<List<Veiculo>> listarPorProprietario(@PathVariable Long proprietarioId) {
        return ResponseEntity.ok(veiculoService.listarPorProprietario(proprietarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Veiculo>> listarPorStatus(@PathVariable StatusVeiculo status) {
        return ResponseEntity.ok(veiculoService.listarPorStatus(status));
    }
}