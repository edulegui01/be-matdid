package com.app.bematdid.service;

import com.app.bematdid.dto.ConceptoDTO;
import com.app.bematdid.mapper.ConceptoMapper;
import com.app.bematdid.model.Concepto;
import com.app.bematdid.repository.ConceptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConceptoService {
    @Autowired
    private ConceptoRepository conceptoRepository;

    @Autowired
    private ConceptoMapper mapper;

    public Page<ConceptoDTO> listar (Pageable pageable, String nombre) {
        Optional<List<ConceptoDTO>> lista = conceptoRepository.listarConceptos(nombre).map(conceptos -> mapper.conceptosAConceptosDTO(conceptos));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }

    public List<ConceptoDTO> listarTodos(){

        Optional<List<ConceptoDTO>> lista = conceptoRepository.listarConceptos("").map(conceptos -> mapper.conceptosAConceptosDTO(conceptos));
        return lista.get();
    }

    public Optional<ConceptoDTO> obtenerPorId (Integer id){
        return conceptoRepository.findById(id).map(concepto -> mapper.conceptoAConceptoDTO(concepto));
    }

    public ConceptoDTO guardar(ConceptoDTO conceptoDTO) {
        Concepto concepto = mapper.conceptoDTOAConcepto(conceptoDTO);
        return mapper.conceptoAConceptoDTO(conceptoRepository.save(concepto));
    }

    public void borrar(Integer id){
        Optional<Concepto> concepto = conceptoRepository.findById(id);

        Concepto concepto1 = concepto.get();

        concepto1.setEstado(false);

        conceptoRepository.save(concepto1);
    }
}
