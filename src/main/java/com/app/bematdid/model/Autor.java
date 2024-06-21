package com.app.bematdid.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Long idAutor;
    @NotNull
    private String nombre;
    @Column(columnDefinition = "TEXT")
    private String biografia;

    private String image;

    private boolean estado = true;

    @JsonIgnore()
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL)
    private List<Producto> productos;


}
