package com.app.bematdid.repository;

import com.app.bematdid.model.Timbrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimbradoRepository extends JpaRepository<Timbrado, Long> {
    @Query(value = "SELECT * FROM timbrado where activo is true;", nativeQuery = true)
    List<Timbrado> getTimbradoValido();
}
