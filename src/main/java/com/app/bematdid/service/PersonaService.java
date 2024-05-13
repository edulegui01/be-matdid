package com.app.bematdid.service;


import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.mapper.PersonaMapper;
import com.app.bematdid.model.Persona;
import com.app.bematdid.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaMapper personaMapper;


    public Page<PersonaDTO> listar(Pageable pageable,String cedulaFilter,String nombreFilter,boolean esCliente){

        Page<Persona> lista = personaRepository.listarPersonas(pageable,cedulaFilter,nombreFilter,esCliente);

        List<PersonaDTO> dtos = personaMapper.personasAPersonasDTO(lista.getContent());

        //return personaMapper.mapEntityPageIntoDTOPage(pageable,resultPage);
        return new PageImpl<>(dtos, pageable, lista.getTotalElements());



    }

    public void guardar(Persona persona){

        personaRepository.save(persona);
    }

    public PersonaDTO findByIdPersona(Long idPersona){
        //System.out.println(idPersona);
        Persona persona = personaRepository.findByIdPersona(idPersona);

        return personaMapper.personaAPersonaDTO(persona);

    }


    public  Persona actualizar(Persona request,Long id){
        Optional<Persona> personas = personaRepository.findById(id);


        Persona persona = personas.get();
        persona.setCedula(request.getCedula());
        persona.setRuc(request.getRuc());
        persona.setTelefono(request.getTelefono());
        persona.setDireccion(request.getDireccion());
        persona.setEmail(request.getEmail());
        persona.setEsCliente(request.getEsCliente());
        persona.setLocalidad(request.getLocalidad());
        persona.setRazonSocial(request.getRazonSocial());
        persona.setSector(request.getSector());
        persona.setNombreEncargado(request.getNombreEncargado());


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
