package com.app.bematdid.repository;


import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {



    @Query(value = "SELECT * FROM PERSONA WHERE concat(nombre,' ',apellido) ilike %:filtro%", nativeQuery = true)
    Page<Persona> searchByNombreApellido(Pageable pageable, @Param("filtro") String filtro);

    @Query(value = "SELECT * FROM PERSONA WHERE cast(cedula as varchar) ilike %:filtro% or ruc ilike %:filtro%", nativeQuery = true)
    Page<Persona> searchByCeduOrRuc(Pageable pageable,@Param("filtro") String filtro);

    @Query(value = "SELECT id_persona, estado, cedula, apellido, direccion, nombre, email, es_cliente, ruc, id_localidad, telefono\n" +
            "\tFROM persona where (estado is true and es_cliente = :esCliente) and (cast(cedula as varchar) ilike %:cedulaFilter% or ruc ilike %:cedulaFilter%) " +
            "and concat(nombre,' ',apellido) ilike %:nombreFilter% order by id_persona desc",nativeQuery = true)
    Page<Persona> listarPersonas(Pageable pageable,@Param("cedulaFilter") String cedulaFilter,@Param("nombreFilter") String nombreFilter,@Param("esCliente") boolean esCliente);



    Persona findByIdPersona(Long idPersona);


}
