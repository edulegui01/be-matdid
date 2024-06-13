package com.app.bematdid.repository;

import com.app.bematdid.model.Localidad;
import com.app.bematdid.model.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query(value = "SELECT * FROM pago where estado is true and id_compra = :idCompra order by fecha desc;", nativeQuery = true)
    List<Pago> listarPorIdCompra(@Param("idCompra") Long idCompra);

    @Query(value ="SELECT coalesce(sum(monto),0) FROM pago WHERE estado is true", nativeQuery = true)
    Integer montoTotal();
}
