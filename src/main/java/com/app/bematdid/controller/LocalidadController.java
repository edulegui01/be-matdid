package com.app.bematdid.controller;

import com.app.bematdid.dto.LocalidadDTO;
import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.model.Localidad;
import com.app.bematdid.service.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocalidadController {

    @Autowired
    private LocalidadService localidadService;



    @GetMapping("localidad/listar")
    public Page<LocalidadDTO> listar(Pageable pageable, @RequestParam String nombre ){



        return localidadService.listar(pageable, nombre);

    }

    @PostMapping("localidad/guardar")
    public Localidad guardar(@RequestBody Localidad localidad){
        localidadService.guardar(localidad);
        return localidad;
    }

    @PostMapping("localidad/guardarlista")
    public List<Localidad> guardar(@RequestBody List<Localidad> localidades){
        for (Localidad localidad:localidades
             ) {
            localidadService.guardar(localidad);
        }
        return localidades;
    }
}
