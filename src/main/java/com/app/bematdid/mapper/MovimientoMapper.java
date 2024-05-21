package com.app.bematdid.mapper;

import com.app.bematdid.dto.MovimientoDTO;
import com.app.bematdid.dto.MovimientoSaveDTO;
import com.app.bematdid.model.Movimiento;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.text.SimpleDateFormat;
import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleMovimientoMapper.class, MotivoMapper.class})
public interface MovimientoMapper {

    @Mappings({
            @Mapping(source = "funcionario.nombre", target = "nombreFuncionario"),
            @Mapping(source = "funcionario.apellido", target = "apellidoFuncionario"),
            @Mapping(target = "fecha", source = "fecha",dateFormat = "dd-MM-yyyy")

    })
    MovimientoDTO movimientoAMovimientoDTO (Movimiento movimiento);
    List<MovimientoDTO> movimientosAMovimientosDTO (List<Movimiento> movimientos);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "funcionario", ignore = true),
    })
    Movimiento movimientoDTOAMovimiento (MovimientoSaveDTO movimientoDTO);
}
