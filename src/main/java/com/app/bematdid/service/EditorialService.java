package com.app.bematdid.service;

import com.app.bematdid.dto.EditorialDTO;
import com.app.bematdid.mapper.EditorialMapper;
import com.app.bematdid.model.Editorial;
import com.app.bematdid.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    @Autowired
    private EditorialMapper mapper;

    public Page<EditorialDTO> listar (Pageable pageable, String nombre) {
        Optional<List<EditorialDTO>> lista = editorialRepository.listarEditoriales(nombre).map(editoriales -> mapper.editorialesAEditorialesDTO(editoriales));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }

    public List<EditorialDTO> listarTodos(){

        Optional<List<EditorialDTO>> lista = editorialRepository.listarEditoriales("").map(editoriales -> mapper.editorialesAEditorialesDTO(editoriales));
        return lista.get();
    }

    public Optional<EditorialDTO> obtenerPorId (Integer id){
        return editorialRepository.findById(id).map(editorial -> mapper.editorialAEditorialDTO(editorial));
    }

    public EditorialDTO guardar(EditorialDTO editorialDTO) {
        Editorial editorial = mapper.editorialDTOAEditorial(editorialDTO);
        return mapper.editorialAEditorialDTO(editorialRepository.save(editorial));
    }

    public void borrar(Integer id){
        Optional<Editorial> editorial = editorialRepository.findById(id);

        Editorial editorial1 = editorial.get();

        editorial1.setEstado(false);

        editorialRepository.save(editorial1);
    }
}
