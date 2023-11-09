package com.app.bematdid.repository;

import com.app.bematdid.model.Producto;
import com.app.bematdid.model.Timbrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimbradoRepository extends JpaRepository<Timbrado, Long> {
    @Query(value = "SELECT * FROM timbrado where fecha_vencimiento is null;", nativeQuery = true)
    Timbrado getTimbradoValido();
}
