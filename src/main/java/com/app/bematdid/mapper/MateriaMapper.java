package com.app.bematdid.mapper;

import com.app.bematdid.dto.MateriaDTO;
import com.app.bematdid.model.Materia;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MateriaMapper {

    MateriaDTO materiaAMateriaDTO(Materia materia);
    List<MateriaDTO> materiasAMateriasDTO(List<Materia> materias);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "productos", ignore = true)
    })
    Materia materiaDTOAMateria (MateriaDTO materiaDTO);
}
