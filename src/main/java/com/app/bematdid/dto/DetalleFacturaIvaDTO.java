package com.app.bematdid.dto;

import com.ctc.wstx.shaded.msv_core.util.xml.SAXEventGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.SQLData;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleFacturaIvaDTO {

    private long idProducto;
    private int cantidad;
    private String nombre;
    private int precio;
    private Float descuento;
    private float iva;
    private String exenta;
    private String cinco;
    private String diez;

}
