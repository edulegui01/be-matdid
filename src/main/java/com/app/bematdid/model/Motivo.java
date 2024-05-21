package com.app.bematdid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Motivo")
public class Motivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_motivo")
    private Integer idMotivo;

    @NotNull
    private String nombre;

    @Column(name = "es_ingreso")
    private Boolean esIngreso;

    private boolean estado=true;

    @JsonIgnore
    @OneToMany(mappedBy = "motivo",cascade = CascadeType.ALL)
    private List<Movimiento> movimientos;
}
