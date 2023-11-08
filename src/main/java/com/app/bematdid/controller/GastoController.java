package com.app.bematdid.controller;

import com.app.bematdid.dto.GastoDTO;
import com.app.bematdid.dto.TimbradoDTO;
import com.app.bematdid.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @GetMapping("gasto/listar")
    public Page<GastoDTO> listar (Pageable pageable, @RequestParam String categoria, @RequestParam String beneficiario, @RequestParam String comentario) {
        return gastoService.listar(pageable, categoria, beneficiario, comentario);
    }


    @GetMapping("gasto/{id}")
    private Optional<GastoDTO> obtenerPorId (@PathVariable("id") Long id){
        return gastoService.obtenerPorId(id);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("gasto/guardar")
    public GastoDTO guardar(@RequestBody GastoDTO gastoDTO) {
        return gastoService.guardar(gastoDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("gasto/borrar/{id}")
    public void borrar (@PathVariable("id") Long id) {
        gastoService.borrar(id);
    }
}
