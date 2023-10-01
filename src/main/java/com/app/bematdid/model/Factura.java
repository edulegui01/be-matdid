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
    @NotBlank
    private Calendar fecha;
    @NotBlank
    @Column(name = "monto_total")
    private int montoTotal;
    @NotNull
    @Column(name = "num_factura")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int numFactura;


    @ManyToOne()
    @JoinColumn(name = "nro_timbrado")
    Timbrado timbrado;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario")
    Funcionario funcionario;

    @ManyToOne()
    @JoinColumn(name = "id_persona")
    Persona persona;

    @JsonIgnore
    @OneToMany(mappedBy = "factura",cascade = CascadeType.PERSIST)
    private List<DetalleFactura> detalleFacturas;

}
