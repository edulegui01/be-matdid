package com.app.bematdid.mapper;

import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ProductoMapper {

    public List<ProductoDTO> mapEntitiesIntoDTOs(Iterable<Producto> entities){
        List<ProductoDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(mapEntityIntoDto(e)));

        return dtos;
    }



    public ProductoDTO mapEntityIntoDto(Producto entity){
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(entity.getIdProducto());
        dto.setNombre(entity.getNombre());
        dto.setPrecioCosto(entity.getPrecioCosto());
        dto.setPrecioVenta(entity.getPrecioVenta());
        dto.setIva(entity.getIva());
        dto.setCantidadMinima(entity.getCantidadMinima());
        dto.setCantidad(entity.getCantidad());


        return dto;
    }



    public Page<ProductoDTO> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Producto> source) {
        List<ProductoDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
    }

}
