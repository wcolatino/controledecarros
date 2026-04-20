package com.westside.controledecarros.dto.request;

import com.westside.controledecarros.enums.Bloco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProprietarioRequest {

    private String nome;
    private String unidade;
    private Bloco bloco;
    private String telefone;
}