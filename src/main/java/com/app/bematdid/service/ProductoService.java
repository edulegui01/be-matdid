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

}
