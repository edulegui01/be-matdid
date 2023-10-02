package com.app.bematdid.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DetalleFacturaPk {

    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "id_producto")
    private Long idProducto;
}
