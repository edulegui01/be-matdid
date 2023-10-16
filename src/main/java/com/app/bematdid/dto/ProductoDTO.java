package com.app.bematdid.dto;




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
    private String descripcion;
    private String autor;
    private String editorial;
    private String isbn;
    private String materia;
    private String gradoCurso;
    private int costo;
    private int precio;
    private Float iva;


    public ProductoDTO(Producto producto){
        this.idProducto = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.autor = producto.getAutor();
        this.editorial = producto.getEditorial();
        this.isbn = producto.getIsbn();
        this.materia = producto.getMateria();
        this.gradoCurso = producto.getGradoCurso();
        this.costo = producto.getCosto();
        this.precio = producto.getPrecio();
        this.iva = producto.getIva();


    }

}
