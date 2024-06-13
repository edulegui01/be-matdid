package com.app.bematdid.mapper;

import com.app.bematdid.dto.MovimientoCajaDTO;
import com.app.bematdid.model.MovimientoCaja;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientoCajaMapper {

    @Mappings({
            @Mapping(source = "funcionario.nombre", target = "nombreFuncionario"),
            @Mapping(source = "funcionario.apellido", target = "apellidoFuncionario")
    })
    MovimientoCajaDTO movimientoCajaAMovimientoCajaDTO (MovimientoCaja movimientoCaja);
    List<MovimientoCajaDTO> movimientosCajaAMovimientosCajaDTO (List<MovimientoCaja> movimientoCajas);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "funcionario", ignore = true),
            @Mapping(target = "concepto", ignore = true)

    })
    MovimientoCaja movimientoCajaDTOAMovimientoCaja (MovimientoCajaDTO movimientoCajaDTO);
}
