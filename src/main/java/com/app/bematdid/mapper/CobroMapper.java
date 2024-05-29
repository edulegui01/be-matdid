package com.app.bematdid.mapper;

import com.app.bematdid.dto.CobroDTO;
import com.app.bematdid.model.Cobro;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CobroMapper {

    @Mappings({
            @Mapping(source = "funcionario.nombre", target = "nombreFuncionario"),
            @Mapping(source = "funcionario.apellido", target = "apellidoFuncionario"),
    })
    CobroDTO cobroACobroDTO (Cobro cobro);
    List<CobroDTO> cobrosACobrosDTO (List<Cobro> cobros);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "funcionario", ignore = true),
            @Mapping(target = "factura", ignore = true)
    })
    Cobro cobroDTOACobro (CobroDTO cobroDTO);
}
