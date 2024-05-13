package com.app.bematdid.mapper;

import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.model.Persona;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompraMapper.class, FacturaMapper.class})
public interface PersonaMapper {
    PersonaDTO personaAPersonaDTO (Persona persona);
    List<PersonaDTO> personasAPersonasDTO (List<Persona> personas);

    @InheritInverseConfiguration
    @Mappings({
        @Mapping(target = "esCliente", ignore = true),
        @Mapping(target = "estado", ignore = true),
        @Mapping(target = "compras", ignore = true),
        @Mapping(target = "facturas", ignore = true)
    })
    Persona personaDTOAPersona (PersonaDTO personaDTO);
}
