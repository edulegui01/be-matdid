package com.app.bematdid.mapper;

import com.app.bematdid.dto.FacturaDTO;
import com.app.bematdid.model.Factura;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleFacturaMapper.class, CobroMapper.class})
public interface FacturaMapper {

    @Mappings({
            @Mapping(source = "funcionario.nombre", target = "nombreFuncionario"),
            @Mapping(source = "funcionario.apellido", target = "apellidoFuncionario"),
            @Mapping(source = "persona.razonSocial", target = "cliente"),
            @Mapping(source = "timbrado.numero", target = "numeroTimbrado"),
            @Mapping(source = "folio.numeracionFolio", target = "numeroFolio"),


    })
    FacturaDTO facturaAFacturaDTO (Factura factura);
    List<FacturaDTO> facturasAFacturasDTO (List<Factura> factura);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "fecha", ignore = true),
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "persona", ignore = true),
            @Mapping(target = "funcionario", ignore = true),
            @Mapping(target = "timbrado", ignore = true),
            @Mapping(target = "folio", ignore = true),

    })
    Factura facturaDTOAFactura (FacturaDTO facturaDTO);
}
