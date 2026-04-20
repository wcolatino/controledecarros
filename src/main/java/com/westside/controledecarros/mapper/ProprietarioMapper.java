package com.westside.controledecarros.mapper;

import com.westside.controledecarros.dto.request.ProprietarioRequest;
import com.westside.controledecarros.dto.response.ProprietarioResponse;
import com.westside.controledecarros.entity.Proprietario;

// Mapper manual — projeto pequeno com poucas entidades, não vejo necessidade do uso de frameworks como MapStruct.
public class ProprietarioMapper {

    public static Proprietario toEntity(ProprietarioRequest request) {
        Proprietario proprietario = new Proprietario();
        proprietario.setNome(request.getNome());
        proprietario.setUnidade(request.getUnidade());
        proprietario.setBloco(request.getBloco());
        proprietario.setTelefone(request.getTelefone());
        return proprietario;
    }

    public static ProprietarioResponse toResponse(Proprietario proprietario) {
        ProprietarioResponse response = new ProprietarioResponse();
        response.setId(proprietario.getId());
        response.setNome(proprietario.getNome());
        response.setUnidade(proprietario.getUnidade());
        response.setBloco(proprietario.getBloco());
        response.setTelefone(proprietario.getTelefone());
        return response;
    }
}