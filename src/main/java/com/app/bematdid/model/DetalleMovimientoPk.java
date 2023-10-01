package com.app.bematdid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class DetalleMovimientoPk {
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @Column(name = "id_producto")
    private Long idProducto;

}
