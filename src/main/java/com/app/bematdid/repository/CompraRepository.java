package com.app.bematdid.repository;

import com.app.bematdid.model.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query(value = "SELECT id_compra, fecha, id_funcionario, c.id_persona, monto_total, num_folio, c.estado,c.timbrado,c.tipo_factura,c.fecha_vencimiento   " +
            "FROM compra c JOIN persona p ON c.id_persona = p.id_persona " +
            "WHERE c.estado is true and p.nombre_encargado ilike %:nombrePersonaFilter% and c.num_folio ilike %:numFolioFilter% " +
            "order by id_compra desc;", nativeQuery = true)
    Page<Compra> listarCompras (Pageable pageable, @Param("nombrePersonaFilter") String nombrePersona, @Param("numFolioFilter") String numFolio);
}
