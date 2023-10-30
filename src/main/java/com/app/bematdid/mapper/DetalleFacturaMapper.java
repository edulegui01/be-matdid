package com.app.bematdid.mapper;

import com.app.bematdid.dto.DetalleFacturaDTO;
import com.app.bematdid.model.DetalleFactura;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface DetalleFacturaMapper {

    @Mappings({
            @Mapping(source = "id.idProducto", target = "idProducto")
    })
    DetalleFacturaDTO detalleFacturaADetalleFacturaDTO (DetalleFactura detalleFactura);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "factura", ignore = true),
            @Mapping(target = "producto", ignore = true),
            @Mapping(target = "id.idFactura", ignore = true)
    })
    DetalleFactura detalleFacturaDTOADetalleFactura (DetalleFacturaDTO detalleFacturaDTO);

}
