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
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Column(name = "codigo_departamento")
    private Integer codigoDepartamento;
    @NotBlank
    @Column(name = "descripcion_departamento")
    private String descripcionDepartamento;
    @NotBlank
    @Column(name = "codigo_distrito")
    private String codigoDistrito;
    @NotBlank
    @Column(name = "descripcion_distrito")
    private String descripcionDistrito;



    @JsonIgnore
    @OneToMany(mappedBy = "localidad",cascade = CascadeType.ALL)
    private List<Persona> personas;
}
