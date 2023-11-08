package com.app.bematdid.repository;

import com.app.bematdid.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @Query(value = "SELECT id_factura, fecha, id_funcionario, f.id_persona, nro_timbrado, monto_total, num_factura, f.estado   " +
            "FROM factura f JOIN persona p ON f.id_persona = p.id_persona " +
            "WHERE f.estado is true and p.nombre ilike %:nombrePersonaFilter% and cast(f.num_factura as varchar) ilike %:numFacturaFilter% " +
            "order by id_factura desc;", nativeQuery = true)
    Optional<List<Factura>> listarFacturas (@Param("nombrePersonaFilter") String nombrePersona, @Param("numFacturaFilter") String numFactura);
}
