package com.app.bematdid.dto;

import com.app.bematdid.model.DetalleCompra;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraDTO  implements Serializable {

    private Long idCompra;
    private Long idFuncionario;
    private Long idPersona;
    private Date fecha;
    private int montoTotal;
    private String numFolio;
    private List<DetalleCompra> detalleCompra;
}
