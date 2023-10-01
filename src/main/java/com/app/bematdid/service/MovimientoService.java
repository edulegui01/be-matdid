package com.app.bematdid.service;


import com.app.bematdid.model.Movimiento;
import com.app.bematdid.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;


    public void guardar(Movimiento movimiento){

        movimientoRepository.save(movimiento);
    }
}
