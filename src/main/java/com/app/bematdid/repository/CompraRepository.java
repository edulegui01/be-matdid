package com.app.bematdid.repository;

import com.app.bematdid.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query(value = "SELECT id_compra, fecha, id_funcionario, c.id_persona, monto_total, num_folio, c.estado   " +
            "FROM compra c JOIN persona p ON c.id_persona = p.id_persona " +
            "WHERE c.estado is true and p.nombre ilike %:nombrePersonaFilter% and c.num_folio ilike %:numFolioFilter% " +
            "order by id_compra desc;", nativeQuery = true)
    Optional<List<Compra>> listarCompras (@Param("nombrePersonaFilter") String nombrePersona, @Param("numFolioFilter") String numFolio);
}
