package com.app.bematdid.service;

import com.app.bematdid.dto.FacturaDTO;
import com.app.bematdid.mapper.FacturaMapper;
import com.app.bematdid.model.Factura;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.FacturaRepository;
import com.app.bematdid.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturaMapper mapper;

    @Autowired
    private ProductoRepository productoRepository;

    public Page<FacturaDTO> listar (Pageable pageable, String nombrePersona, String numFactura) {
        Optional<List<FacturaDTO>> lista = facturaRepository.listarFacturas(nombrePersona, numFactura).map(facturas -> mapper.facturasAFacturasDTO(facturas));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());

    }

    public FacturaDTO guardar (FacturaDTO facturaDTO) {
        Factura factura = mapper.facturaDTOAFactura(facturaDTO);
        factura.getDetalleFacturas().forEach(detalleFactura -> {
            detalleFactura.setFactura(factura);

            Optional<Producto> producto = productoRepository.findById(detalleFactura.getId().getIdProducto());
            producto.get().setStockActual(producto.get().getStockActual()-detalleFactura.getCantidad());
            productoRepository.save(producto.get());
        });
        facturaRepository.save(factura);
        return mapper.facturaAFacturaDTO(factura);
    }


    public void borrar(Long idFactura){
        Optional<Factura> factura = facturaRepository.findById(idFactura);
        factura.get().setEstado(false);
        factura.get().getDetalleFacturas().forEach(detalleFactura -> {
            Optional<Producto> producto = productoRepository.findById(detalleFactura.getId().getIdProducto());
            producto.get().setStockActual(producto.get().getStockActual()+detalleFactura.getCantidad());
            productoRepository.save(producto.get());
        });

        facturaRepository.save(factura.get());
    }


}
