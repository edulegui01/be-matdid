package com.app.bematdid.service;

import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.mapper.ProductoMapper;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @Autowired
    private ProductoMapper productoMapper;

    public Page<ProductoDTO> listar(Pageable pageable, String nombre){
        Page<Producto> productos = productoRepository.listarProducto(pageable, nombre);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public List<ProductoDTO> listarSelect(String search){

        return productoMapper.productosAProductosDTO(productoRepository.listarSelect(search));

    }

    public Page<ProductoDTO> listarPorCategoria (Pageable pageable, int idCategoria){
        Page<Producto> productos = productoRepository.listarPorCategoria(pageable,idCategoria);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public Page<ProductoDTO> listarPorCiclo (Pageable pageable, int idCiclo){
        Page<Producto> productos = productoRepository.listarPorCiclo(pageable,idCiclo);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public Page<ProductoDTO> listarPorMateria (Pageable pageable, int idMateria){
        Page<Producto> productos = productoRepository.listarPorMateria(pageable,idMateria);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public ProductoDTO guardar(ProductoDTO productoDTO){

        Producto producto = productoMapper.productoDTOAProducto(productoDTO);
        return productoMapper.productoAProductoDTO(productoRepository.save(producto));
    }


    public  Producto actualizar(Producto request,Long id){

        return productoRepository.save(request);

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
