package com.app.bematdid.dto;

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
public class FacturaDTO implements Serializable {
    private Long idFactura;
    private Long idFuncionario;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private Long idPersona;
    private Long idTimbrado;
    private String numeroTimbrado;
    private Long idFolio;
    private String numeroFolio;
    private String cliente;
    private Date fecha;
    private Long montoTotal;
    private String tipoFactura;
    private Integer saldo;

    private List<DetalleFacturaDTO> detalleFacturas;
    private List<CobroDTO> cobros;
}
