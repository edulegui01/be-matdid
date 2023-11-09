package com.app.bematdid.controller;

import com.app.bematdid.dto.TimbradoDTO;
import com.app.bematdid.model.Timbrado;
import com.app.bematdid.service.TimbradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TimbradoController {
    @Autowired
    private TimbradoService timbradoService;

    @GetMapping("timbrado/listar")
    Page<TimbradoDTO> listar (Pageable pageable) {
        return timbradoService.listarTodo(pageable);
    }
    @GetMapping("timbrado/{id}")
    private Optional<TimbradoDTO> obtenerPorId (@PathVariable("id") Long id){
        return timbradoService.obtenerPorId(id);
    }

    @GetMapping("timbrado/valido")
    public Timbrado getTimbradoValido() {
        return timbradoService.getTimbradoValido();
    }



    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("timbrado/guardar")
    public TimbradoDTO guardar(@RequestBody TimbradoDTO timbradoDTO) {
        return timbradoService.guardar(timbradoDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("timbrado/borrar/{id}")
    public void borrar (@PathVariable("id") Long id) {
        timbradoService.borrar(id);
    }
}
