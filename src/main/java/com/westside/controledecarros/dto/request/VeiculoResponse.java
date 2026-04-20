package com.westside.controledecarros.dto.response;

import com.westside.controledecarros.enums.Bloco;
import com.westside.controledecarros.enums.StatusVeiculo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VeiculoResponse {

    private Long id;
    private String modelo;
    private String marca;
    private String placa;
    private Integer vaga;
    private LocalDate dataCadastro;
    private LocalDate dataExpiracao;
    private StatusVeiculo status;
    private String proprietarioNome;
    private String proprietarioUnidade;
    private Bloco bloco;
}