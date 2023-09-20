package com.app.bematdid.repository;

import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    @Query(value = "SELECT * FROM producto where estado is true and nombre ilike %:nombre% ;", nativeQuery = true)
    Page<Producto> listarProducto(Pageable pageable,@Param("nombre") String nombre);
}
