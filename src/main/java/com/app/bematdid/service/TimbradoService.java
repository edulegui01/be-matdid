package com.app.bematdid.service;

import com.app.bematdid.dto.TimbradoDTO;
import com.app.bematdid.mapper.TimbradoMapper;
import com.app.bematdid.model.Timbrado;
import com.app.bematdid.repository.TimbradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimbradoService {

    @Autowired
    private TimbradoRepository timbradoRepository;

    @Autowired
    private TimbradoMapper timbradoMapper;

    public Page<TimbradoDTO> listarTodo(Pageable pageable) {
        List<TimbradoDTO> lista = timbradoMapper.timbradosATimbradosDTO(timbradoRepository.findAll());
        return new PageImpl <>(lista,  pageable, lista.size());
    }

    public Optional<TimbradoDTO> obtenerPorId (Long id){
       return timbradoRepository.findById(id).map(timbrado -> timbradoMapper.timbradoATimbradoDTO(timbrado));
    }

    public List<Timbrado> getTimbradoValido(){
        return timbradoRepository.getTimbradoValido();
    }

    public TimbradoDTO guardar(TimbradoDTO timbradoDTO) {
        Timbrado timbrado = timbradoMapper.timbradoDTOATimbrado(timbradoDTO);
        return timbradoMapper.timbradoATimbradoDTO(timbradoRepository.save(timbrado));
    }

    public void borrar (Long id) {
        timbradoRepository.deleteById(id);
    }

}
