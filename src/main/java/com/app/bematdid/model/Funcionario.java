package com.app.bematdid.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Long idFuncionario;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotNull
    private int cedula;

    @NotBlank
    private String telefono;
    @NotBlank
    private String direccion;

    private Date fechaNac;
    private Date fechaAlta;
    private Date fechaBaja;

    @NotNull
    private Boolean estado=true;
    private Character activo;


    @ManyToOne()

    @JoinColumn(name = "id_localidad")
    Localidad localidad;
}
