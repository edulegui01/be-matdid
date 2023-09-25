package com.app.bematdid.service;

import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.mapper.PersonaMapper;
import com.app.bematdid.mapper.ProductoMapper;
import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.PersonaRepository;
import com.app.bematdid.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    private ProductoMapper productoMapper = new ProductoMapper();
    public Page<ProductoDTO> listar(Pageable pageable, String nombre){

        Page<Producto> resultPage = productoRepository.listarProducto(pageable, nombre);



        return productoMapper.mapEntityPageIntoDTOPage(pageable,resultPage);



    }

    public void guardar(Producto producto){

        productoRepository.save(producto);
    }


    public  Producto actualizar(Producto request,Long id){
        Optional<Producto> productos = productoRepository.findById(id);

        Producto producto = productos.get();
        producto.setNombre(request.getNombre());
        producto.setPrecioCosto(request.getPrecioCosto());
        producto.setPrecioVenta(request.getPrecioVenta());
        producto.setIva(request.getIva());
        producto.setCantidadMinima(request.getCantidadMinima());
        producto.setCantidad(request.getCantidad());



        return productoRepository.save(producto);



    }


    public void borrar(Long id){
        Optional<Producto> productos = productoRepository.findById(id);

        Producto producto = productos.get();

        producto.setEstado(false);

        productoRepository.save(producto);
    }

}
