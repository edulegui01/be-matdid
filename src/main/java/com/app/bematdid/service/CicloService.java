package com.app.bematdid.service;

import com.app.bematdid.dto.CicloDTO;
import com.app.bematdid.mapper.CicloMapper;
import com.app.bematdid.model.Ciclo;
import com.app.bematdid.repository.CicloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CicloService {

    @Autowired
    private CicloRepository cicloRepository;

    @Autowired
    private CicloMapper mapper;

    public Page<CicloDTO> listar (Pageable pageable, String nombre) {
        Optional<List<CicloDTO>> lista = cicloRepository.listarCiclos(nombre).map(ciclos -> mapper.ciclosACiclosDTO(ciclos));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }

    public List<CicloDTO> listarTodos(){

        //return cicloRepository.ListarCilcoSelect();
        Optional<List<CicloDTO>> lista = cicloRepository.listarCiclos("").map(ciclos -> mapper.ciclosACiclosDTO(ciclos));
        return lista.get();
    }

    public Optional<CicloDTO> obtenerPorId (Integer id){
        return cicloRepository.findById(id).map(ciclo -> mapper.cicloACicloDTO(ciclo));
    }

    public CicloDTO guardar(CicloDTO cicloDTO) {
        Ciclo ciclo = mapper.cicloDTOACiclo(cicloDTO);
        return mapper.cicloACicloDTO(cicloRepository.save(ciclo));
    }

    public void borrar(Integer id){
        Optional<Ciclo> ciclo = cicloRepository.findById(id);

        Ciclo ciclo1 = ciclo.get();

        ciclo1.setEstado(false);

        cicloRepository.save(ciclo1);
    }
}
