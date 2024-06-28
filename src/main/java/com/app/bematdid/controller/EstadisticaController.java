package com.app.bematdid.controller;


import com.app.bematdid.model.Factura;
import com.app.bematdid.service.EstadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class EstadisticaController {

    @Autowired
    private EstadisticaService estadisticaService;


    @GetMapping("estadistica/cantidad/producto")
    public Map<String,List> cantidadProducto(@RequestParam LocalDate fechaDesde, @RequestParam LocalDate fechaHasta){

         return estadisticaService.cantidadProductos(fechaDesde,fechaHasta);
    }

    @GetMapping("estadistica/cantidad/ventas")
    public Map<String,List> cantidadVentas(@RequestParam LocalDate fechaDesde, @RequestParam LocalDate fechaHasta){


        return estadisticaService.clientesCantidad(fechaDesde,fechaHasta);
    }

    @GetMapping("estadistica/cobrado/mes")
    public Map<String,List> cobradoMes(@RequestParam Integer anho){


        return estadisticaService.cobradoPorMes(anho);
    }

    @GetMapping("estadistica/pagado/mes")
    public Map<String,List> pagadoMes(@RequestParam Integer anho){


        return estadisticaService.pagadoPorMes(anho);
    }
}
