package com.app.bematdid.controller;

import com.app.bematdid.dto.CompraDTO;
import com.app.bematdid.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping("compra/todo")
    public List<CompraDTO> listarTodo () {
        return compraService.listarTodo();
    }

    @PostMapping("compra/guardar")
    public CompraDTO guardar (@RequestBody CompraDTO compraDTO) {
        return compraService.guardar(compraDTO);
    }


}
