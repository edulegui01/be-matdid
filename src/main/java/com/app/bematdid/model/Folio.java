package com.app.bematdid.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "folio")
public class Folio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_folio")
    private Long idFolio;

    @Column(name = "numeracion_folio")
    @NotBlank
    private String numeracionFolio;

    @NotNull
    private Boolean activo;

    @JsonIgnore
    @OneToMany(mappedBy = "folio",cascade = CascadeType.ALL)
    private List<Factura> facturas;


}
