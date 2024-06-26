package com.app.bematdid.service;

import com.app.bematdid.dto.LocalidadDTO;
import com.app.bematdid.mapper.LocalidadMapper;
import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Localidad;
import com.app.bematdid.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalidadService {

    @Autowired
    private LocalidadRepository localidadRepository;

    private LocalidadMapper localidadMapper = new LocalidadMapper();
    public void guardar(Localidad localidad){
        localidadRepository.save(localidad);
    }

    public Page<LocalidadDTO> listar(Pageable pageable, String nombre){
        Page<Localidad> result = localidadRepository.listarLocalidad(pageable,nombre);

        return localidadMapper.mapEntityPageIntoDTOPage(pageable,result);
    }

    public Page<LocalidadDTO> listar2(Pageable pageable, String nombre) {
        Page<Localidad> result = localidadRepository.listarLocalidad(pageable,nombre);



        return localidadMapper.mapEntityPageIntoDTOPage(pageable,result);
    }

    public List<Localidad> listarTodos(){

        return localidadRepository.findAll();
    }

    public Localidad actualizar(Localidad request, Long id) {
        Optional<Localidad> localidades = localidadRepository.findById(id);

        Localidad localidad = localidades.get();
        localidad.setNombre(request.getNombre());

        return localidadRepository.save(localidad);
    }

    public void borrar(Long id){
        Optional<Localidad> localidades = localidadRepository.findById(id);

        Localidad localidad = localidades.get();

        localidad.setEstado(false);

        localidadRepository.save(localidad);
    }
}
