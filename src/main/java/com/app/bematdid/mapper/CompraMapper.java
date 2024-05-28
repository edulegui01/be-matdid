package com.app.bematdid.mapper;

import com.app.bematdid.dto.CompraDTO;
import com.app.bematdid.model.Compra;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleCompraMapper.class, PagoMapper.class})
public interface CompraMapper {

    @Mappings({
            @Mapping(source = "funcionario.nombre", target = "nombreFuncionario"),
            @Mapping(source = "funcionario.apellido", target = "apellidoFuncionario"),
            @Mapping(source = "persona.cedula", target = "cedula"),
            @Mapping(source = "persona.ruc", target = "ruc"),
            @Mapping(source = "persona.nombre", target = "nombrePersona"),
            @Mapping(source = "persona.apellido", target = "apellidoPersona"),
            @Mapping(source = "persona.empresa", target = "nombreEmpresa"),
            @Mapping(source = "persona.razonSocial", target = "razonSocial")

    })
    CompraDTO compraACompraDTO (Compra compra);
    List<CompraDTO> comprasAComprasDTO (List<Compra> compras);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "persona", ignore = true),
            @Mapping(target = "funcionario", ignore = true)
    })
    Compra compraDTOACompra (CompraDTO compraDTO);

}

