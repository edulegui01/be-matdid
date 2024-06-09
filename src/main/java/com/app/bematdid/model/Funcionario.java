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

import java.util.Date;
import java.util.List;


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
    @NotNull
    private int cedula;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotNull
    private Date fechaNac;
    @NotBlank
    private String direccion;
    @Email
    private String email;
    @NotBlank
    private String telefono;
    @NotNull
    @Column(name = "fecha_alta",updatable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaAlta = new Date();
    private Date fechaBaja;
    @NotBlank
    private String rol;

    @NotNull
    private Boolean estado=true;
    private Character activo;



    @ManyToOne()
    @JoinColumn(name = "id_localidad")
    Localidad localidad;


    @JsonIgnore
    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    private List<MovimientoCaja> movimientoCajas;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    private List<Compra> compras;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    private List<Movimiento> movimientos;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    private List<Factura> facturas;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    private List<Pago> pagos;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario",cascade = CascadeType.ALL)
    private List<Cobro> cobros;
}
