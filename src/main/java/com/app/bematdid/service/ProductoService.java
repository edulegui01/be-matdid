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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductoService {

    private final static String UPLOADS_FOLDER_PATH = "C:/Users/edu/Documents/matdidProyecto2/be-matdid/uploads/";

    private final static String URL_GET_IMAGE = "http://localhost:8090/producto/imagen?searchImagen=";

    @Autowired
    private ProductoRepository productoRepository;
    private ProductoMapper productoMapper = new ProductoMapper();
    public Page<ProductoDTO> listar(Pageable pageable, String nombre){

        Page<Producto> resultPage = productoRepository.listarProducto(pageable, nombre);



        return productoMapper.mapEntityPageIntoDTOPage(pageable,resultPage);



    }

    public List<Producto> listarSelect(String search){





        return productoRepository.listarSelect(search);



    }

    public void guardar(Producto producto){

        productoRepository.save(producto);
    }


    public  Producto actualizar(Producto request,Long id){
        Optional<Producto> productos = productoRepository.findById(id);

        Producto producto = productos.get();
        producto.setNombre(request.getNombre());
        producto.setCosto(request.getCosto());
        producto.setPrecio(request.getPrecio());
        producto.setIva(request.getIva());
        producto.setStockActual(request.getStockActual());



        return productoRepository.save(producto);



    }




    public String subirImagen(MultipartFile image, Long idProducto) throws IOException{
        String imagePath = UPLOADS_FOLDER_PATH+image.getOriginalFilename();

        Optional<Producto> productoBuscado = productoRepository.findById(idProducto);

        Producto producto =  productoBuscado.get();

        producto.setImage(URL_GET_IMAGE+image.getOriginalFilename());

        Producto productoSave = productoRepository.save(producto);

        image.transferTo(new File(imagePath));

        if (productoSave != null){
            return "la ruta de la imagen guardo en: "+ imagePath;
        }




        return null;
    }


    public byte[] getImage(String nombreImagen) throws IOException {
        String imagePath = UPLOADS_FOLDER_PATH+nombreImagen;
        System.out.println(imagePath);

        byte[] imagen = Files.readAllBytes(new File(imagePath).toPath());

        return imagen;

    }

    public void borrar(Long id){
        Optional<Producto> productos = productoRepository.findById(id);

        Producto producto = productos.get();

        producto.setEstado(false);

        productoRepository.save(producto);
    }

}
