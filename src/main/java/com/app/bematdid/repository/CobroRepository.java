package com.app.bematdid.repository;

import com.app.bematdid.model.Cobro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CobroRepository extends JpaRepository<Cobro, Long> {

    @Query(value = "SELECT * FROM cobro where (estado = 'ABIERTO' OR estado = 'CERRADO') and id_factura = :idFactura order by fecha desc;", nativeQuery = true)
    List<Cobro> listarPorIdVenta(@Param("idFactura") Long idFactura);

    @Query(value = "SELECT * FROM cobro where estado = 'ABIERTO'",nativeQuery = true)
    List<Cobro> listarAbiertos();

    @Query(value = "SELECT * FROM cobro where (estado = 'ABIERTO' OR estado = 'CERRADO') and (fecha BETWEEN :fechaInicio AND :fechaFin)", nativeQuery = true)
    List<Cobro> listarPorFecha(@Param("fechaInicio") Date fechaInico, @Param("fechaFin") Date fechafin);

    @Query(value = "SELECT id_cobro, comentario, c.estado, c.fecha, c.id_factura, c.id_funcionario, monto, tipo_cobro\n" +
            "FROM cobro c\n" +
            "JOIN factura f\n" +
            "ON c.id_factura = f.id_factura\n" +
            "WHERE (c.estado = 'ABIERTO' OR c.estado = 'CERRADO') AND (c.fecha BETWEEN :fechaInicio AND :fechaFin) AND f.id_persona = :id", nativeQuery = true)
    List<Cobro> listarPorFechaCliente(@Param("fechaInicio") Date fechaInico, @Param("fechaFin") Date fechafin, @Param("id")Long id);

    @Query(value ="SELECT coalesce(sum(monto),0) FROM cobro WHERE estado = 'ABIERTO'", nativeQuery = true)
    Integer montoTotal();

    @Query(value ="SELECT coalesce(sum(monto),0) FROM cobro WHERE estado = 'ABIERTO' AND tipo_cobro =:tipo", nativeQuery = true)
    Integer montoTotalTipo(@Param("tipo") String tipo);
}
