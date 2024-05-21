package com.app.bematdid.mapper;

import com.app.bematdid.dto.MotivoDTO;
import com.app.bematdid.model.Motivo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MotivoMapper {

    MotivoDTO motivoAMotivoDTO(Motivo motivo);
    List<MotivoDTO> motivossAMotivosDTO(List<Motivo> motivos);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "movimientos", ignore = true)
    })
    Motivo motivoDTOAMotivo (MotivoDTO motivoDTO);
}
