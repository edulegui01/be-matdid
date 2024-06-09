package com.app.bematdid.service;

import com.app.bematdid.dto.MovimientoCajaDTO;
import com.app.bematdid.mapper.MovimientoCajaMapper;
import com.app.bematdid.model.MovimientoCaja;
import com.app.bematdid.repository.MovimientoCajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoCajaService {

    @Autowired
    private MovimientoCajaRepository movimientoCajaRepository;
    @Autowired
    private MovimientoCajaMapper mapper;

    public Page<MovimientoCajaDTO> listar (Pageable pageable, String beneficiario, String comentario) {
        Optional<List<MovimientoCajaDTO>> lista = movimientoCajaRepository.listarMovimientosCaja( beneficiario, comentario).map(movimientoCajas -> mapper.movimientosCajaAMovimientosCajaDTO(movimientoCajas));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());

    }

    public Optional<MovimientoCajaDTO> obtenerPorId (Long id){
        return movimientoCajaRepository.findById(id).map(movimientoCaja -> mapper.movimientoCajaAMovimientoCajaDTO(movimientoCaja));
    }

    public MovimientoCajaDTO guardar(MovimientoCajaDTO movimientoCajaDTO) {
        MovimientoCaja movimientoCaja = mapper.movimientoCajaDTOAMovimientoCaja(movimientoCajaDTO);
        return mapper.movimientoCajaAMovimientoCajaDTO(movimientoCajaRepository.save(movimientoCaja));
    }

    public void borrar(Long id){
        Optional<MovimientoCaja> movimientoCaja = movimientoCajaRepository.findById(id);

        MovimientoCaja movimientoCaja1 = movimientoCaja.get();

        movimientoCaja1.setEstado(false);

        movimientoCajaRepository.save(movimientoCaja1);
    }


}
