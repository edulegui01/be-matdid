package com.app.bematdid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class DetalleCompraPk {

    @Column(name = "id_compra")
    private Long idCompra;

    @Column(name = "id_producto")
    private Long idProducto;
}
