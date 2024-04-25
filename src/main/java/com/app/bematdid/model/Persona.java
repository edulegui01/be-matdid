package com.app.bematdid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;


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
    @NotNull
    private int cedula;
    @Column(name = "nombre_encargado")
    @NotBlank
    private String nombreEncargado;
    @NotBlank
    private String direccion;
    @Email
    private String email;
    @NotBlank
    private String telefono;
    @NotBlank
    private String sector;
    @NotBlank
    private String ruc;
    @NotBlank
    private String razonSocial;
    @Column(name = "es_cliente")
    private Boolean esCliente;
    @NotNull
    private Boolean estado=true;








    @ManyToOne()
    @JoinColumn(name = "id_localidad")
    Localidad localidad;

    @JsonIgnore
    @OneToMany(mappedBy = "persona",cascade = CascadeType.ALL)
    private List<Compra> compras;

    @JsonIgnore
    @OneToMany(mappedBy = "persona",cascade = CascadeType.ALL)
    private List<Factura> facturas;


}
