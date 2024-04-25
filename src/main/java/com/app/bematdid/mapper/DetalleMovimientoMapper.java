package com.app.bematdid.mapper;

import com.app.bematdid.dto.DetalleMovimientoDTO;
import com.app.bematdid.model.DetalleMovimiento;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface DetalleMovimientoMapper {

    @Mappings({
            @Mapping(source = "id.idProducto", target = "idProducto"),
            @Mapping(source = "producto.nombre", target = "nombre"),
    })
    DetalleMovimientoDTO detalleMovimientoADetalleMovimientoDTO (DetalleMovimiento detalleMovimiento);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "movimiento", ignore = true),
            @Mapping(target = "producto", ignore = true),
            @Mapping(target = "id.idMovimiento", ignore = true)
    })
    DetalleMovimiento detalleMovimientoDTOADetalleMovimiento (DetalleMovimientoDTO detalleMovimientoDTO);
}
