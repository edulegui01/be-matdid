package com.app.bematdid.controller;

import com.app.bematdid.dto.AutorDTO;
import com.app.bematdid.dto.CategoriaDTO;
import com.app.bematdid.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("autor/listar")
    public Page<AutorDTO> listar (Pageable pageable, @RequestParam String nombre) {
        return autorService.listar(pageable, nombre);
    }

    @GetMapping("autor_select/listar")
    public List<AutorDTO> listarSelect(){

        return autorService.listarTodos();

    }

    @GetMapping("autor/listar/ciclo")
    public List<AutorDTO> listarByCiclo(@RequestParam Long idCiclo){
        return autorService.listarByCiclo(idCiclo);
    }

    @GetMapping("autor/{id}")
    private Optional<AutorDTO> obtenerPorId (@PathVariable("id") Integer id){
        return autorService.obtenerPorId(id);
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PostMapping("autor/guardar")
    public AutorDTO guardar(@RequestBody AutorDTO autorDTO) {
        return autorService.guardar(autorDTO);
    }

    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("autor/borrar/{id}")
    public void borrar (@PathVariable("id") Integer id) {
        autorService.borrar(id);
    }
}
