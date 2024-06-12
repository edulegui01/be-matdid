package com.app.bematdid.service;

import com.app.bematdid.dto.CajaDTO;
import com.app.bematdid.dto.LocalidadDTO;
import com.app.bematdid.dto.MovimientoCajaDTO;
import com.app.bematdid.mapper.MovimientoCajaMapper;
import com.app.bematdid.model.Localidad;
import com.app.bematdid.model.MovimientoCaja;
import com.app.bematdid.repository.MovimientoCajaRepository;
import com.app.bematdid.repository.PagoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovimientoCajaService {

    @Autowired
    private MovimientoCajaRepository movimientoCajaRepository;

    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private MovimientoCajaMapper mapper;

    @Autowired
    private EntityManager em;

    public Page<MovimientoCajaDTO> listar (Pageable pageable, String beneficiario, String comentario) {
        Optional<List<MovimientoCajaDTO>> lista = movimientoCajaRepository.listarMovimientosCaja( beneficiario, comentario).map(movimientoCajas -> mapper.movimientosCajaAMovimientosCajaDTO(movimientoCajas));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());

    }

    public List<ObjectNode> listarTodosLosMovimientosCaja(){
        Query nativeQuery = em.createNativeQuery("select p.id_pago as id,p.fecha, p.monto, 'PAGO DE MERCADERIA' as concepto,'E' as esIngreso, c.num_folio as comprobante \n" +
                "from pago p\n" +
                "join compra c on p.id_compra = c.id_compra\n" +
                "where p.estado is true\n" +
                "UNION\n" +
                "SELEct c.id_cobro as id,c.fecha, c.monto, 'COBRO DE VENTA' as concepto,'I' as esIngreso, f.num_factura as comprobante from cobro c\n" +
                "join factura f on c.id_factura = f.id_factura\n" +
                "where c.estado is true\n" +
                "UNION\n" +
                "SELECT mc.id_movimiento_caja as id,mc.fecha, mc.cantidad, c.nombre as concepto, c.es_ingreso as esIngreso, mc.comprobante as comprobante FROM movimiento_caja mc\n" +
                "join concepto c on mc.id_concepto = c.id_concepto\n" +
                "where mc.estado is true\n" +
                "order by fecha desc, id desc", Tuple.class);

        List<Tuple> resulsts = nativeQuery.getResultList();

        List<ObjectNode> json = _toJson(resulsts);

        return json;

    }

    public Map<String,Integer> montoTotal() {
        Map<String, Integer> montosTotales = new HashMap<String,Integer>();
        montosTotales.put("totalPagos",pagoRepository.montoTotal());
        return montosTotales;
    }



    private List<ObjectNode> _toJson(List<Tuple> results) {

        List<ObjectNode> json = new ArrayList<ObjectNode>();

        ObjectMapper mapper = new ObjectMapper();

        for (Tuple t : results)
        {
            List<TupleElement<?>> cols = t.getElements();

            ObjectNode one = mapper.createObjectNode();

            for (TupleElement col : cols)
            {
                one.put(col.getAlias(), t.get(col.getAlias()).toString());
            }

            json.add(one);
        }

        return json;
    }

    public Optional<MovimientoCajaDTO> obtenerPorId (Long id){
        return movimientoCajaRepository.findById(id).map(movimientoCaja -> mapper.movimientoCajaAMovimientoCajaDTO(movimientoCaja));
    }

    public MovimientoCajaDTO guardar(MovimientoCajaDTO movimientoCajaDTO) {
        MovimientoCaja movimientoCaja = mapper.movimientoCajaDTOAMovimientoCaja(movimientoCajaDTO);
        return mapper.movimientoCajaAMovimientoCajaDTO(movimientoCajaRepository.save(movimientoCaja));
    }

    public void borrar(Long id){
        Optional<MovimientoCaja> movimientoCaja = movimientoCajaRepository.findById(id);

        MovimientoCaja movimientoCaja1 = movimientoCaja.get();

        movimientoCaja1.setEstado(false);

        movimientoCajaRepository.save(movimientoCaja1);
    }


}
