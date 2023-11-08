package com.app.bematdid.service;

import com.app.bematdid.dto.GastoDTO;
import com.app.bematdid.mapper.GastoMapper;
import com.app.bematdid.model.Gasto;
import com.app.bematdid.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private GastoMapper mapper;

    public Page<GastoDTO> listar (Pageable pageable, String categoria, String beneficiario, String comentario) {
        Optional<List<GastoDTO>> lista = gastoRepository.listarGastos(categoria, beneficiario, comentario).map(gastos -> mapper.gastosAGastosDTO(gastos));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());

    }

    public Optional<GastoDTO> obtenerPorId (Long id){
        return gastoRepository.findById(id).map(gasto -> mapper.gastoAgastoDTO(gasto));
    }

    public GastoDTO guardar(GastoDTO gastoDTO) {
        Gasto gasto = mapper.gastoDTOAGasto(gastoDTO);
        return mapper.gastoAgastoDTO(gastoRepository.save(gasto));
    }

    public void borrar(Long id){
        Optional<Gasto> gasto = gastoRepository.findById(id);

        Gasto gasto1 = gasto.get();

        gasto1.setEstado(false);

        gastoRepository.save(gasto1);
    }


}
