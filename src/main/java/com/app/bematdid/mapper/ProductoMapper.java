package com.app.bematdid.mapper;

import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.model.Producto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CicloMapper.class})
public interface ProductoMapper {

    ProductoDTO productoAProductoDTO (Producto producto);
    List<ProductoDTO> productosAProductosDTO (List<Producto> productos);

    @InheritInverseConfiguration
    Producto productoDTOAProducto (ProductoDTO productoDTO);

}
