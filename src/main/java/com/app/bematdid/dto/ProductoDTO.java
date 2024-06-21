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
public class ProductoDTO implements Serializable {
    private Long idProducto;
    private int idEditorial;
    private int idCategoria;
    private int idCiclo;
    private int idMateria;
    private String nombre;
    private String descripcion;
    private String isbn;
    private int costo;
    private int precio;
    private Float iva;
    private Integer stockActual = 0;
    private String image;
    private Boolean estado = true;

    private EditorialDTO editorial;
    private CategoriaDTO categoria;
    private CicloDTO ciclo;
    private MateriaDTO materia;
    private AutorDTO autor;
}
