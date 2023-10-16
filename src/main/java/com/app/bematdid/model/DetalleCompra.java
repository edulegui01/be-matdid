package com.app.bematdid.model;


import jakarta.persistence.*;
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
@Table(name = "detalle_compra")
public class DetalleCompra {

    @EmbeddedId
    private DetalleCompraPk id;


    @NotNull
    private int cantidad;
    @NotNull
    private int precioCompra;
    @NotNull
    private Float descuento;


    @ManyToOne()
    @JoinColumn(name = "id_compra", insertable = false, updatable = false)
    @MapsId("idCompra")
    Compra compra;

    @ManyToOne()
    @JoinColumn(name = "id_producto",insertable = false, updatable = false)
    Producto producto;

}
