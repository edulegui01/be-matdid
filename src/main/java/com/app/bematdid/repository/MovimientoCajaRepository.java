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

    @Query(value = "SELECT * FROM gasto " +
            "where (estado is true) and beneficiario ilike %:beneficiarioFilter% and comentario ilike %:comentarioFilter% " +
            "order by id_gasto desc",nativeQuery = true)
    Optional<List<MovimientoCaja>> listarMovimientosCaja(
                                                @Param("beneficiarioFilter") String beneficiarioFilter,
                                                @Param("comentarioFilter") String comentarioFilter);

    @Query(value ="SELECT sum(m.cantidad)\n" +
            "FROM movimiento_caja m JOIN concepto c ON m.id_concepto = c.id_concepto\n" +
            "WHERE m.estado is true AND c.es_ingreso = 'E'",nativeQuery = true)
    Integer egresoTotal();

    @Query(value ="SELECT sum(m.cantidad)\n" +
            "FROM movimiento_caja m JOIN concepto c ON m.id_concepto = c.id_concepto\n" +
            "WHERE m.estado is true AND c.es_ingreso = 'I'",nativeQuery = true)
    Integer ingresoTotal();
}
