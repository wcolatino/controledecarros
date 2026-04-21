package com.westside.controledecarros.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    @NotBlank(message = "O login é obrigatório.")
    private String login;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;
}