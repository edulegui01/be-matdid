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
@Table(name = "Editorial")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editorial")
    private Integer idEditorial;

    @NotNull
    private String nombre;

    private boolean estado=true;

    @JsonIgnore
    @OneToMany(mappedBy = "editorial",cascade = CascadeType.ALL)
    private List<Producto> productos;
}
