package com.app.bematdid.mapper;

import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PersonaMapper {



    public List<PersonaDTO> mapEntitiesIntoDTOs(Iterable<Persona> entities){
        List<PersonaDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(mapEntityIntoDto(e)));

        return dtos;
    }



    public PersonaDTO mapEntityIntoDto(Persona entity){
        PersonaDTO dto = new PersonaDTO();
        dto.setIdPersona(entity.getIdPersona());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setCedula(entity.getCedula());
        dto.setRuc(entity.getRuc());
        dto.setLocalidad(entity.getLocalidad());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());

        return dto;
    }



    public Page<PersonaDTO> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Persona> source) {
        List<PersonaDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
    }


}
