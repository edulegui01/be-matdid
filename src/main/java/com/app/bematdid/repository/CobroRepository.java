package com.app.bematdid.repository;

import com.app.bematdid.model.Cobro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CobroRepository extends JpaRepository<Cobro, Long> {

    @Query(value = "SELECT * FROM cobro where estado is true and id_factura = :idFactura order by fecha desc;", nativeQuery = true)
    List<Cobro> listarPorIdVenta(@Param("idFactura") Long idFactura);
}
