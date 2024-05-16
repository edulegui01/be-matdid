package com.app.bematdid.repository;

import com.app.bematdid.model.Ciclo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CicloRepository extends JpaRepository<Ciclo, Integer> {
    @Query(value = "SELECT * FROM ciclo where estado is true and nombre ilike %:nombreFilter% order by nombre asc;", nativeQuery = true)
    Optional<List<Ciclo>> listarCiclos(@Param("nombreFilter") String nombreFilter);
}
