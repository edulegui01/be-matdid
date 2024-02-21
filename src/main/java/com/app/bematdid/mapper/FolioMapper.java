package com.app.bematdid.mapper;

import com.app.bematdid.dto.FacturaDTO;
import com.app.bematdid.dto.FolioDTO;
import com.app.bematdid.dto.GastoDTO;
import com.app.bematdid.model.Factura;
import com.app.bematdid.model.Gasto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolioMapper {

    @Mappings({
            @Mapping(source = "numFactura", target = "numFactura")
    })
    FolioDTO folioAfolioDTO (Factura factura);

}