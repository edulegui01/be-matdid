package com.app.bematdid.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class
DetalleFacturaDTO implements Serializable {
    private long idProducto;
    private int cantidad;
    private int precio;
    private float descuento;
    private String nombre;
    private float iva;
}
