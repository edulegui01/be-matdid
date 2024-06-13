package com.app.bematdid.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO implements Serializable {

    private Long idPago;
    private Long idFuncionario;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private Long idCompra;
    private Date fecha;
    private String comentario;
    private String tipoPago;
    private Integer monto;
    private String estado;
}
