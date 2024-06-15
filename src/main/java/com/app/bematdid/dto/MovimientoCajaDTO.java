package com.app.bematdid.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoCajaDTO implements Serializable {

    private Long idMovimientoCaja;
    private Long idFuncionario;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private Long idConcepto;
    private Date fecha;
    private String comprobante;
    private String beneficiario;
    private String comentario;
    private String tipoPago;
    private Integer monto;
    private String estado = "ABIERTO";

}
