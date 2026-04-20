package com.westside.controledecarros.mapper;

import com.westside.controledecarros.dto.request.VeiculoRequest;
import com.westside.controledecarros.dto.response.VeiculoResponse;
import com.westside.controledecarros.entity.Veiculo;

// Mapper manual — projeto pequeno com poucas entidades,
// não justifica o uso de frameworks como MapStruct.
public class VeiculoMapper {

    public static Veiculo toEntity(VeiculoRequest request) {
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(request.getModelo());
        veiculo.setMarca(request.getMarca());
        veiculo.setPlaca(request.getPlaca());
        veiculo.setVaga(request.getVaga());
        return veiculo;
    }

    public static VeiculoResponse toResponse(Veiculo veiculo) {
        VeiculoResponse response = new VeiculoResponse();
        response.setId(veiculo.getId());
        response.setModelo(veiculo.getModelo());
        response.setMarca(veiculo.getMarca());
        response.setPlaca(veiculo.getPlaca());
        response.setVaga(veiculo.getVaga());
        response.setDataCadastro(veiculo.getDataCadastro());
        response.setDataExpiracao(veiculo.getDataExpiracao());
        response.setStatus(veiculo.getStatus());
        // Dados resumidos do proprietário para evitar chamada extra na API
        response.setProprietarioNome(veiculo.getProprietario().getNome());
        response.setProprietarioUnidade(veiculo.getProprietario().getUnidade());
        response.setBloco(veiculo.getProprietario().getBloco());
        return response;
    }
}