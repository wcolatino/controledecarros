package com.westside.controledecarros.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoRequest {

    private String modelo;
    private String marca;
    private String placa;
    private Integer vaga;
}