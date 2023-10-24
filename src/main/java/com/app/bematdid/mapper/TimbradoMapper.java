package com.app.bematdid.mapper;

import com.app.bematdid.dto.TimbradoDTO;
import com.app.bematdid.model.Timbrado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FacturaMapper.class})
public interface TimbradoMapper {

    TimbradoDTO timbradoATimbradoDTO (Timbrado timbrado);
    List<TimbradoDTO> timbradosATimbradosDTO (List<Timbrado> timbrados);


    @Mapping(target = "facturas", ignore = true)
    Timbrado timbradoDTOATimbrado (TimbradoDTO timbradoDTO);
}
