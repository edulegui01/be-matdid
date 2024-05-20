package com.app.bematdid.controller;

import com.app.bematdid.dto.MateriaDTO;
import com.app.bematdid.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping("materia/listar")
    public Page<MateriaDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return materiaService.listar(pageable, nombre);
    }

    @GetMapping("materia_select/listar")
    public List<MateriaDTO> listarSelect(){

        return materiaService.listarTodos();

    }

    @GetMapping("materia/{id}")
    private Optional<MateriaDTO> obtenerPorId (@PathVariable("id") Integer id){
        return materiaService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("materia/guardar")
    public MateriaDTO guardar(@RequestBody MateriaDTO materiaDTO) {
        return materiaService.guardar(materiaDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("materia/borrar/{id}")
    public void borrar (@PathVariable("id") Integer id) {
        materiaService.borrar(id);
    }
}
