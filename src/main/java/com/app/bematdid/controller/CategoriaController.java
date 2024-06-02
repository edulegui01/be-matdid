package com.app.bematdid.controller;

import com.app.bematdid.dto.CategoriaDTO;
import com.app.bematdid.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("categoria/listar")
    public Page<CategoriaDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return categoriaService.listar(pageable, nombre);
    }

    @GetMapping("categoria_select/listar")
    public List<CategoriaDTO> listarSelect(){

        return categoriaService.listarTodos();

    }

    @GetMapping("categoria/listar/ciclo")
    public List<CategoriaDTO> listarByCiclo(@RequestParam Long idCiclo){
        return categoriaService.listarByCiclo(idCiclo);
    }

    @GetMapping("categoria/{id}")
    private Optional<CategoriaDTO> obtenerPorId (@PathVariable("id") Integer id){
        return categoriaService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("categoria/guardar")
    public CategoriaDTO guardar(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.guardar(categoriaDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("categoria/borrar/{id}")
    public void borrar (@PathVariable("id") Integer id) {
        categoriaService.borrar(id);
    }
}
