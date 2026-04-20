package com.westside.controledecarros.controller;

import com.westside.controledecarros.entity.Proprietario;
import com.westside.controledecarros.service.ProprietarioService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<Proprietario> cadastrar(@RequestBody Proprietario proprietario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proprietarioService.cadastrar(proprietario));
    }

    @GetMapping
    public ResponseEntity<List<Proprietario>> listarTodos() {
        return ResponseEntity.ok(proprietarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proprietarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long id, @RequestBody Proprietario dados) {
        return ResponseEntity.ok(proprietarioService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        proprietarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}