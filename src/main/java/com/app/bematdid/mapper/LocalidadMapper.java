package com.app.bematdid.mapper;

import com.app.bematdid.dto.LocalidadDTO;
import com.app.bematdid.model.Localidad;

import java.util.ArrayList;
import java.util.List;

public class LocalidadMapper {

    public List<LocalidadDTO> mapEntitiesIntoDTOs(Iterable<Localidad> entities){
        List<LocalidadDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(mapEntityIntoDto(e)));

        return dtos;
    }



    public LocalidadDTO mapEntityIntoDto(Localidad entity){
        LocalidadDTO dto = new LocalidadDTO();
        dto.setId(entity.getId());
        dto.setCodigo_distrito(entity.getCodigoDistrito());
        dto.setDescripcion_distrito(entity.getDescripcionDistrito());


        return dto;
    }
}
