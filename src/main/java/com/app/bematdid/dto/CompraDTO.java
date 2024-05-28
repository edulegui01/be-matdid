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
public class CompraDTO  implements Serializable {

    private Long idCompra;
    private Long idFuncionario;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private Long idPersona;
    private String nombrePersona;
    private String apellidoPersona;
    private String nombreEmpresa;
    private String razonSocial;
    private Date fecha;
    private Date fechaVencimiento;
    private Long montoTotal;
    private String numFolio;
    private int cedula;
    private String ruc;
    private String timbrado;

    private List<DetalleCompraDTO> detalleCompra;
    private List<PagoDTO> pagos;
}
