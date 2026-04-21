package com.westside.controledecarros.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

// Responsável por gerar, validar e extrair informações dos tokens JWT
@Service
public class JwtService {

    // Chave secreta definida no application.yml
    @Value("${jwt.secret}")
    private String secret;

    // Tempo de expiração do token: 8 horas
    private static final long EXPIRATION = 1000 * 60 * 60 * 8;

    // Gera um token JWT para o usuário autenticado
    public String gerarToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSecretKey())
                .compact();
    }

    // Extrai o login (subject) do token
    public String extrairLogin(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    // Valida se o token pertence ao usuário e não está expirado
    public boolean tokenValido(String token, UserDetails userDetails) {
        return extrairLogin(token).equals(userDetails.getUsername()) && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {
        return extrairClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extrairClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}