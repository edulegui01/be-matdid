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
@Table(name = "detalle_factura")
public class DetalleFactura {


    @EmbeddedId
    private DetalleFacturaPk id;


    @NotNull
    private int cantidad;
    @NotNull
    private int precio;
    @NotNull
    private Float descuento;


    @ManyToOne()
    @JoinColumn(name = "id_factura", insertable = false, updatable = false)
    @MapsId("idFactura")
    Factura factura;

    @ManyToOne()
    @JoinColumn(name = "id_producto",insertable = false, updatable = false)
    Producto producto;
}
