package com.app.bematdid.mapper;

import com.app.bematdid.dto.PagoDTO;
import com.app.bematdid.model.Pago;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagoMapper {
    @Mappings({
            @Mapping(source = "funcionario.nombre", target = "nombreFuncionario"),
            @Mapping(source = "funcionario.apellido", target = "apellidoFuncionario"),
    })
    PagoDTO pagoAPagoDTO (Pago pago);
    List<PagoDTO> pagosAPagosDTO (List<Pago> pagos);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "funcionario", ignore = true),
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "estado", ignore = true)
    })
    Pago pagoDTOAPago (PagoDTO pagoDTO);

}
