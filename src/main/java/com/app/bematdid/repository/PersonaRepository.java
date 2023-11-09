package com.app.bematdid.repository;


import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
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

    @Query(value = "SELECT * FROM persona where (estado is true and es_cliente = :esCliente) and (cast(cedula as varchar) ilike %:cedulaFilter% or ruc ilike %:cedulaFilter%) " +
            "and nombre ilike %:nombreFilter% order by id_persona desc",nativeQuery = true)
    Page<Persona> listarPersonas(Pageable pageable,@Param("cedulaFilter") String cedulaFilter,@Param("nombreFilter") String nombreFilter,@Param("esCliente") boolean esCliente);


    @Query(value = "select id_persona, cedula, direccion, email, es_cliente, p.estado, p.nombre, nombre_encargado, razon_social, ruc, sector, telefono, id_localidad\n" +
            "from persona p\n" +
            "join localidad l\n" +
            "    on p.id_localidad = l.id",nativeQuery = true)
    List<Persona> listarPrueba();


    @Query(value = "SELECT * FROM persona where estado is true and razon_social ilike %:razonSocial% ;", nativeQuery = true)
    List<Persona> listarSelect(@Param("razonSocial") String razonSocial);

    Persona findByIdPersona(Long idPersona);


}
