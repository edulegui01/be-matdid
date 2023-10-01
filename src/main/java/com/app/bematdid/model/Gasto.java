package com.app.bematdid.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gasto")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gasto")
    private Long idGasto;
    @NotBlank
    @Column(name = "fecha")
    private Calendar fecha;
    @NotBlank
    private String categoria;
    private String beneficiario;
    private String comentario;
    @NotNull
    private int cantidad;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario")
    Funcionario funcionario;


}
