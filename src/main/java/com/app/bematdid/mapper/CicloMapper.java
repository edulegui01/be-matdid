package com.app.bematdid.mapper;

import com.app.bematdid.dto.CicloDTO;
import com.app.bematdid.model.Ciclo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CicloMapper {

    CicloDTO cicloACicloDTO(Ciclo ciclo);
    List<CicloDTO> ciclosACiclosDTO(List<Ciclo> ciclos);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "productos", ignore = true)
    })
    Ciclo cicloDTOACiclo (CicloDTO cicloDTO);
}
