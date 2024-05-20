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

    @Column(name = "id_editorial")
    private Integer idEditorial;

    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "id_ciclo")
    private Integer idCiclo;

    @Column(name = "id_materia")
    private Integer idMateria;

    private String nombre;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    @NotBlank
    private String autor;
    @NotBlank

    private String isbn;

    @NotNull
    private Integer costo;
    @Column(name = "precio")
    @NotNull
    private Integer precio;
    @NotNull
    private Float iva;
    @Column(name = "stock_actual")
    @NotNull
    private Integer stockActual=0;
    private String image;
    private Boolean estado=true;

    @ManyToOne()
    @JoinColumn(name = "id_editorial",insertable=false, updatable=false)
    Editorial editorial;

    @ManyToOne()
    @JoinColumn(name = "id_categoria",insertable=false, updatable=false)
    Categoria categoria;

    @ManyToOne()
    @JoinColumn(name = "id_ciclo",insertable=false, updatable=false)
    Ciclo ciclo;

    @ManyToOne()
    @JoinColumn(name = "id_materia",insertable=false, updatable=false)
    Materia materia;


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
