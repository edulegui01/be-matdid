package com.app.bematdid.service;

import com.app.bematdid.dto.CategoriaDTO;
import com.app.bematdid.mapper.CategoriaMapper;
import com.app.bematdid.model.Categoria;
import com.app.bematdid.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper mapper;

    public Page<CategoriaDTO> listar (Pageable pageable, String nombre) {
        Optional<List<CategoriaDTO>> lista = categoriaRepository.listarCategorias(nombre).map(categorias -> mapper.categoriasACategoriasDTO(categorias));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }

    public List<CategoriaDTO> listarTodos(){

        Optional<List<CategoriaDTO>> lista = categoriaRepository.listarCategorias("").map(categorias -> mapper.categoriasACategoriasDTO(categorias));
        return lista.get();
    }

    public Optional<CategoriaDTO> obtenerPorId (Integer id){
        return categoriaRepository.findById(id).map(categoria -> mapper.categoriaACateogriaDTO(categoria));
    }

    public CategoriaDTO guardar(CategoriaDTO categoriaDTO) {
        Categoria categoria = mapper.categoriaDTOACategoria(categoriaDTO);
        return mapper.categoriaACateogriaDTO(categoriaRepository.save(categoria));
    }

    public void borrar(Integer id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        Categoria categoria1 = categoria.get();

        categoria1.setEstado(false);

        categoriaRepository.save(categoria1);
    }
}
