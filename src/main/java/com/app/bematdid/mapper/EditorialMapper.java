package com.app.bematdid.mapper;

import com.app.bematdid.dto.EditorialDTO;
import com.app.bematdid.model.Editorial;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EditorialMapper {
    EditorialDTO editorialAEditorialDTO(Editorial editorial);
    List<EditorialDTO> editorialesAEditorialesDTO(List<Editorial> editoriales);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "productos", ignore = true)
    })
    Editorial editorialDTOAEditorial (EditorialDTO editorialDTO);
}
