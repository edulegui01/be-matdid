package com.app.bematdid.service;


import com.app.bematdid.dto.AutorDTO;
import com.app.bematdid.dto.CategoriaDTO;
import com.app.bematdid.mapper.AutorMapper;
import com.app.bematdid.model.Autor;
import com.app.bematdid.model.Categoria;
import com.app.bematdid.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorMapper mapper;

    public Page<AutorDTO> listar (Pageable pageable, String nombre) {
        Optional<List<AutorDTO>> lista = autorRepository.listarAutores(nombre).map(autores -> mapper.autoresAAutoresDTO(autores));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }

    public List<AutorDTO> listarTodos(){

        Optional<List<AutorDTO>> lista = autorRepository.listarAutores("").map(autores -> mapper.autoresAAutoresDTO(autores));
        return lista.get();
    }

    public List<AutorDTO> listarByCiclo(Long idCiclo){
        List<Autor> autores = autorRepository.listarAutoresByCiclo(idCiclo);
        return mapper.autoresAAutoresDTO(autores);
    }

    public Optional<AutorDTO> obtenerPorId (Integer id){
        return autorRepository.findById(id).map(autor -> mapper.autorAAutorDTO(autor));
    }

    public AutorDTO guardar(AutorDTO autorDTO) {
        Autor autor = mapper.autorDTOAAutor(autorDTO);
        return mapper.autorAAutorDTO(autorRepository.save(autor));
    }

    public void borrar(Integer id){
        Optional<Autor> categoria = autorRepository.findById(id);

        Autor autor1 = categoria.get();

        autor1.setEstado(false);

        autorRepository.save(autor1);
    }
}
