package com.app.bematdid.mapper;

import com.app.bematdid.dto.FuncionarioDTO;
import com.app.bematdid.dto.LocalidadDTO;
import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Localidad;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
        dto.setNombre(entity.getNombre());


        return dto;
    }


    public Page<LocalidadDTO> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Localidad> source) {
        List<LocalidadDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
    }
}
