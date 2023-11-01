package com.app.bematdid.repository;

import com.app.bematdid.model.Factura;
import com.app.bematdid.model.Movimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento,Long> {
    @Query(value = "SELECT * FROM movimiento where estado is true and motivo ilike %:motivo% ;", nativeQuery = true)
    Optional<List<Movimiento>> listarMovimiento(@Param("motivo") String motivo);
}
