package com.app.bematdid.repository;

import com.app.bematdid.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad,Long> {

    List<Localidad> findAllByOrderByDescripcionDistrito();

}
