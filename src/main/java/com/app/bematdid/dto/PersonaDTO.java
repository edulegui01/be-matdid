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
    private String empresa;
    private String nombre;
    private String apellido;
    private Integer cedula;
    private String nombreEncargado;
    private String direccion;
    private String email;
    private  String telefono;
    private String sector;
    private String ruc;
    private String razonSocial;
    private Localidad localidad;


}



