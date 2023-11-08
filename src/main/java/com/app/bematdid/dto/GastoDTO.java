package com.app.bematdid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GastoDTO implements Serializable {

    private Long idGasto;
    private Long idFuncionario;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private Date fecha;
    private String categoria;
    private String beneficiario;
    private String comentario;
    private Integer cantidad;

}
