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

    public Map<String,Integer> cantidadPorAnho(String anho) {
        Map<String, Integer> datos = new HashMap<String,Integer>();
        datos.put("enero", facturaRepository.vendidosPorFecha(anho + "-01-01T00:00:00",anho + "-01-31T23:59:59"));
        datos.put("febrero", facturaRepository.vendidosPorFecha(anho + "-02-01T00:00:00",anho + "-03-01T00:00:00"));
        datos.put("marzo", facturaRepository.vendidosPorFecha(anho + "-03-01T00:00:00",anho + "-03-31T23:59:59"));
        datos.put("abril", facturaRepository.vendidosPorFecha(anho + "-04-01T00:00:00",anho + "-04-30T23:59:59"));
        datos.put("mayo", facturaRepository.vendidosPorFecha(anho + "-05-01T00:00:00",anho + "-05-31T23:59:59"));
        datos.put("junio", facturaRepository.vendidosPorFecha(anho + "-06-01T00:00:00",anho + "-06-30T23:59:59"));
        datos.put("julio", facturaRepository.vendidosPorFecha(anho + "-07-01T00:00:00",anho + "-07-31T23:59:59"));
        datos.put("agosto", facturaRepository.vendidosPorFecha(anho + "-08-01T00:00:00",anho + "-08-31T23:59:59"));
        datos.put("septiembre", facturaRepository.vendidosPorFecha(anho + "-09-01T00:00:00",anho + "-09-30T23:59:59"));
        datos.put("octubre", facturaRepository.vendidosPorFecha(anho + "-10-01T00:00:00",anho + "-10-31T23:59:59"));
        datos.put("noviembre", facturaRepository.vendidosPorFecha(anho + "-11-01T00:00:00",anho + "-11-30T23:59:59"));
        datos.put("diciembre", facturaRepository.vendidosPorFecha(anho + "-12-01T00:00:00",anho + "-12-31T23:59:59"));
        return datos;
    }



}
