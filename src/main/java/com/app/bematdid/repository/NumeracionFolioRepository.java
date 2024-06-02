package com.app.bematdid.repository;

import com.app.bematdid.model.Folio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NumeracionFolioRepository extends JpaRepository<Folio,Long> {


    @Query(value = "select * from numeracion_folio where activo is true",nativeQuery = true)
    Folio getNumFolio();
}
