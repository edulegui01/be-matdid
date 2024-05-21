package com.app.bematdid.controller;

import com.app.bematdid.dto.MotivoDTO;
import com.app.bematdid.service.MotivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MotivoController {
    @Autowired
    private MotivoService motivoService;

    @GetMapping("motivo/listar")
    public Page<MotivoDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return motivoService.listar(pageable, nombre);
    }

    @GetMapping("motivo_select/listar")
    public List<MotivoDTO> listarSelect(){

        return motivoService.listarTodos();

    }

    @GetMapping("motivo/{id}")
    private Optional<MotivoDTO> obtenerPorId (@PathVariable("id") Integer id){
        return motivoService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("motivo/guardar")
    public MotivoDTO guardar(@RequestBody MotivoDTO motivoDTO) {
        return motivoService.guardar(motivoDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("motivo/borrar/{id}")
    public void borrar (@PathVariable("id") Integer id) {
        motivoService.borrar(id);
    }
}
