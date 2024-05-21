package com.app.bematdid.service;

import com.app.bematdid.dto.MotivoDTO;
import com.app.bematdid.mapper.MotivoMapper;
import com.app.bematdid.model.Motivo;
import com.app.bematdid.repository.MotivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotivoService {
    @Autowired
    private MotivoRepository motivoRepository;

    @Autowired
    private MotivoMapper mapper;

    public Page<MotivoDTO> listar (Pageable pageable, String nombre) {
        Optional<List<MotivoDTO>> lista = motivoRepository.listarMotivos(nombre).map(motivos -> mapper.motivossAMotivosDTO(motivos));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }

    public List<MotivoDTO> listarTodos(){

        Optional<List<MotivoDTO>> lista = motivoRepository.listarMotivos("").map(motivos -> mapper.motivossAMotivosDTO(motivos));
        return lista.get();
    }

    public Optional<MotivoDTO> obtenerPorId (Integer id){
        return motivoRepository.findById(id).map(motivo -> mapper.motivoAMotivoDTO(motivo));
    }

    public MotivoDTO guardar(MotivoDTO motivoDTO) {
        Motivo motivo = mapper.motivoDTOAMotivo(motivoDTO);
        return mapper.motivoAMotivoDTO(motivoRepository.save(motivo));
    }

    public void borrar(Integer id){
        Optional<Motivo> motivo = motivoRepository.findById(id);

        Motivo motivo1 = motivo.get();

        motivo1.setEstado(false);

        motivoRepository.save(motivo1);
    }
}
