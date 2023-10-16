package com.app.bematdid.mapper;

import com.app.bematdid.dto.CompraDTO;
import com.app.bematdid.model.Compra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleCompraMapper.class})
public interface CompraMapper {

    CompraDTO compraACompraDTO (Compra compra);
    List<CompraDTO> comprasAComprasDTO (List<Compra> compras);


    @Mappings({
        @Mapping(target = "persona", ignore = true),
        @Mapping(target = "funcionario", ignore = true)
    })
    Compra compraDTOACompra (CompraDTO compraDTO);

}

