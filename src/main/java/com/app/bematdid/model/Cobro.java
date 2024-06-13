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
@Table(name = "Cobro")
public class Cobro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cobro")
    private Long idCobro;

    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @Column(name = "id_factura")
    private Long idFactura;

    private Date fecha = new Date();

    private String comentario;

    @Column(name = "tipo_cobro")
    private String tipoCobro;

    private Integer monto;

    private String estado= "ABIERTO";

    @ManyToOne()
    @JoinColumn(name = "id_funcionario",insertable=false, updatable=false)
    Funcionario funcionario;

    @ManyToOne()
    @JoinColumn(name = "id_factura",insertable=false, updatable=false)
    Factura factura;
}
