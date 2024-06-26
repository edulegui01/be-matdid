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
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @Column(name = "id_motivo")
    private Integer idMotivo;

    private Date fecha;

    private String documento;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    private Boolean estado= true;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario",insertable=false, updatable=false)
    Funcionario funcionario;

    @ManyToOne()
    @JoinColumn(name = "id_motivo",insertable=false, updatable=false)
    Motivo motivo;


    @JsonIgnore
    @OneToMany(mappedBy = "movimiento",cascade = CascadeType.PERSIST)
    private List<DetalleMovimiento> detalleMovimientos;

}
