package com.app.bematdid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleFacturaPk {

    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "id_producto")
    private Long idProducto;
}
