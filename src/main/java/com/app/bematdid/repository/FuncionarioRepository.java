package com.app.bematdid.repository;

import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository  extends JpaRepository<Funcionario,Long> {


    @Query(value = "SELECT * FROM funcionario where (estado is true) and (cast(cedula as varchar) ilike %:cedulaFilter%) " +
            "and nombre ilike %:nombreFilter% order by id_funcionario desc",nativeQuery = true)
    Page<Funcionario> listarFuncionarios(Pageable pageable, @Param("cedulaFilter") String cedulaFilter, @Param("nombreFilter") String nombreFilter);



    @Query(value = "select * from funcionario where concat(nombre,' ',apellido) ilike %:search%",nativeQuery = true)
    List<Funcionario> listarFuncionariosSelect(@Param("search") String search);


    Funcionario findFuncionarioByIdFuncionario(Long idFuncionario);



}
