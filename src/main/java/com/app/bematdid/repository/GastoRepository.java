package com.app.bematdid.repository;

import com.app.bematdid.model.Gasto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

    @Query(value = "SELECT * FROM gasto " +
            "where (estado is true) and categoria ilike %:categoriaFilter% and beneficiario ilike %:beneficiarioFilter% and comentario ilike %:comentarioFilter% " +
            "order by id_gasto desc",nativeQuery = true)
    Optional<List<Gasto>> listarGastos(@Param("categoriaFilter") String categoriaFilter,
                                       @Param("beneficiarioFilter") String beneficiarioFilter,
                                       @Param("comentarioFilter") String comentarioFilter);
}
