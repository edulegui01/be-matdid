package com.app.bematdid.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @Column(name="id_persona")
    private Long idPersona;

    @Column(name="id_timbrado")
    private Long idTimbrado;

    @Column(name="id_folio")
    private Long idFolio;

    private Date fecha;

    private Date fechaVencimiento;
    @Column(name = "monto_total")
    private Long montoTotal;

    @Column(name = "tipo_factura")
    private String tipoFactura;

    private Integer saldo;
    @Column(name = "num_factura")
    private String numFactura;

    private String estado= "SC";

    @ManyToOne()
    @JoinColumn(name = "id_timbrado",insertable=false, updatable=false)
    Timbrado timbrado;

    @ManyToOne()
    @JoinColumn(name = "id_folio",insertable=false, updatable=false)
    Folio folio;

    @ManyToOne()
    @JoinColumn(name = "id_funcionario",insertable=false, updatable=false)
    Funcionario funcionario;

    @ManyToOne()
    @JoinColumn(name = "id_persona",insertable=false, updatable=false)
    Persona persona;

    @JsonIgnore
    @OneToMany(mappedBy = "factura",cascade = {CascadeType.ALL})
    private List<DetalleFactura> detalleFacturas;

    @JsonIgnore
    @OneToMany(mappedBy = "factura",cascade = {CascadeType.ALL})
    private List<Cobro> cobros;

    public Map<String, Object> getMapDetalle (){
        JRBeanCollectionDataSource detalleCollection = new JRBeanCollectionDataSource(this.detalleFacturas);
        Map<String, Object> mapDetalle = new HashMap<>();
        mapDetalle.put("detalleDatos", detalleCollection);
        return mapDetalle;
    }

}
