package com.westside.controledecarros.controller;

import com.westside.controledecarros.dto.request.AuthRequest;
import com.westside.controledecarros.dto.request.RegisterRequest;
import com.westside.controledecarros.dto.response.AuthResponse;
import com.westside.controledecarros.dto.response.RegisterResponse;
import com.westside.controledecarros.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Endpoint público — qualquer um pode fazer login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Apenas ADMIN pode registrar novos usuários
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RegisterResponse>> listarUsuarios() {
        return ResponseEntity.ok(authService.listarUsuarios());
    }


}