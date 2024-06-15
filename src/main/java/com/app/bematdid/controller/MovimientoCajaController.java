package com.app.bematdid.controller;

import com.app.bematdid.dto.CajaDTO;
import com.app.bematdid.dto.MovimientoCajaDTO;
import com.app.bematdid.model.Localidad;
import com.app.bematdid.model.Movimiento;
import com.app.bematdid.service.MovimientoCajaService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MovimientoCajaController {

    @Autowired
    private MovimientoCajaService movimientoCajaService;

    @GetMapping("movimiento-caja/listar")
    public Page<MovimientoCajaDTO> listar (Pageable pageable, @RequestParam String beneficiario, @RequestParam String comentario) {
        return movimientoCajaService.listar(pageable, beneficiario, comentario);
    }


    @GetMapping("movimiento-caja/{id}")
    private Optional<MovimientoCajaDTO> obtenerPorId (@PathVariable("id") Long id){
        return movimientoCajaService.obtenerPorId(id);
    }

    @GetMapping("movimiento-caja/listar-total")
    public List<ObjectNode> listarMovimientoCajaTotal(){
        return movimientoCajaService.listarTodosLosMovimientosCaja();
    }
    @GetMapping("movimiento-caja/saldo-disponible")
    public Map<String, Integer> saldoDisponible() {
        return movimientoCajaService.saldoDisponible();
    }

    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("movimiento-caja/cerrar-caja")
    public MovimientoCajaDTO cerrarCaja(@RequestBody MovimientoCajaDTO movimientoCajaDTO){
        movimientoCajaService.cerrarCaja();
        return movimientoCajaService.guardar(movimientoCajaDTO);
    }




    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("movimiento-caja/guardar")
    public MovimientoCajaDTO guardar(@RequestBody MovimientoCajaDTO movimientoCajaDTO) {
        System.out.println(movimientoCajaDTO.getFecha());
        return movimientoCajaService.guardar(movimientoCajaDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("movimiento-caja/borrar/{id}")
    public void borrar (@PathVariable("id") Long id) {
        movimientoCajaService.borrar(id);
    }
}
