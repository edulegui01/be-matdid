package com.app.bematdid.repository;

import com.app.bematdid.model.Localidad;
import com.app.bematdid.model.MovimientoCaja;
import com.app.bematdid.model.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query(value = "SELECT * FROM pago where (estado = 'ABIERTO' OR estado = 'CERRADO') and id_compra = :idCompra order by fecha desc;", nativeQuery = true)
    List<Pago> listarPorIdCompra(@Param("idCompra") Long idCompra);

    @Query(value = "SELECT * FROM pago where estado = 'ABIERTO'",nativeQuery = true)
    List<Pago> listarAbiertos();

    @Query(value = "SELECT * FROM pago where (estado = 'ABIERTO' OR estado = 'CERRADO') and (fecha BETWEEN :fechaInicio AND :fechaFin)", nativeQuery = true)
    List<Pago> listarPorFecha(@Param("fechaInicio") Date fechaInico, @Param("fechaFin") Date fechafin);

    @Query(value = "SELECT id_pago, comentario, p.estado, p.fecha, p.id_compra, p.id_funcionario, monto, tipo_pago\n" +
            "FROM pago p\n" +
            "JOIN compra c\n" +
            "ON p.id_compra = c.id_compra\n" +
            "where (p.estado = 'ABIERTO' OR p.estado = 'CERRADO') and (p.fecha BETWEEN :fechaInicio AND :fechaFin) AND c.id_persona = :id", nativeQuery = true)
    List<Pago> listarPorFechaProveedor(@Param("fechaInicio") Date fechaInico, @Param("fechaFin") Date fechafin, Long id);

    @Query(value ="SELECT coalesce(sum(monto),0) FROM pago WHERE estado = 'ABIERTO'", nativeQuery = true)
    Integer montoTotal();

    @Query(value ="SELECT coalesce(sum(monto),0) FROM pago WHERE estado = 'ABIERTO' AND tipo_pago =':tipo'", nativeQuery = true)
    Integer montoTotalTipo(@Param("tipo") String tipo);
}
