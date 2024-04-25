package com.app.bematdid.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;
    @NotBlank
    private String nombre;
    @NotBlank
    private String descripcion;
    @NotBlank
    private String autor;
    @NotBlank
    private String editorial;
    @NotBlank
    private String isbn;
    @NotBlank
    private String materia;
    @Column(name = "grado_curso")
    @NotBlank
    private String gradoCurso;
    @NotNull
    private int costo;
    @Column(name = "precio")
    @NotNull
    private int precio;
    @NotNull
    private Float iva;
    @Column(name = "stock_actual")
    @NotNull
    private Integer stockActual=0;
    private boolean estado=true;
    private String image;



    @JsonIgnore
    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
    private List<DetalleMovimiento> detalleMovimientos;


    @JsonIgnore
    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
    private List<DetalleCompra> detalleCompras;

    @JsonIgnore
    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
    private List<DetalleFactura> detalleFacturas;
}
