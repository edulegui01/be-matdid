package com.app.bematdid.mapper;

import com.app.bematdid.dto.CategoriaDTO;
import com.app.bematdid.model.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaDTO categoriaACateogriaDTO(Categoria categoria);
    List<CategoriaDTO> categoriasACategoriasDTO(List<Categoria> categorias);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "productos", ignore = true)
    })
    Categoria categoriaDTOACategoria (CategoriaDTO categoriaDTO);
}
