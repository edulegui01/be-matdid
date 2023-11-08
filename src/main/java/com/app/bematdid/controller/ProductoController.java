package com.app.bematdid.controller;

import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
import com.app.bematdid.service.PersonaService;
import com.app.bematdid.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("producto/listar")
    public Page<ProductoDTO> listar(Pageable pageable,@RequestParam String nombre ){

        return productoService.listar(pageable, nombre);

    }

    @GetMapping("producto/listar_select")
    public List<Producto> listarSelect(@RequestParam String search ){



        return productoService.listarSelect(search);

    }

    @PostMapping("producto/guardar")
    public Producto guardar(@RequestBody Producto producto){

        productoService.guardar(producto);

        return producto;
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PutMapping("producto/actualizar/{idPer}")
    public Producto actualizar(@RequestBody Producto prodcutoModi,@PathVariable Long idPer){

        return productoService.actualizar(prodcutoModi,idPer);

    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("producto/borrar/{idPer}")
    public void borrar(@PathVariable Long idPer){
        productoService.borrar(idPer);

    }
}
