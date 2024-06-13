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
    private int idMotivo;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private String fecha;
    private String documento;
    private String comentario;

    private MotivoDTO motivo;
    private List<DetalleMovimientoDTO> detalleMovimientos;
}
