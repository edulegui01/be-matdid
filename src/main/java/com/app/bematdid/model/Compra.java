package com.app.bematdid.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    @Column(name="id_persona")
    private Long idPersona;

    private Date fecha = new Date();

    @Column(name = "monto_total")
    private Long montoTotal;

    @Column(name = "num_folio")
    private String numFolio;

    private Boolean estado= true;



    @ManyToOne()
    @JoinColumn(name = "id_persona",insertable=false, updatable=false)
    Persona persona;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario", insertable=false, updatable=false)
    Funcionario funcionario;

    @JsonIgnore
    @OneToMany(mappedBy = "compra",cascade = {CascadeType.ALL})
    private List<DetalleCompra> detalleCompra;

}
