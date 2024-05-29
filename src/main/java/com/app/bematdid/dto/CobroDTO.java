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
public class CobroDTO implements Serializable {

    private Long idCobro;
    private Long idFuncionario;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private Long idFactura;
    private Date fecha;
    private String comentario;
    private String tipoCobro;
    private Integer monto;
}
