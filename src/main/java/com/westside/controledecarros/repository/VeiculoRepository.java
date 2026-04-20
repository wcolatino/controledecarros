package com.westside.controledecarros.repository;

import com.westside.controledecarros.entity.Veiculo;
import com.westside.controledecarros.enums.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

        Optional<Veiculo> findByPlaca(String placa);

        List<Veiculo> findByProprietarioId(Long proprietarioId);

        List<Veiculo> findByStatus(StatusVeiculo status);

        List<Veiculo> findByDataExpiracaoBeforeAndStatus(LocalDate data, StatusVeiculo status);

}
