package com.app.bematdid.controller;

import com.app.bematdid.dto.PagoDTO;
import com.app.bematdid.error.SaldoNegativeException;
import com.app.bematdid.model.Pago;
import com.app.bematdid.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PagoController {

    @Autowired
    private PagoService pagoService;



    /*@GetMapping("pago/listar")
    public Page<PagoDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return pagoService.listar(pageable, nombre);
    }*/

    @GetMapping("pago/listarTodos")
    public List<PagoDTO> listarTodos(){

        return pagoService.listarTodos();

    }

    @GetMapping("pago/listar")
    public List<PagoDTO> listar(@RequestParam Long idCompra){
        return pagoService.listarPorIdCompra(idCompra);
    }

    @GetMapping("pago/{id}")
    private Optional<PagoDTO> obtenerPorId (@PathVariable("id") Long id){
        return pagoService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("pago/guardar")
    public PagoDTO guardar(@RequestBody PagoDTO pagoDTO) throws SaldoNegativeException {
        return pagoService.guardar(pagoDTO);
    }

    @GetMapping("pago/caja")
    public Object listarPagoCaja(){
        return pagoService.listarPagosCobros();
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("pago/borrar/{id}")
    public void borrar (@PathVariable("id") Long id) {
        pagoService.borrar(id);
    }
}
