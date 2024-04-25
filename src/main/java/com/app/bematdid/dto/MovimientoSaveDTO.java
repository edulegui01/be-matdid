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
public class MovimientoSaveDTO implements Serializable {

    private Long idMovimiento;
    private Long idFuncionario;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private String motivo;
    private Date fecha;
    private Boolean esIngreso;
    private List<DetalleMovimientoDTO> detalleMovimientos;
}
