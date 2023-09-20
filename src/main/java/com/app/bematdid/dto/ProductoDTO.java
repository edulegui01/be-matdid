package com.app.bematdid.dto;



import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO implements Serializable {

    private Long idProducto;
    private String nombre;
    private Integer precioCosto;
    private Integer precioVenta;
    private Float iva;
    private Integer cantidadMinima;
    private Integer cantidad;


    public ProductoDTO(Producto producto){
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.precioCosto = producto.getPrecioCosto();
        this.precioVenta = producto.getPrecioVenta();
        this.iva = producto.getIva();
        this.cantidadMinima = producto.getCantidadMinima();
        this.cantidad = producto.getCantidad();
    }

}
