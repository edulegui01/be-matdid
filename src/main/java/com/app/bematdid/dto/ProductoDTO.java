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
    private Integer costo;
    private Integer precio;
    private Float iva;
    private int stockActual;


    public ProductoDTO(Producto producto){
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.costo = producto.getCosto();
        this.precio = producto.getPrecio();
        this.iva = producto.getIva();
        this.stockActual = producto.getStockActual();
    }

}
