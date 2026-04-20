package com.westside.controledecarros.dto.request;

import com.westside.controledecarros.enums.Bloco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProprietarioRequest {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "A unidade é obrigatória.")
    private String unidade;

    @NotNull(message = "O bloco é obrigatório.")
    private Bloco bloco;

    // Aceita formatos: (11) 91234-5678 ou (11) 1234-5678
    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "Telefone inválido. Use o formato (11) 91234-5678.")
    private String telefone;
}