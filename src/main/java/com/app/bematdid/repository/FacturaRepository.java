package com.app.bematdid.repository;

import com.app.bematdid.model.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @Query(value = "SELECT id_factura, fecha, fecha_vencimiento,id_funcionario, f.id_persona, num_factura,id_folio,id_timbrado, monto_total, saldo, f.estado, f.tipo_factura  " +
            "FROM factura f JOIN persona p ON f.id_persona = p.id_persona " +
            "WHERE p.razon_social ilike %:nombrePersonaFilter% and cast(f.num_factura as varchar) ilike %:numFacturaFilter% " +
            "order by id_factura desc;", nativeQuery = true)
    Page<Factura> listarFacturas (Pageable pageable, @Param("nombrePersonaFilter") String nombrePersona, @Param("numFacturaFilter") String numFactura);

    @Query(value ="SELECT coalesce(sum(monto_total),0) FROM factura WHERE estado != 'A'", nativeQuery = true)
    Integer totalVendido();

    @Query(value ="SELECT coalesce(sum(saldo),0) FROM factura WHERE estado != 'A'", nativeQuery = true)
    Integer porCobrar();

    @Query(value ="SELECT COALESCE(SUM(d.cantidad), 0)\n" +
            "FROM factura f\n" +
            "JOIN detalle_factura d\n" +
            "ON d.id_factura = f.id_factura\n" +
            "WHERE f.estado != 'A' AND (f.fecha BETWEEN :fechaInicio AND :fechaFin)", nativeQuery = true)
    Integer vendidosPorFecha(@Param("fechaInicio") String fechaInico, @Param("fechaFin") String fechafin);




    @Query(value ="SELECT COALESCE(SUM(d.cantidad), 0)\n" +
            "FROM factura f\n" +
            "JOIN detalle_factura d\n" +
            "ON d.id_factura = f.id_factura\n" +
            "WHERE f.estado != 'A' AND (extract(month from f.fecha) = :mes and extract(year from f.fecha) = :anho)", nativeQuery = true)
    Integer vendidosPorMes(@Param("mes") Integer mes,@Param("anho") Integer anho);



    List<Factura> findByFechaBetween(Date des, Date has);

    @Query(value ="SELECT f.id_factura, estado, id_folio, id_funcionario, id_persona, id_timbrado, monto_total, saldo, tipo_factura, num_factura, fecha, fecha_vencimiento\n" +
            "FROM factura f\n" +
            "WHERE f.estado != 'A' AND (f.fecha BETWEEN :fechaInicio AND :fechaFin)", nativeQuery = true)
    List<Factura> facturasPorFecha(@Param("fechaInicio") Date fechaInico, @Param("fechaFin") Date fechafin);

    @Query(value ="SELECT f.id_factura, estado, id_folio, id_funcionario, id_persona, id_timbrado, monto_total, saldo, tipo_factura, num_factura, fecha, fecha_vencimiento\n" +
            "FROM factura f\n" +
            "WHERE f.estado != 'A' AND (f.fecha BETWEEN :fechaInicio AND :fechaFin) AND f.id_persona = :id", nativeQuery = true)
    List<Factura> facturasPorFechaCliente(@Param("fechaInicio") Date fechaInico, @Param("fechaFin") Date fechafin, @Param("id") Long id);


}
