package com.app.bematdid.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

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
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    @Column(name = "fecha")
    private Date fecha;
    @NotBlank
    private String categoria;
    private String beneficiario;
    private String comentario;
    @NotNull
    private Integer cantidad;

    private Boolean estado = true;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario", insertable=false, updatable=false)
    Funcionario funcionario;


}
