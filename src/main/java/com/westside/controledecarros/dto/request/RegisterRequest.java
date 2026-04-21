package com.westside.controledecarros.dto.request;

import com.westside.controledecarros.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "O login é obrigatório.")
    private String login;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    @NotNull(message = "A role é obrigatória.")
    private Role role;
}