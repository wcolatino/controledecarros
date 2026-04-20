package com.westside.controledecarros.controller;

import com.westside.controledecarros.dto.request.ProprietarioRequest;
import com.westside.controledecarros.dto.response.ProprietarioResponse;
import com.westside.controledecarros.service.ProprietarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proprietarios")
@RequiredArgsConstructor
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    @PostMapping
    public ResponseEntity<ProprietarioResponse> cadastrar(@RequestBody ProprietarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proprietarioService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<ProprietarioResponse>> listarTodos() {
        return ResponseEntity.ok(proprietarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proprietarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProprietarioResponse> atualizar(@PathVariable Long id, @RequestBody ProprietarioRequest request) {
        return ResponseEntity.ok(proprietarioService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        proprietarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}