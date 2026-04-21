package com.westside.controledecarros.repository;

import com.westside.controledecarros.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca o usuário pelo login para autenticação
    Optional<Usuario> findByLogin(String login);
}