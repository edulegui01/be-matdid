package com.app.bematdid.repository;



import com.app.bematdid.model.Autor;
import com.app.bematdid.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {

    @Query(value = "SELECT * FROM autor where estado is true and nombre ilike %:nombreFilter% order by nombre asc;", nativeQuery = true)
    Optional<List<Autor>> listarAutores(@Param("nombreFilter") String nombreFilter);

    @Query(value = "SELECT * FROM autor where estado is true and idCiclo = :idCiclo",nativeQuery = true)
    List<Autor> listarAutoresByCiclo(@Param("idCiclo") Long idCiclo);
}
