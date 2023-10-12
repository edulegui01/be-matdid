package com.app.bematdid.dto;

import com.app.bematdid.model.Localidad;
import com.app.bematdid.model.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO implements Serializable {

    private Long idPersona;
    private String nombre;
    private String nombreEncargado;
    private int cedula;
    private String ruc;
    private String direccion;
    private  String telefono;
    private String sector;
    private String razonSocial;
    private Localidad localidad;
    private String email;



    public PersonaDTO(Persona persona){
        this.idPersona = persona.getIdPersona();
        this.nombre = persona.getNombre();
        this.cedula = persona.getCedula();
        this.ruc = persona.getRuc();
        this.direccion = persona.getDireccion();
        this.telefono = persona.getTelefono();
        this.localidad = persona.getLocalidad();
        this.email = persona.getEmail();
    }
}



