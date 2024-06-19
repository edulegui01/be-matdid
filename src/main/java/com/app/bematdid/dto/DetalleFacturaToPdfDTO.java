package com.app.bematdid.dto;


import lombok.Data;

@Data
public class DetalleFacturaToPdfDTO {
    private Long idProducto;
    private Integer cantidad;
    private String nombre;
    private Integer precio;
    private Float iva;
    private Integer descuento;
    private Integer excenta;
    private Integer cincoPorCiento;
    private Integer diezPorCiento;
}
