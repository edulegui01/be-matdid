package com.app.bematdid.controller;

import com.app.bematdid.dto.EditorialDTO;
import com.app.bematdid.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @GetMapping("editorial/listar")
    public Page<EditorialDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return editorialService.listar(pageable, nombre);
    }

    @GetMapping("editorial_select/listar")
    public List<EditorialDTO> listarSelect(){

        return editorialService.listarTodos();

    }

    @GetMapping("editorial/{id}")
    private Optional<EditorialDTO> obtenerPorId (@PathVariable("id") Integer id){
        return editorialService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("editorial/guardar")
    public EditorialDTO guardar(@RequestBody EditorialDTO editorialDTO) {
        return editorialService.guardar(editorialDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("editorial/borrar/{id}")
    public void borrar (@PathVariable("id") Integer id) {
        editorialService.borrar(id);
    }
}
