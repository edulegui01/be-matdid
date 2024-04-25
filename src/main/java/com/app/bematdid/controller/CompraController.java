package com.app.bematdid.controller;

import com.app.bematdid.dto.CompraDTO;
import com.app.bematdid.model.Compra;
import com.app.bematdid.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping("compra/todo")
    public List<CompraDTO> listarTodo () {
        return compraService.listarTodo();
    }

    @GetMapping("compra/listar")
    public Page<CompraDTO> listar (Pageable pageable, @RequestParam String nombrePersona, @RequestParam String numFolio) {
        return compraService.listar(pageable, nombrePersona, numFolio);
    }

    @PostMapping("compra/guardar")
    public CompraDTO guardar (@RequestBody CompraDTO compraDTO) {
        return compraService.guardar(compraDTO);
    }

    //@CrossOrigin("origins = http://localhost:4200")
    @PutMapping("compra/actualizar")
    public CompraDTO actualizar (@RequestBody CompraDTO compraDTO){
        return compraService.actualizar(compraDTO);
    }
    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("compra/borrar/{idCompra}")
    public void borrar (@PathVariable Long idCompra){
        compraService.borrar(idCompra);
    }


}
