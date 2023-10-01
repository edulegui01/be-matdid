package com.app.bematdid.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "nombre_usuario")
    @NotBlank
    private String nombreUsuario;
    @Column(name = "password")
    @NotBlank
    private String password;


    @ManyToOne()
    @JoinColumn(name = "id_funcionario")
    Funcionario funcionario;

}
