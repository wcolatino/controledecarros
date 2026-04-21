package com.westside.controledecarros.repository;

import com.westside.controledecarros.entity.Log;
import com.westside.controledecarros.enums.Acao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    // Buscar logs por usuário
    List<Log> findByUsuarioLogin(String usuarioLogin);

    // Buscar logs por entidade (ex: todos os logs de Proprietario)
    List<Log> findByEntidade(String entidade);

    // Buscar logs por ação (ex: todos os DELETEs)
    List<Log> findByAcao(Acao acao);

    // Buscar logs de um registro específico
    List<Log> findByEntidadeAndEntidadeId(String entidade, Long entidadeId);
}