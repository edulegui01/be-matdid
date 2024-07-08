package com.app.bematdid.repository;

import com.app.bematdid.model.MovimientoCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Long> {

    @Query(value = "SELECT * FROM movimiento_caja" +
            "where (estado = 'ABIERTO' OR estado = 'CERRADO') and beneficiario ilike %:beneficiarioFilter% and comentario ilike %:comentarioFilter% " +
            "order by id_gasto desc",nativeQuery = true)
    Optional<List<MovimientoCaja>> listarMovimientosCaja(
                                                @Param("beneficiarioFilter") String beneficiarioFilter,
                                                @Param("comentarioFilter") String comentarioFilter);
    @Query(value = "SELECT * FROM movimiento_caja where estado = 'ABIERTO'",nativeQuery = true)
    List<MovimientoCaja> listarAbiertos();

    @Query(value ="SELECT coalesce(sum(m.monto),0)\n" +
            "FROM movimiento_caja m JOIN concepto c ON m.id_concepto = c.id_concepto\n" +
            "WHERE m.estado = 'ABIERTO' AND c.es_ingreso = 'E'",nativeQuery = true)
    Integer egresoTotal();

    @Query(value ="SELECT coalesce(sum(m.monto),0)\n" +
            "FROM movimiento_caja m JOIN concepto c ON m.id_concepto = c.id_concepto\n" +
            "WHERE m.estado = 'ABIERTO' AND c.es_ingreso = 'I'",nativeQuery = true)
    Integer ingresoTotal();

    @Query(value ="SELECT coalesce(sum(m.monto),0)\n" +
            "FROM movimiento_caja m JOIN concepto c ON m.id_concepto = c.id_concepto\n" +
            "WHERE m.estado = 'ABIERTO' AND c.es_ingreso = 'E' AND m.tipo_pago =':tipo'",nativeQuery = true)
    Integer egresoTotalTipo(@Param("tipo") String tipo);

    @Query(value ="SELECT coalesce(sum(m.monto),0)\n" +
            "FROM movimiento_caja m JOIN concepto c ON m.id_concepto = c.id_concepto\n" +
            "WHERE m.estado = 'ABIERTO' AND c.es_ingreso = 'I' AND m.tipo_pago = ':tipo'",nativeQuery = true)
    Integer ingresoTotalTipo(@Param("tipo") String tipo);
}
