package com.app.bematdid.mapper;

import com.app.bematdid.dto.DetalleCompraDTO;
import com.app.bematdid.model.DetalleCompra;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface DetalleCompraMapper {

    @Mappings({
            @Mapping(source = "id.idProducto", target = "idProducto"),
            @Mapping(source = "producto.nombre", target = "nombre"),
            @Mapping(source = "producto.iva", target = "iva"),


    })
    DetalleCompraDTO detalleCompraADetalleCompraDTO (DetalleCompra detalleCompra);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "producto", ignore = true),
            @Mapping(target = "id.idCompra", ignore = true)
    })
    DetalleCompra detalleCompraDTOADetalleCompra (DetalleCompraDTO detalleCompraDTO);

}

