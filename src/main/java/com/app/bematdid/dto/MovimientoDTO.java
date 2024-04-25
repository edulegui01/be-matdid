package com.app.bematdid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MovimientoDTO implements Serializable {

    private Long idMovimiento;
    private Long idFuncionario;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private String motivo;
    private String fecha;
    private Boolean esIngreso;
    private List<DetalleMovimientoDTO> detalleMovimientos;
}
