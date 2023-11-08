package com.app.bematdid.mapper;

import com.app.bematdid.dto.GastoDTO;
import com.app.bematdid.model.Gasto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GastoMapper {

    @Mappings({
            @Mapping(source = "funcionario.nombre", target = "nombreFuncionario"),
            @Mapping(source = "funcionario.apellido", target = "apellidoFuncionario")
    })
    GastoDTO gastoAgastoDTO (Gasto gasto);
    List<GastoDTO> gastosAGastosDTO (List<Gasto> gastos);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "funcionario", ignore = true)
    })
    Gasto gastoDTOAGasto (GastoDTO gastoDTO);
}
