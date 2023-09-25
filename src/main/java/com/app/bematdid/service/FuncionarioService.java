package com.app.bematdid.service;


import com.app.bematdid.dto.FuncionarioDTO;
import com.app.bematdid.dto.PersonaDTO;
import com.app.bematdid.mapper.FuncionarioMapper;
import com.app.bematdid.mapper.PersonaMapper;
import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Persona;
import com.app.bematdid.repository.FuncionarioRepository;
import com.app.bematdid.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    private FuncionarioMapper funcionarioMapper = new FuncionarioMapper();

    public Page<FuncionarioDTO> listar(Pageable pageable, String cedulaFilter, String nombreFilter){

        Page<Funcionario> resultPage = funcionarioRepository.listarFuncionarios(pageable,cedulaFilter,nombreFilter);



        return funcionarioMapper.mapEntityPageIntoDTOPage(pageable,resultPage);



    }

    public void guardar(Funcionario funcionario){

        funcionarioRepository.save(funcionario);
    }

    public  Funcionario actualizar(Funcionario request,Long id){
        Optional<Funcionario> funcionarios = funcionarioRepository.findById(id);

        Funcionario funcionario = funcionarios.get();
        funcionario.setNombre(request.getNombre());
        funcionario.setApellido(request.getApellido());
        funcionario.setCedula(request.getCedula());
        funcionario.setFechaAlta(request.getFechaAlta());
        funcionario.setTelefono(request.getTelefono());
        funcionario.setDireccion(request.getDireccion());
        funcionario.setFechaNac(request.getFechaNac());
        funcionario.setActivo(request.getActivo());
        funcionario.setLocalidad(request.getLocalidad());


        return funcionarioRepository.save(funcionario);



    }

    public void borrar(Long id){
        Optional<Funcionario> funcionarios = funcionarioRepository.findById(id);

        Funcionario funcionario = funcionarios.get();

        funcionario.setEstado(false);

        funcionarioRepository.save(funcionario);
    }

}
