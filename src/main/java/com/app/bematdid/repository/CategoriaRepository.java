package com.app.bematdid.repository;

import com.app.bematdid.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    @Query(value = "SELECT * FROM categoria where estado is true and nombre ilike %:nombreFilter% order by nombre asc;", nativeQuery = true)
    Optional<List<Categoria>> listarCategorias(@Param("nombreFilter") String nombreFilter);

    @Query(value = "SELECT * FROM categoria where estado is true and idCiclo = :idCiclo",nativeQuery = true)
    List<Categoria> listarCategoriaByCiclo(@Param("idCiclo") Long idCiclo);
}
