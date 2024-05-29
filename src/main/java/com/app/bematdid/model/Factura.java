package com.app.bematdid.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    @Column(name="id_persona")
    private Long idPersona;

    @Column(name="nro_timbrado")
    private Long nroTimbrado;

    private Date fecha = new Date();
    @Column(name = "monto_total")
    private Long montoTotal;

    @Column(name = "num_factura")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long numFactura;

    @Column(name = "tipo_factura")
    private String tipoFactura;

    private Integer saldo;

    private Boolean estado= true;

    @ManyToOne()
    @JoinColumn(name = "nro_timbrado",insertable=false, updatable=false)
    Timbrado timbrado;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario",insertable=false, updatable=false)
    Funcionario funcionario;

    @ManyToOne()
    @JoinColumn(name = "id_persona",insertable=false, updatable=false)
    Persona persona;

    @JsonIgnore
    @OneToMany(mappedBy = "factura",cascade = {CascadeType.ALL})
    private List<DetalleFactura> detalleFacturas;

    @JsonIgnore
    @OneToMany(mappedBy = "factura",cascade = {CascadeType.ALL})
    private List<Cobro> cobros;

}
