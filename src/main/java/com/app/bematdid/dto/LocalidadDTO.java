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
public class LocalidadDTO implements Serializable {

    private Integer id;
    private String nombre;




    public LocalidadDTO(Localidad localidad){
        this.id = localidad.getId();
        this.nombre = localidad.getNombre();

    }
}
