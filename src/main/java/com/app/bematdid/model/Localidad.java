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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "localidad")
public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    private boolean estado=true;



    @JsonIgnore
    @OneToMany(mappedBy = "localidad",cascade = CascadeType.ALL)
    private List<Persona> personas;

    @JsonIgnore
    @OneToMany(mappedBy = "localidad",cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;
}
