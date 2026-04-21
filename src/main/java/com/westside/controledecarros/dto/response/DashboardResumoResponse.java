package com.westside.controledecarros.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardResumoResponse {

    private Long totalCadastrados;
    private Long totalAtivos;
    private Long totalExpirados;
    private Long totalAVencer;
}