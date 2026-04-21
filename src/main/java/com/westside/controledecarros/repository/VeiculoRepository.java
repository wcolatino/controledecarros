package com.westside.controledecarros.repository;

import com.westside.controledecarros.entity.Veiculo;
import com.westside.controledecarros.enums.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

        Optional<Veiculo> findByPlaca(String placa);

        List<Veiculo> findByProprietarioId(Long proprietarioId);

        List<Veiculo> findByStatus(StatusVeiculo status);

        List<Veiculo> findByDataExpiracaoBeforeAndStatus(LocalDate data, StatusVeiculo status);

        // Contar por status
        Long countByStatus(StatusVeiculo status);

        // Contar veículos ativos com expiração nos próximos N dias
        @Query("SELECT COUNT(v) FROM Veiculo v WHERE v.status = 'ATIVO' AND v.dataExpiracao BETWEEN :hoje AND :limite")
        Long countAVencer(@Param("hoje") LocalDate hoje, @Param("limite") LocalDate limite);

}
