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
@Table(name = "detalle_movimiento")
public class DetalleMovimiento {

    @EmbeddedId
    private DetalleMovimientoPk id;


    @NotNull
    private int cantidad;


    @ManyToOne()
    @JoinColumn(name = "id_movimiento", insertable = false, updatable = false)
    @MapsId("idMovimiento")
    Movimiento movimiento;

    @ManyToOne()
    @JoinColumn(name = "id_producto",insertable = false, updatable = false)
    Producto producto;
}
