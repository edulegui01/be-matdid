package com.app.bematdid.mapper;

import com.app.bematdid.dto.DetalleFacturaIvaDTO;
import com.app.bematdid.dto.LocalidadDTO;
import com.app.bematdid.model.DetalleFactura;
import com.app.bematdid.model.Localidad;

import java.util.ArrayList;
import java.util.List;

public class DetalleFacturaIvaMapper {

    public DetalleFacturaIvaDTO aDetalleFacturaIva (DetalleFactura detalleFactura){
        DetalleFacturaIvaDTO detalleFacturaIvaDTO = new DetalleFacturaIvaDTO();

        detalleFacturaIvaDTO.setIdProducto(detalleFactura.getId().getIdProducto());
        detalleFacturaIvaDTO.setCantidad(detalleFactura.getCantidad());
        detalleFacturaIvaDTO.setNombre(detalleFactura.getProducto().getNombre());
        detalleFacturaIvaDTO.setPrecio(detalleFactura.getPrecio());
        detalleFacturaIvaDTO.setDescuento(detalleFactura.getDescuento());
        detalleFacturaIvaDTO.setPrecioDescuento(detalleFactura.getPrecio()-detalleFactura.getDescuento());
        detalleFacturaIvaDTO.setIva(detalleFactura.getProducto().getIva());
        if(detalleFactura.getProducto().getIva() == 0 ){
            detalleFacturaIvaDTO.setExcenta(Integer.toString(detalleFactura.getCantidad()*(detalleFactura.getPrecio()-detalleFactura.getDescuento())));
            detalleFacturaIvaDTO.setCincoPorcieto("");
            detalleFacturaIvaDTO.setDiesPorciento("");
        } else if(detalleFactura.getProducto().getIva() == 0.05) {
            detalleFacturaIvaDTO.setExcenta("");
            detalleFacturaIvaDTO.setCincoPorcieto(Integer.toString(detalleFactura.getCantidad()*(detalleFactura.getPrecio()-detalleFactura.getDescuento())));
            detalleFacturaIvaDTO.setDiesPorciento("");
        } else {
            detalleFacturaIvaDTO.setExcenta("");
            detalleFacturaIvaDTO.setCincoPorcieto("");
            detalleFacturaIvaDTO.setDiesPorciento(Integer.toString(detalleFactura.getCantidad()*(detalleFactura.getPrecio()-detalleFactura.getDescuento())));
        }
        return detalleFacturaIvaDTO;
    }

    public List<DetalleFacturaIvaDTO> aDetalleFacturaIvaDTOS (Iterable<DetalleFactura> entities){
        List<DetalleFacturaIvaDTO> dtos = new ArrayList<>();

        entities.forEach(e -> dtos.add(aDetalleFacturaIva(e)));

        return dtos;
    }
}
