package com.app.bematdid.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long idCompra;
    @NotBlank
    private Calendar fecha;
    @NotBlank
    @Column(name = "monto_total")
    private int montoTotal;
    @NotBlank
    @Column(name = "num_folio")
    private String numFolio;



    @ManyToOne()
    @JoinColumn(name = "id_persona")
    Persona persona;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario")
    Funcionario funcionario;

    @JsonIgnore
    @OneToMany(mappedBy = "compra",cascade = CascadeType.PERSIST)
    private List<DetalleCompra> detalleCompra;

}
