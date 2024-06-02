package com.app.bematdid.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timbrado")
public class Timbrado {
    @Id
    @Column(name = "id_timbrado")
    private Long idTimbrado;

    private String numero;

    @Column(name = "fecha_inicio")
    private Date fechainicio;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    private Boolean activo = true;



    @JsonIgnore
    @OneToMany(mappedBy = "timbrado",cascade = CascadeType.ALL)
    private List<Factura> facturas;
}
