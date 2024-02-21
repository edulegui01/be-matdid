package com.app.bematdid.repository;

import com.app.bematdid.model.Movimiento;
import com.app.bematdid.model.NumeracionFolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NumeracionFolioRepository extends JpaRepository<NumeracionFolio,Long> {


    @Query(value = "select * from numeracion_folio where activo is true",nativeQuery = true)
    NumeracionFolio getNumFolio();
}
