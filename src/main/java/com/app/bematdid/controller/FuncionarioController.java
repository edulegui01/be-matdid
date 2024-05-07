package com.app.bematdid.controller;

import com.app.bematdid.dto.FuncionarioDTO;
import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Persona;
import com.app.bematdid.service.FuncionarioService;
import com.app.bematdid.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @CrossOrigin(origins = {"http://localhost:4200"})
    @GetMapping("funcionario/listar")
    public Page<FuncionarioDTO> listar(Pageable pageable, @RequestParam String cedulaFilter, @RequestParam String nombreFilter){

        return funcionarioService.listar(pageable,cedulaFilter,nombreFilter);

    }

    @GetMapping("funcionario/listar_select")
    public List<FuncionarioDTO> listar_select(@RequestParam String search){

        return funcionarioService.listar_select(search);

    }


    @PostMapping("funcionario/guardar")
    public Funcionario guardar(@RequestBody Funcionario funcionario){

        funcionarioService.guardar(funcionario);

        return funcionario;
    }


    @CrossOrigin("origins = http://localhost:4200")
    @PutMapping("funcionario/actualizar/{idFun}")
    public Funcionario actualizar(@RequestBody Funcionario funcionarioModi,@PathVariable Long idFun){

        return funcionarioService.actualizar(funcionarioModi,idFun);

    }


    @CrossOrigin("origins = http://localhost:4200")
    @DeleteMapping("funcionario/borrar/{idPer}")
    public void borrar(@PathVariable Long idPer){
        funcionarioService.borrar(idPer);

    }
}
