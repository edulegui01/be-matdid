package com.app.bematdid.controller;

import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.model.Localidad;
import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
import com.app.bematdid.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class PersonaController {

    @Autowired
    private PersonaService personaService;


    @GetMapping("persona/listar")
    public Page<PersonaDTO> listar(Pageable pageable, @RequestParam String cedulaFilter, @RequestParam String nombreFilter, @RequestParam boolean esCliente ){

        System.out.println(esCliente);

        return personaService.listar(pageable,cedulaFilter,nombreFilter,esCliente);

    }

    @GetMapping("persona/buscar/{idPer}")
    public PersonaDTO buscarPorId(@PathVariable Long idPer){
        //System.out.println(idPer.getClass());
        return personaService.findByIdPersona(idPer);

    }


    @PostMapping("persona/guardar")
    public Persona guardar(@RequestBody Persona persona){

        personaService.guardar(persona);

       return persona;
    }


    @GetMapping("persona/listar_select")
    public List<Persona> listarSelect(@RequestParam String search ){



        return personaService.listarSelect(search);

    }

    @CrossOrigin("origins = http://localhost:4200")
    @PutMapping("persona/actualizar/{idPer}")
    public Persona actualizar(@RequestBody Persona personaModi){
        return personaService.actualizar(personaModi);

    }


    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("persona/borrar/{idPer}")
    public void borrar(@PathVariable Long idPer){
        personaService.borrar(idPer);

    }
}
