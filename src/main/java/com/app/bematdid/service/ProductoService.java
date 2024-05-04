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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

    private final static String UPLOADS_FOLDER = "uploads";

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

        String uniqueFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        Optional<Producto> productoBuscado = productoRepository.findById(idProducto);

        Producto producto =  productoBuscado.get();

        producto.setImage(uniqueFilename);

        Producto productoSave = productoRepository.save(producto);

        if (!image.isEmpty()) {
            if (producto.getIdProducto() > 0 && producto.getImage() != null && producto.getImage().length() > 0) {
                deleteImage(producto.getImage());
            }
        }


        Path rootPath = getPath(uniqueFilename);
        Files.copy(image.getInputStream(),rootPath);

        return uniqueFilename;


    }


    public Resource getImage(String nombreImagen) throws IOException {
        Path path = getPath(nombreImagen);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            //throw new RuntimeException("Error in path: " + path.toString());

            resource = null;
        }
        return resource;

    }

    public void borrar(Long id){
        Optional<Producto> productos = productoRepository.findById(id);

        Producto producto = productos.get();

        producto.setEstado(false);

        productoRepository.save(producto);
    }


    public boolean deleteImage(String filename) {
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();
        if(file.exists() && file.canRead()) {
            if(file.delete()) {
                return true;
            }
        }
        return false;
    }

    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

}
