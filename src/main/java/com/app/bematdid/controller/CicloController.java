package com.app.bematdid.controller;

import com.app.bematdid.dto.CicloDTO;
import com.app.bematdid.model.Ciclo;
import com.app.bematdid.model.Localidad;
import com.app.bematdid.service.CicloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CicloController {

    @Autowired
    private CicloService cicloService;

    @GetMapping("ciclo/listar")
    public Page<CicloDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return cicloService.listar(pageable, nombre);
    }

    @GetMapping("ciclo_select/listar")
    public List<CicloDTO> listarSelect(){

        return cicloService.listarTodos();

    }

    @GetMapping("ciclo/{id}")
    private Optional<CicloDTO> obtenerPorId (@PathVariable("id") Integer id){
        return cicloService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("ciclo/guardar")
    public CicloDTO guardar(@RequestBody CicloDTO cicloDTO) {
        return cicloService.guardar(cicloDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("ciclo/borrar/{id}")
    public void borrar (@PathVariable("id") Integer id) {
        cicloService.borrar(id);
    }
}
