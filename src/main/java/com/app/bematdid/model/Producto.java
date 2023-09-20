package com.app.bematdid.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    @NotNull
    private Integer precioCosto;
    @NotNull
    private Integer precioVenta;
    @NotNull
    private Float iva;
    @NotNull
    private Integer cantidadMinima;
    @NotNull
    private Integer cantidad;
    private boolean estado=true;
}
