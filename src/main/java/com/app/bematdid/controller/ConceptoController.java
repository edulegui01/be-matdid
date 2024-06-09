package com.app.bematdid.controller;

import com.app.bematdid.dto.ConceptoDTO;
import com.app.bematdid.service.ConceptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConceptoController {

    @Autowired
    private ConceptoService conceptoService;

    @GetMapping("concepto/listar")
    public Page<ConceptoDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return conceptoService.listar(pageable, nombre);
    }

    @GetMapping("concepto_select/listar")
    public List<ConceptoDTO> listarSelect(){

        return conceptoService.listarTodos();

    }

    @GetMapping("concepto/{id}")
    private Optional<ConceptoDTO> obtenerPorId (@PathVariable("id") Integer id){
        return conceptoService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("concepto/guardar")
    public ConceptoDTO guardar(@RequestBody ConceptoDTO conceptoDTO) {
        return conceptoService.guardar(conceptoDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("concepto/borrar/{id}")
    public void borrar (@PathVariable("id") Integer id) {
        conceptoService.borrar(id);
    }
}
