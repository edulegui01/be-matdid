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
public class DetalleMovimientoPk {
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @Column(name = "id_producto")
    private Long idProducto;

}
