package com.app.bematdid.controller;

import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.service.PersonaService;
import com.app.bematdid.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("producto/listar")
    public Page<ProductoDTO> listar(Pageable pageable,@RequestParam String nombre ){



        return productoService.listar(pageable, nombre);

    }
}
