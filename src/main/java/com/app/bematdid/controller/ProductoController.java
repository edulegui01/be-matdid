package com.app.bematdid.controller;

import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.model.Persona;
import com.app.bematdid.model.Producto;
import com.app.bematdid.service.PersonaService;
import com.app.bematdid.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("producto/listar")
    public Page<ProductoDTO> listar(Pageable pageable,@RequestParam String nombre ){

        return productoService.listar(pageable, nombre);

    }

    @GetMapping("producto/listar_select")
    public List<Producto> listarSelect(@RequestParam String search ){



        return productoService.listarSelect(search);

    }

    @PostMapping("producto/guardar")
    public Producto guardar(@RequestBody Producto producto) throws Exception{
        productoService.guardar(producto);


        return producto;
    }


    @PostMapping("producto/imagen")
    public String uploadImage(@RequestParam MultipartFile image, @RequestParam Long idProducto) throws Exception{
        return productoService.subirImagen(image,idProducto);



    }


    @GetMapping("producto/imagen")
    public ResponseEntity<?> getImage(@RequestParam String searchImagen ) throws IOException {
        byte[] imagenSearch = productoService.getImage(searchImagen);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imagenSearch);




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
