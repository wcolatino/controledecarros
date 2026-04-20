package com.westside.controledecarros.repository;

import com.westside.controledecarros.entity.Proprietario;
import com.westside.controledecarros.enums.Bloco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {

    Optional<Proprietario> findByUnidadeAndBloco(String unidade, Bloco bloco);
}
