package com.app.bematdid.controller;

import com.app.bematdid.dto.CompraDTO;
import com.app.bematdid.dto.FacturaDTO;
import com.app.bematdid.dto.FolioDTO;
import com.app.bematdid.model.Factura;
import com.app.bematdid.model.NumeracionFolio;
import com.app.bematdid.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class FacturaController {

    @Autowired
    private FacturaService facturaService;


    @GetMapping("factura/listar")
    public Page<FacturaDTO> listar (Pageable pageable, @RequestParam String nombrePersona, @RequestParam String numFactura) {
        return facturaService.listar(pageable, nombrePersona, numFactura);
    }

    @GetMapping("factura/folio")
    public  Map<String, String> getNumFactura () {
        return facturaService.getLastFolio();
    }

    @GetMapping("factura/numeracion")
    public NumeracionFolio getNumFolio () {
        return facturaService.getNumeracionFolio();
    }

    @PostMapping("factura/guardar")
    public FacturaDTO guardar (@RequestBody FacturaDTO facturaDTO) {

        return facturaService.guardar(facturaDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("factura/anular/{idFactura}")
    public void anular (@PathVariable Long idFactura){
        facturaService.borrar(idFactura);
    }
}
