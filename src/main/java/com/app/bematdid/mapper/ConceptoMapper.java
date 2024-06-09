package com.app.bematdid.mapper;

import com.app.bematdid.dto.ConceptoDTO;
import com.app.bematdid.model.Concepto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConceptoMapper {
    ConceptoDTO conceptoAConceptoDTO(Concepto concepto);
    List<ConceptoDTO> conceptosAConceptosDTO(List<Concepto> conceptos);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "estado", ignore = true),
            @Mapping(target = "movimientosCaja", ignore = true)
    })
    Concepto conceptoDTOAConcepto (ConceptoDTO conceptoDTO);
}
