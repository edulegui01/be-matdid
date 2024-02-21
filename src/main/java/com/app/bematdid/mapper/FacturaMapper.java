package com.app.bematdid.mapper;

import com.app.bematdid.dto.FacturaDTO;
import com.app.bematdid.model.Factura;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleFacturaMapper.class})
public interface FacturaMapper {

    @Mappings({
            @Mapping(source = "funcionario.nombre", target = "nombreFuncionario"),
            @Mapping(source = "funcionario.apellido", target = "apellidoFuncionario"),
            @Mapping(source = "persona.nombre", target = "nombrePersona"),

    })
    FacturaDTO facturaAFacturaDTO (Factura factura);
    List<FacturaDTO> facturasAFacturasDTO (List<Factura> factura);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "fecha", ignore = true),
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "persona", ignore = true),
            @Mapping(target = "funcionario", ignore = true),

    })
    Factura facturaDTOAFactura (FacturaDTO facturaDTO);
}
