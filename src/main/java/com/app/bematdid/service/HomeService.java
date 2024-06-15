package com.app.bematdid.service;

import com.app.bematdid.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private MovimientoCajaRepository movimientoCajaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CobroRepository cobroRepository;

    public Integer vendido() {
        return facturaRepository.totalVendido();
    }

    public Integer comprado() {
        return compraRepository.totalComprado();
    }

    public Integer porCobrar() {
        return facturaRepository.porCobrar();
    }

    public Integer porPagar() {
        return compraRepository.porPagar();
    }

    public Integer ingreso() {
        return cobroRepository.montoTotal() + movimientoCajaRepository.ingresoTotal();
    }

    public Integer egreso() {
        return pagoRepository.montoTotal() + movimientoCajaRepository.egresoTotal();
    }

    public Map<String,Integer> estadisticas() {
        Map<String, Integer> datos = new HashMap<String,Integer>();
        datos.put("totalVendido",vendido());
        datos.put("totalComprado",comprado());
        datos.put("cantidadACobrar",porCobrar());
        datos.put("cantidadAPagar",porPagar());
        datos.put("ingresoEnCaja",ingreso());
        datos.put("egresoEnCaja",egreso());
        datos.put("saldoEnCaja",ingreso() - egreso());

        return datos;
    }



}
