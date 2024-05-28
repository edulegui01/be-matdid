package com.app.bematdid.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @Column(name = "id_compra")
    private Long idCompra;

    private Date fecha = new Date();

    private String comentario;

    @Column(name = "tipo_pago")
    private String tipoPago;

    private Integer monto;

    private boolean estado=true;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario",insertable=false, updatable=false)
    Funcionario funcionario;

    @ManyToOne()
    @JoinColumn(name = "id_compra",insertable=false, updatable=false)
    Compra compra;
}
