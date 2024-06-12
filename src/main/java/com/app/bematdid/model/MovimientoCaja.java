package com.app.bematdid.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movimiento_caja")
public class MovimientoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento_caja")
    private Long idMovimientoCaja;
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    @Column(name = "id_concepto")
    private Long idConcepto;
    @Column(name = "fecha")
    private Date fecha;
    private String comprobante;
    private String beneficiario;
    private String comentario;
    @NotNull
    private Integer cantidad;

    private Boolean estado = true;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario", insertable=false, updatable=false)
    Funcionario funcionario;

    @ManyToOne()
    @JoinColumn(name = "id_concepto", insertable=false, updatable=false)
    Concepto concepto;


}
