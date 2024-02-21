package com.app.bematdid.mapper;

import com.app.bematdid.dto.FuncionarioDTO;
import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioMapper {

    public List<FuncionarioDTO> mapEntitiesIntoDTOs(Iterable<Funcionario> entities){
        List<FuncionarioDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(mapEntityIntoDto(e)));

        return dtos;
    }



    public FuncionarioDTO mapEntityIntoDto(Funcionario entity){
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setIdFuncionario(entity.getIdFuncionario());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setDireccion(entity.getDireccion());
        dto.setCedula(entity.getCedula());
        dto.setFechaAlta(entity.getFechaAlta());
        dto.setLocalidad(entity.getLocalidad());
        dto.setActivo(entity.getActivo());
        dto.setTelefono(entity.getTelefono());
        dto.setFechaNac(entity.getFechaNac());

        return dto;
    }



    public Page<FuncionarioDTO> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Funcionario> source) {
        List<FuncionarioDTO> dtos = mapEntitiesIntoDTOs(source.getContent());

        return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
    }
}
