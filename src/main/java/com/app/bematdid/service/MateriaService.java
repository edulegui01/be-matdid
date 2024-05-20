package com.app.bematdid.service;

import com.app.bematdid.dto.MateriaDTO;
import com.app.bematdid.mapper.MateriaMapper;
import com.app.bematdid.model.Materia;
import com.app.bematdid.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private MateriaMapper mapper;

    public Page<MateriaDTO> listar (Pageable pageable, String nombre) {
        Optional<List<MateriaDTO>> lista = materiaRepository.listarMaterias(nombre).map(materias -> mapper.materiasAMateriasDTO(materias));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }

    public List<MateriaDTO> listarTodos(){

        Optional<List<MateriaDTO>> lista = materiaRepository.listarMaterias("").map(materias -> mapper.materiasAMateriasDTO(materias));
        return lista.get();
    }

    public Optional<MateriaDTO> obtenerPorId (Integer id){
        return materiaRepository.findById(id).map(materia -> mapper.materiaAMateriaDTO(materia));
    }

    public MateriaDTO guardar(MateriaDTO materiaDTO) {
        Materia materia = mapper.materiaDTOAMateria(materiaDTO);
        return mapper.materiaAMateriaDTO(materiaRepository.save(materia));
    }

    public void borrar(Integer id){
        Optional<Materia> materia = materiaRepository.findById(id);

        Materia materia1 = materia.get();

        materia1.setEstado(false);

        materiaRepository.save(materia1);
    }
}
