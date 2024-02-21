package com.app.bematdid.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "numeracion_folio")
public class NumeracionFolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_num_folio")
    private Long idNumFolio;

    @Column(name = "numeracion_folio")
    @NotBlank
    private String numeracionFolio;

    @NotNull
    private Boolean activo;


}
