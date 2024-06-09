package com.app.bematdid.controller;

import com.app.bematdid.dto.MovimientoCajaDTO;
import com.app.bematdid.service.MovimientoCajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MovimientoCajaController {

    @Autowired
    private MovimientoCajaService movimientoCajaService;

    @GetMapping("MovimientoCaja/listar")
    public Page<MovimientoCajaDTO> listar (Pageable pageable, @RequestParam String beneficiario, @RequestParam String comentario) {
        return movimientoCajaService.listar(pageable, beneficiario, comentario);
    }


    @GetMapping("MovimientoCaja/{id}")
    private Optional<MovimientoCajaDTO> obtenerPorId (@PathVariable("id") Long id){
        return movimientoCajaService.obtenerPorId(id);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("MovimientoCaja/guardar")
    public MovimientoCajaDTO guardar(@RequestBody MovimientoCajaDTO movimientoCajaDTO) {
        return movimientoCajaService.guardar(movimientoCajaDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("MovimientoCaja/borrar/{id}")
    public void borrar (@PathVariable("id") Long id) {
        movimientoCajaService.borrar(id);
    }
}
