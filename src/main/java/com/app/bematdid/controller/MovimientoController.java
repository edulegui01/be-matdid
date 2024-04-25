package com.app.bematdid.controller;

import com.app.bematdid.dto.FacturaDTO;
import com.app.bematdid.dto.MovimientoDTO;
import com.app.bematdid.dto.MovimientoSaveDTO;
import com.app.bematdid.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("movimiento/listar")
    public Page<MovimientoDTO> listar (Pageable pageable, @RequestParam String motivo) {
        return movimientoService.listar(pageable, motivo);
    }

    @PostMapping("movimiento/guardar")
    public MovimientoDTO guardar (@RequestBody MovimientoSaveDTO movimientoDTO) {
        return movimientoService.guardar(movimientoDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("movimiento/borrar/{idMovimiento}")
    public void borrar (@PathVariable Long idMovimiento){
        movimientoService.borrar(idMovimiento);
    }

}
