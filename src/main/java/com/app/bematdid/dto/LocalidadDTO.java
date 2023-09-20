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
    private String codigo_distrito;
    private String descripcion_distrito;



    public LocalidadDTO(Localidad localidad){
        this.id = localidad.getId();
        this.codigo_distrito = localidad.getCodigoDistrito();
        this.descripcion_distrito = localidad.getDescripcionDistrito();
    }
}
