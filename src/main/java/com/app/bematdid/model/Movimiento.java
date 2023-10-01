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
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @NotBlank
    private String motivo;

    @NotBlank
    private Calendar fecha;

    @NotNull
    @Column(name = "es_ingreso")
    private boolean esIngreso;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario")
    Funcionario funcionario;


    @JsonIgnore
    @OneToMany(mappedBy = "movimiento",cascade = CascadeType.PERSIST)
    private List<DetalleMovimiento> detalleMovimientos;

}
