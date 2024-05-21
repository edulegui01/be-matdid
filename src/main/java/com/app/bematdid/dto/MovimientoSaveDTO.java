package com.app.bematdid.dto;

import com.app.bematdid.model.Motivo;
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
    private int idMotivo;
    private String nombreFuncionario;
    private String apellidoFuncionario;
    private Date fecha;

    private Motivo motivo;
    private List<DetalleMovimientoDTO> detalleMovimientos;
}
