package com.westside.controledecarros.service;

import com.westside.controledecarros.entity.Log;
import com.westside.controledecarros.enums.Acao;
import com.westside.controledecarros.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

// Responsável por registrar e consultar logs de auditoria
@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    // Registra uma ação no log — chamado pelas outras services
    public void registrar(Acao acao, String entidade, Long entidadeId, String detalhes) {
        Log log = new Log();
        log.setAcao(acao);
        log.setEntidade(entidade);
        log.setEntidadeId(entidadeId);
        log.setUsuarioLogin(getUsuarioLogado());
        log.setDetalhes(detalhes);
        logRepository.save(log);
    }

    public List<Log> listarTodos() {
        return logRepository.findAll();
    }

    public List<Log> listarPorUsuario(String usuarioLogin) {
        return logRepository.findByUsuarioLogin(usuarioLogin);
    }

    public List<Log> listarPorEntidade(String entidade) {
        return logRepository.findByEntidade(entidade);
    }

    public List<Log> listarPorAcao(Acao acao) {
        return logRepository.findByAcao(acao);
    }

    public List<Log> listarPorEntidadeEId(String entidade, Long entidadeId) {
        return logRepository.findByEntidadeAndEntidadeId(entidade, entidadeId);
    }

    // Extrai o login do usuário autenticado via Spring Security
    private String getUsuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}