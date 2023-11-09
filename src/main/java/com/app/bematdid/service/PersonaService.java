package com.app.bematdid.service;


import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.mapper.PersonaMapper;
import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;
    private PersonaMapper personaMapper = new PersonaMapper();
    public Page<PersonaDTO> listar(Pageable pageable,String cedulaFilter,String nombreFilter,boolean esCliente){
        System.out.println(esCliente);
        Page<Persona> resultPage = personaRepository.listarPersonas(pageable,cedulaFilter,nombreFilter,esCliente);



        return personaMapper.mapEntityPageIntoDTOPage(pageable,resultPage);



    }

    public List<Persona> pruebaJoin(){
        return personaRepository.listarPrueba();
    }


    public void guardar(Persona persona){

        personaRepository.save(persona);
    }

    public PersonaDTO findByIdPersona(Long idPersona){
        //System.out.println(idPersona);
        Persona persona = personaRepository.findByIdPersona(idPersona);

        return personaMapper.mapEntityIntoDto(persona);

    }

    public Page<PersonaDTO> searchByNombreApellido(Pageable pageable,String filtro){
        Page<Persona> resultPage = personaRepository.searchByNombreApellido(pageable,filtro);

        return personaMapper.mapEntityPageIntoDTOPage(pageable,resultPage);

    }

    public Page<PersonaDTO> searchByCeduOrRuc(Pageable pageable,String filtro){
        Page<Persona> resultPage = personaRepository.searchByCeduOrRuc(pageable,filtro);

        return personaMapper.mapEntityPageIntoDTOPage(pageable,resultPage);
    }




    public  Persona actualizar(Persona request,Long id){
        Optional<Persona> personas = personaRepository.findById(id);

        Persona persona = personas.get();
        persona.setNombre(request.getNombre());
        persona.setCedula(request.getCedula());
        persona.setRuc(request.getRuc());
        persona.setTelefono(request.getTelefono());
        persona.setDireccion(request.getDireccion());
        persona.setEmail(request.getEmail());
        persona.setEsCliente(request.getEsCliente());
        persona.setLocalidad(request.getLocalidad());


        return personaRepository.save(persona);



    }


    public void borrar(Long id){
        Optional<Persona> personas = personaRepository.findById(id);

        Persona persona = personas.get();

        persona.setEstado(false);

        personaRepository.save(persona);
    }


    public List<Persona> listarSelect(String search){





        return personaRepository.listarSelect(search);



    }




}
