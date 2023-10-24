package com.app.bematdid.repository;

import com.app.bematdid.model.Timbrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimbradoRepository extends JpaRepository<Timbrado, Long> {

}
