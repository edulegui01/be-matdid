package com.app.bematdid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "concepto")
public class Concepto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_concepto")
    private Long idConcepto;

    private String nombre;

    @Column(name = "es_ingreso")
    private Character esIngreso;

    private Boolean estado = true;

    @JsonIgnore
    @OneToMany(mappedBy = "concepto",cascade = CascadeType.ALL)
    private List<MovimientoCaja> movimientosCaja;
}
