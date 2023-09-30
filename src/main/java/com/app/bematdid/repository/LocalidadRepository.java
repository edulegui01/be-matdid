package com.app.bematdid.repository;

import com.app.bematdid.model.Localidad;
import com.app.bematdid.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad,Long> {

    @Query(value = "SELECT * FROM localidad where estado is true and nombre ilike %:nombre% ;", nativeQuery = true)
    Page<Localidad> listarLocalidad(Pageable pageable, @Param("nombre") String nombre);

}
