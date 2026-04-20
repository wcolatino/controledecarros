package com.westside.controledecarros.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoRequest {

    @NotBlank(message = "O modelo é obrigatório.")
    private String modelo;

    @NotBlank(message = "A marca é obrigatória.")
    private String marca;

    // Aceita placas no formato antigo (ABC-1234) e Mercosul (ABC1D23)
    @NotBlank(message = "A placa é obrigatória.")
    @Pattern(regexp = "^[A-Z]{3}-?\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$", message = "Placa inválida. Use o formato ABC-1234 ou ABC1D23.")
    private String placa;

    @NotNull(message = "A vaga é obrigatória.")
    @Min(value = 1, message = "O número da vaga deve ser maior que zero.")
    private Integer vaga;
}