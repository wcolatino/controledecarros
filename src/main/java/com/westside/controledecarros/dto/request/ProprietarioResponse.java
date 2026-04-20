package com.westside.controledecarros.dto.response;

import com.westside.controledecarros.enums.Bloco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProprietarioResponse {

    private Long id;
    private String nome;
    private String unidade;
    private Bloco bloco;
    private String telefone;
}