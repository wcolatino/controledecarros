package com.westside.controledecarros.dto.response;

import com.westside.controledecarros.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {

    private Long id;
    private String login;
    private Role role;
}