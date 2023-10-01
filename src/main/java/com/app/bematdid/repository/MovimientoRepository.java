package com.app.bematdid.repository;

import com.app.bematdid.model.Localidad;
import com.app.bematdid.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento,Long> {
}
