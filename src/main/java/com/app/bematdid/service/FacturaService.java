package com.app.bematdid.service;

import com.app.bematdid.dto.FacturaDTO;
import com.app.bematdid.error.StockNegativeException;
import com.app.bematdid.mapper.FacturaMapper;
import com.app.bematdid.model.Factura;
import com.app.bematdid.model.Folio;
import com.app.bematdid.model.Movimiento;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.FacturaRepository;
import com.app.bematdid.repository.FolioRepository;
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
    private FolioRepository folioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public Page<FacturaDTO> listar (Pageable pageable, String nombrePersona, String numFactura) {
        Optional<List<FacturaDTO>> lista = facturaRepository.listarFacturas(nombrePersona, numFactura).map(facturas -> mapper.facturasAFacturasDTO(facturas));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());

    }

    public FacturaDTO guardar (FacturaDTO facturaDTO) throws StockNegativeException{
        Factura factura = mapper.facturaDTOAFactura(facturaDTO);
        Folio folio = folioRepository.getNumFolio();

        validationStock(factura);
        factura.getDetalleFacturas().forEach(detalleFactura -> {
            detalleFactura.setFactura(factura);

            Optional<Producto> producto = productoRepository.findById(detalleFactura.getId().getIdProducto());
            producto.get().setStockActual(producto.get().getStockActual()-detalleFactura.getCantidad());
            productoRepository.save(producto.get());
        });
        Factura factura1 =  facturaRepository.save(factura);

        factura1.setNumFactura(folio.getNumeracionFolio()+"-"+factura1.getIdFactura());

        facturaRepository.save(factura1);

        return mapper.facturaAFacturaDTO(factura1);
    }


    public void validationStock(Factura factura) throws StockNegativeException {
        factura.getDetalleFacturas().forEach(detalleFactura -> {
            detalleFactura.setFactura(factura);
            Optional<Producto> producto = productoRepository.findById(detalleFactura.getId().getIdProducto());
            if(producto.get().getStockActual() - detalleFactura.getCantidad()<0){
                try {
                    throw new StockNegativeException(String.format("QUEDAN %s UNIDADES DE %s",producto.get().getStockActual(),producto.get().getNombre()));
                } catch (StockNegativeException e) {
                    throw new RuntimeException(e);
                }
            }

        });
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
