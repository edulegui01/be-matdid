package com.app.bematdid.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotNull
    private int cedula;
    @NotBlank
    private String ruc;
    @NotBlank
    private String telefono;
    @NotBlank
    private String direccion;
    @Email
    private String email;
    private Boolean es_cliente;
    @NotNull
    private Boolean estado=true;








    @ManyToOne()

    @JoinColumn(name = "id_localidad")
    Localidad localidad;


}
