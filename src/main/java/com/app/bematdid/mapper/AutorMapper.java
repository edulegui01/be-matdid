package com.app.bematdid.mapper;



import com.app.bematdid.dto.AutorDTO;
import com.app.bematdid.model.Autor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorDTO autorAAutorDTO(Autor autor);
    List<AutorDTO> autoresAAutoresDTO(List<Autor> autores);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "productos", ignore = true)
    })
    Autor autorDTOAAutor (AutorDTO autorDTO);
}
