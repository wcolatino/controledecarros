package com.westside.controledecarros.service;

import com.westside.controledecarros.entity.Proprietario;
import com.westside.controledecarros.repository.ProprietarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    public Proprietario cadastrar(Proprietario proprietario) {
        boolean jaExiste = proprietarioRepository
                .findByUnidadeAndBloco(proprietario.getUnidade(), proprietario.getBloco())
                .isPresent();

        if (jaExiste) {
            throw new IllegalArgumentException("Já existe um proprietário cadastrado nesta unidade e bloco.");
        }

        return proprietarioRepository.save(proprietario);
    }

    public List<Proprietario> listarTodos() {
        return proprietarioRepository.findAll();
    }

    public Proprietario buscarPorId(Long id) {
        return proprietarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado."));
    }

    public Proprietario atualizar(Long id, Proprietario dados) {
        Proprietario existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setUnidade(dados.getUnidade());
        existente.setBloco(dados.getBloco());
        existente.setTelefone(dados.getTelefone());
        return proprietarioRepository.save(existente);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        proprietarioRepository.deleteById(id);
    }
}
