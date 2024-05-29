package com.app.bematdid.controller;

import com.app.bematdid.dto.CobroDTO;
import com.app.bematdid.error.SaldoNegativeException;
import com.app.bematdid.service.CobroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CobroController {

    @Autowired
    private CobroService cobroService;

    /*@GetMapping("cobro/listar")
    public Page<CobroDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return cobroService.listar(pageable, nombre);
    }*/

    @GetMapping("cobro/listarTodos")
    public List<CobroDTO> listarTodos(){

        return cobroService.listarTodos();

    }

    @GetMapping("cobro/{id}")
    private Optional<CobroDTO> obtenerPorId (@PathVariable("id") Long id){
        return cobroService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("cobro/guardar")
    public CobroDTO guardar(@RequestBody CobroDTO cobroDTO) throws SaldoNegativeException {
        return cobroService.guardar(cobroDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("cobro/borrar/{id}")
    public void borrar (@PathVariable("id") Long id) {
        cobroService.borrar(id);
    }
}
