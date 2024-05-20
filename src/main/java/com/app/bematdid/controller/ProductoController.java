package com.app.bematdid.controller;

import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.model.Producto;
import com.app.bematdid.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("producto/listar")
    public Page<ProductoDTO> listar(Pageable pageable,@RequestParam String nombre ){

        return productoService.listar(pageable, nombre);

    }
    @GetMapping("producto/listado")
    public Page<Producto> listadoProducto(Pageable pageable,@RequestParam String nombre, @RequestParam String idCiclo){
        return productoService.getListProduct(pageable,nombre,idCiclo);
    }



    @GetMapping("producto/listar_select")
    public List<ProductoDTO> listarSelect(@RequestParam String search ){



        return productoService.listarSelect(search);

    }

    @GetMapping("producto/editorial/{idEditorial}")
    public Page<ProductoDTO> listarPorEditorial (Pageable pageable,@PathVariable("idEditorial") int idEditorial) {
        return productoService.listarPorEditorial(pageable,idEditorial);
    }

    @GetMapping("producto/categoria/{idCategoria}")
    public Page<ProductoDTO> listarPorCategoria (Pageable pageable,@PathVariable("idCategoria") int idCategoria) {
        return productoService.listarPorCategoria(pageable,idCategoria);
    }

    @GetMapping("producto/ciclo/{idCiclo}")
    public Page<ProductoDTO> listarPorCiclo (Pageable pageable,@PathVariable("idCiclo") int idCilco) {
        return productoService.listarPorCiclo(pageable,idCilco);
    }

    @GetMapping("producto/materia/{idMateria}")
    public Page<ProductoDTO> listarPorMateria (Pageable pageable,@PathVariable("idMateria") int idMateria) {
        return productoService.listarPorMateria(pageable,idMateria);
    }

    @PostMapping("producto/guardar")
    public ProductoDTO guardar(@RequestBody ProductoDTO productoDTO) throws Exception{
        return productoService.guardar(productoDTO);

    }


    @PostMapping("producto/imagen")
    public Map<String,String> uploadImage(@RequestParam MultipartFile image, @RequestParam Long idProducto) throws Exception{


        Map<String,String> imageResult = new HashMap<String,String>();

        imageResult.put("imageName",productoService.subirImagen(image,idProducto));



        return imageResult;



    }


    @GetMapping("producto/imagen")
    public ResponseEntity<Resource> getImage(@RequestParam String searchImagen ) throws IOException {
        Resource resource = null;
        try {
            resource = productoService.getImage(searchImagen);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(resource);




    }




    /*@PostMapping("im/save")
    public void saveMeme( @RequestParam("file") MultipartFile image )
            throws Exception {
            if (!image.isEmpty()) {
                String uniqueFileName = productoService.copy(image);
                productoService.setImage(uniqueFileName);
            }
            memeService.save(meme);
            status.setComplete();

    }*/


    @CrossOrigin("origins = http://localhost:4200")
    @PutMapping("producto/actualizar/{idPer}")
    public Producto actualizar(@RequestBody Producto prodcutoModi,@PathVariable Long idPer){

        return productoService.actualizar(prodcutoModi,idPer);

    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("producto/borrar/{idPer}")
    public void borrar(@PathVariable Long idPer){
        productoService.borrar(idPer);

    }
}
