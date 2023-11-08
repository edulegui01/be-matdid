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
public class TimbradoDTO implements Serializable {
    private Long nroTimbrado;
    private Date fechainicio;
    private Date fechaVencimiento;
}
