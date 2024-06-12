package com.app.bematdid.service;

import com.app.bematdid.dto.CompraDTO;
import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.mapper.CompraMapper;
import com.app.bematdid.model.Compra;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.CompraRepository;
import com.app.bematdid.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CompraMapper mapper;

    @Autowired
    private ProductoRepository productoRepository;

    public List<CompraDTO> listarTodo () {
        return mapper.comprasAComprasDTO(compraRepository.findAll());
    }

    public Page<CompraDTO> listar (Pageable pageable, String nombrePersona, String numFolio) {
        Page<Compra> lista = compraRepository.listarCompras(pageable, nombrePersona, numFolio);

        List<CompraDTO> dtos = mapper.comprasAComprasDTO(lista.getContent());

        return new PageImpl<>(dtos, pageable, lista.getTotalElements());

    }

    public CompraDTO guardar (CompraDTO compraDTO) {
        Compra compra = mapper.compraDTOACompra(compraDTO);
        compra.getDetalleCompra().forEach(detalleCompra -> {
            detalleCompra.setCompra(compra);

            Optional<Producto> producto = productoRepository.findById(detalleCompra.getId().getIdProducto());
            producto.get().setStockActual(producto.get().getStockActual()+detalleCompra.getCantidad());
            productoRepository.save(producto.get());
        });
        compraRepository.save(compra);
        return mapper.compraACompraDTO(compra);
    }



    public CompraDTO actualizar (CompraDTO compraDTO){
        Compra compra = mapper.compraDTOACompra(compraDTO);
        compra.getDetalleCompra().forEach(detalle -> detalle.setCompra(compra));

        return mapper.compraACompraDTO(compraRepository.save(compra));
    }


    public void borrar(Long idCompra){
        Optional<Compra> compra = compraRepository.findById(idCompra);
        compra.get().setEstado("A");
        compra.get().getDetalleCompra().forEach(detalleCompra -> {
            Optional<Producto> producto = productoRepository.findById(detalleCompra.getId().getIdProducto());
            producto.get().setStockActual(producto.get().getStockActual()-detalleCompra.getCantidad());
            productoRepository.save(producto.get());
        });

        compraRepository.save(compra.get());
    }




}
