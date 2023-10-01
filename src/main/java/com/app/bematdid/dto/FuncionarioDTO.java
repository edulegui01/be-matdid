package com.app.bematdid.dto;

import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Localidad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FuncionarioDTO implements Serializable {

    private Long idFuncionario;
    private String nombre;
    private String apellido;
    private  int cedula;
    private String telefono;
    private String direccion;
    private Calendar fechaAlta;
    private Localidad localidad;
    private Character activo;
    private Calendar fechaNac;


    public FuncionarioDTO(Funcionario funcionario){
        this.idFuncionario = funcionario.getIdFuncionario();
        this.nombre = funcionario.getNombre();
        this.apellido = funcionario.getApellido();
        this.cedula = funcionario.getCedula();
        this.direccion = funcionario.getDireccion();
        this.telefono = funcionario.getTelefono();
        this.localidad = funcionario.getLocalidad();
        this.fechaAlta = funcionario.getFechaAlta();
        this.activo = funcionario.getActivo();
        this.fechaNac = funcionario.getFechaNac();
    }

}
