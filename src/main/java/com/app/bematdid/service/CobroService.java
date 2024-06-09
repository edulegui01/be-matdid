package com.app.bematdid.service;

import com.app.bematdid.dto.CobroDTO;
import com.app.bematdid.dto.PagoDTO;
import com.app.bematdid.error.SaldoNegativeException;
import com.app.bematdid.mapper.CobroMapper;
import com.app.bematdid.model.Cobro;
import com.app.bematdid.model.Factura;
import com.app.bematdid.model.Pago;
import com.app.bematdid.repository.CobroRepository;
import com.app.bematdid.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CobroService {
    @Autowired
    private CobroRepository cobroRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private CobroMapper mapper;

    /*public Page<CobroDTO> listar (Pageable pageable, String nombre) {
        Optional<List<CobroDTO>> lista = cobroRepository.listarCobros(nombre).map(cobros -> mapper.cobrosACobrosDTO(cobros));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }*/

    public List<CobroDTO> listarTodos(){
        List<Cobro> lista = cobroRepository.findAll();
        return mapper.cobrosACobrosDTO(lista);
    }

    public Optional<CobroDTO> obtenerPorId (Long id){
        return cobroRepository.findById(id).map(cobro -> mapper.cobroACobroDTO(cobro));
    }

    public List<CobroDTO> listarPorIdVenta(Long idFactura){
        List<Cobro> lista = cobroRepository.listarPorIdVenta(idFactura);
        return mapper.cobrosACobrosDTO(lista);

    }

    public CobroDTO guardar(CobroDTO cobroDTO) throws SaldoNegativeException {
        Cobro cobro = mapper.cobroDTOACobro(cobroDTO);
        Optional<Factura> factura = facturaRepository.findById(cobro.getIdFactura());
        factura.get().setSaldo(factura.get().getSaldo() - cobro.getMonto());
        if(factura.get().getSaldo()<0){
            throw new SaldoNegativeException("EL MONTO QUE DESEA COBRAR ES MAYOR AL SALDO DE LA FACTURA");
        }

        facturaRepository.save(factura.get());
        return mapper.cobroACobroDTO(cobroRepository.save(cobro));
    }

    public void borrar(Long id){
        Optional<Cobro> cobro = cobroRepository.findById(id);
        Cobro cobro1 = cobro.get();
        cobro1.setEstado(false);

        Optional<Factura> factura = facturaRepository.findById(cobro1.getIdFactura());
        factura.get().setSaldo(factura.get().getSaldo() + cobro1.getMonto());
        facturaRepository.save(factura.get());

        cobroRepository.save(cobro1);
    }
}
