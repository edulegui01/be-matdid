package com.app.bematdid.service;



import com.app.bematdid.model.Factura;
import com.app.bematdid.repository.FacturaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class EstadisticaService {

    @Autowired
    private EntityManager em;

    @Autowired
    private FacturaRepository facturaRepository;





    public  Map<String,List> cantidadProductos(LocalDate fechaDesde, LocalDate fechaHasta){

        java.sql.Date sqlDesde = java.sql.Date.valueOf(fechaDesde);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(fechaHasta.plusDays(1));

        Query nativeQuery = em.createNativeQuery(
                "select p.id_producto, p.nombre, sum(df.cantidad) as cantidad\n" +
                        "from detalle_factura df\n" +
                        "join producto p\n" +
                        "\ton df.id_producto = p.id_producto\n" +
                        "join factura f\n" +
                        "\ton df.id_factura = f.id_factura\n" +
                        "where f.fecha between :fechaDesde and :fechaHasta and f.estado !='A'\n" +
                        "group by p.id_producto order by cantidad desc"
        ).setParameter("fechaDesde",sqlDesde).setParameter("fechaHasta",sqlHasta);
         List<Object[]> result = nativeQuery.getResultList();

        Map<String,List> map = new HashMap<>();

         List<String> nombresProductosCargar = new ArrayList<>();
         List<String> nombresProductos = new ArrayList<>();
         List<Long> cantidadProductosCargar = new ArrayList<>();
         List<Long> cantidadProductos = new ArrayList<>();

        result.stream().forEach(item ->{
            nombresProductosCargar.add((String) item[1]);
            cantidadProductosCargar.add((Long) item[2]);
        });

        if(nombresProductosCargar.size()>=10){
            nombresProductos = nombresProductosCargar.subList(0,10);
        }

        if(cantidadProductosCargar.size()>=10){
            cantidadProductos = cantidadProductosCargar.subList(0,10);
        }

        map.put("productos",nombresProductos.isEmpty() ? nombresProductosCargar : nombresProductos);
        map.put("cantidad",cantidadProductos.isEmpty() ? cantidadProductosCargar : cantidadProductos);

        return map;








    }

    public Map<String,List> clientesCantidad(LocalDate fechaDesde, LocalDate fechaHasta){
        java.sql.Date sqlDesde = java.sql.Date.valueOf(fechaDesde);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(fechaHasta.plusDays(1));


        Query nativeQuery = em.createNativeQuery(
                "select f.id_persona, sum(f.monto_total) as cantidad, p.razon_social\n" +
                        "from factura f\n" +
                        "join persona p\n" +
                        "\ton f.id_persona = p.id_persona\n" +
                        "where f.fecha between :fechaDesde and :fechaHasta and f.estado !='A'\n" +
                        "group by f.id_persona, p.razon_social\n" +
                        "order by cantidad desc"
        ).setParameter("fechaDesde",sqlDesde).setParameter("fechaHasta",sqlHasta);

        List<Object[]> result = nativeQuery.getResultList();

        Map<String,List> map = new HashMap<>();

        List<BigDecimal> montoTotalCargar = new ArrayList<>();
        List<BigDecimal> montoTotal = new ArrayList<>();
        List<String> clientesCargar = new ArrayList<>();
        List<String> clientes = new ArrayList<>();

        result.stream().forEach(item ->{
            montoTotalCargar.add((BigDecimal) item[1]);
            clientesCargar.add((String) item[2]);
        });

        if(montoTotalCargar.size()>=10){
            montoTotal = montoTotalCargar.subList(0,10);
        }

        if(clientesCargar.size()>=10){
            clientes = clientesCargar.subList(0,10);
        }

        map.put("montoTotal",montoTotal.isEmpty() ? montoTotalCargar : montoTotal);
        map.put("clientes",clientes.isEmpty() ? clientesCargar : clientes);

        return map;

    }

    public Map<String,List> cobradoPorMes(Integer anho){
        Query nativeQuery = em.createNativeQuery(
                "select sum(monto) monto, extract(month from fecha) as mes\n" +
                        "from cobro\n" +
                        "where estado != 'A' and extract(YEAR from fecha) = :anho\n" +
                        "group by extract(month from fecha) order by mes"
        ).setParameter("anho",anho);

        List<Object[]> result = nativeQuery.getResultList();

        Long[] array = {0L, 0L, 0L, 0L, 0L, 0L,0L, 0L, 0L, 0L, 0L, 0L};

        Map<String,List> map = new HashMap<>();

        List<Long> monto = new ArrayList<>(Arrays.asList(array));
        List<Long> montoCopy = new ArrayList<>();
        List<BigDecimal> mes = new ArrayList<>();

        result.stream().forEach(item ->{
            montoCopy.add((Long) item[0]);
            mes.add((BigDecimal) item[1]);
        });

        for (int i =0; i < mes.size(); i++){

            monto.set(mes.get(i).intValue() -1,montoCopy.get(i));
        };



        /*for (Integer i = 0; i <= 13; i++) {
            if( i == mes.get(i)){
                monto.add(i, monto.get(i));
            }else{
                monto.add(i, 0L);
            }
        }*/

        map.put("monto",monto);
        map.put("mes",mes);

        return map;
    }


    public Map<String,List> pagadoPorMes(Integer anho){
        Query nativeQuery = em.createNativeQuery(
                "select sum(monto) monto, extract(month from fecha) as mes\n" +
                        "from pago\n" +
                        "where estado != 'A' and extract(YEAR from fecha) = :anho\n" +
                        "group by extract(month from fecha) order by mes"
        ).setParameter("anho",anho);

        List<Object[]> result = nativeQuery.getResultList();
        Long[] array = {0L, 0L, 0L, 0L, 0L, 0L,0L, 0L, 0L, 0L, 0L, 0L};
        Map<String,List> map = new HashMap<>();

        List<Long> monto = new ArrayList<>(Arrays.asList(array));
        List<Long> montoCopy = new ArrayList<>();
        List<BigDecimal> mes = new ArrayList<>();

        result.stream().forEach(item ->{
            montoCopy.add((Long) item[0]);
            mes.add((BigDecimal) item[1]);
        });

        for (int i =0; i < mes.size(); i++){

            monto.set(mes.get(i).intValue() -1,montoCopy.get(i));
        };

        map.put("monto",monto);
        map.put("mes",mes);

        return map;
    }




}
