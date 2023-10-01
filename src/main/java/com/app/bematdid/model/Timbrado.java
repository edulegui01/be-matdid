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
    @Column(name = "nro_timbrado")
    private Integer nroTimbrado;
    @Column(name = "fecha_inicio")
    private Calendar fecha_inicio;
    @Column(name = "fecha_vencimiento")
    private Calendar fecha_vencimiento;



    @JsonIgnore
    @OneToMany(mappedBy = "timbrado",cascade = CascadeType.ALL)
    private List<Factura> facturas;
}
