package com.westside.controledecarros.service;

import com.westside.controledecarros.dto.request.AuthRequest;
import com.westside.controledecarros.dto.request.RegisterRequest;
import com.westside.controledecarros.dto.response.AuthResponse;
import com.westside.controledecarros.dto.response.RegisterResponse;
import com.westside.controledecarros.entity.Usuario;
import com.westside.controledecarros.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// Responsável pela autenticação e registro de usuários
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Autentica o usuário e retorna um token JWT
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha())
        );

        Usuario usuario = usuarioRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        String token = jwtService.gerarToken(usuario);
        return new AuthResponse(token);
    }

    // Registra um novo usuário — apenas ADMIN pode chamar este endpoint
    public RegisterResponse register(RegisterRequest request) {
        if (usuarioRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Login já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setLogin(request.getLogin());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(request.getRole());

        usuarioRepository.save(usuario);

        return new RegisterResponse(usuario.getId(), usuario.getLogin(), usuario.getRole());
    }

    public List<RegisterResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> new RegisterResponse(u.getId(), u.getLogin(), u.getRole()))
                .toList();
    }
}