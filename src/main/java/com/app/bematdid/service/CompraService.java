package com.app.bematdid.service;

import com.app.bematdid.dto.CompraDTO;
import com.app.bematdid.mapper.CompraMapper;
import com.app.bematdid.model.Compra;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.CompraRepository;
import com.app.bematdid.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private CompraMapper mapper;

    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    public List<CompraDTO> listarTodo () {
        return mapper.comprasAComprasDTO((List<Compra>) compraRepository.findAll());
    }

    public CompraDTO guardar (CompraDTO compraDTO) {
        Compra compra = mapper.compraDTOACompra(compraDTO);
        compra.getDetalleCompra().forEach(detalleCompra -> {
            detalleCompra.setCompra(compra);

            Optional<Producto> producto = productoRepository.findById(detalleCompra.getId().getIdProducto());
            producto.get().setIdProducto(detalleCompra.getId().getIdProducto());
            producto.get().setStockActual(producto.get().getStockActual()+detalleCompra.getCantidad());
            productoService.actualizar(producto.get(),detalleCompra.getId().getIdProducto());
        });
        return mapper.compraACompraDTO(compraRepository.save(compra));
    }

}
