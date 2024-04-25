package com.app.bematdid.service;


import com.app.bematdid.dto.MovimientoDTO;
import com.app.bematdid.dto.MovimientoSaveDTO;
import com.app.bematdid.mapper.MovimientoMapper;
import com.app.bematdid.model.Factura;
import com.app.bematdid.model.Movimiento;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.MovimientoRepository;
import com.app.bematdid.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private MovimientoMapper mapper;

    @Autowired
    private ProductoRepository productoRepository;

    public Page<MovimientoDTO> listar (Pageable pageable, String motivo) {
        Optional<List<MovimientoDTO>> lista = movimientoRepository.listarMovimiento(motivo).map(movimientos -> mapper.movimientosAMovimientosDTO(movimientos));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());

    }


    public MovimientoDTO guardar(MovimientoSaveDTO movimientoDTO){
        Movimiento movimiento = mapper.movimientoDTOAMovimiento(movimientoDTO);
        movimiento.getDetalleMovimientos().forEach(detalleMovimiento -> {
            detalleMovimiento.setMovimiento(movimiento);
            if(movimiento.getEsIngreso()) {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() + detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            } else {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() - detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            }
        });
        movimientoRepository.save(movimiento);
        return mapper.movimientoAMovimientoDTO(movimiento);
    }

    public void borrar(Long idMovimiento){
        Optional<Movimiento> movimiento = movimientoRepository.findById(idMovimiento);
        movimiento.get().setEstado(false);
        movimiento.get().getDetalleMovimientos().forEach(detalleMovimiento -> {
            if(movimiento.get().getEsIngreso()) {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() - detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            }else {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() + detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            }
        });

        movimientoRepository.save(movimiento.get());
    }
}
