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

    @Query(value ="SELECT coalesce(sum(monto),0) FROM cobro WHERE estado = 'ABIERTO'", nativeQuery = true)
    Integer montoTotal();
}
