package com.app.bematdid.service;

import com.app.bematdid.dto.PagoDTO;
import com.app.bematdid.error.SaldoNegativeException;
import com.app.bematdid.mapper.PagoMapper;
import com.app.bematdid.model.Compra;
import com.app.bematdid.model.Pago;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.CompraRepository;
import com.app.bematdid.repository.PagoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private PagoMapper mapper;

    @Autowired
    private EntityManager em;

    /*public Page<PagoDTO> listar (Pageable pageable, String nombre) {
        Optional<List<PagoDTO>> lista = pagoRepository.listarPagos(nombre).map(pagos -> mapper.pagosAPagosDTO(pagos));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }*/

    public List<PagoDTO> listarTodos(){
        List<Pago> lista = pagoRepository.findAll();
        return mapper.pagosAPagosDTO(lista);
    }

    public Optional<PagoDTO> obtenerPorId (Long id){
        return pagoRepository.findById(id).map(pago -> mapper.pagoAPagoDTO(pago));
    }

    public List<PagoDTO> listarPorIdCompra(Long idCompra){
        List<Pago> lista = pagoRepository.listarPorIdCompra(idCompra);
        return mapper.pagosAPagosDTO(lista);

    }

    public List<Object> listarPagosCobros(){
        Query nativeQuery = em.createNativeQuery("select fecha, monto, 'PAGO' from pago\n" +
                "UNION\n" +
                "SELEct fecha, monto, 'COBRO' from cobro\n" +
                "order by fecha");

        List<Object> resulsts = nativeQuery.getResultList();

        return resulsts;

    }

    public PagoDTO guardar(PagoDTO pagoDTO) throws SaldoNegativeException{
        Pago pago = mapper.pagoDTOAPago(pagoDTO);
        Optional<Compra> compra = compraRepository.findById(pago.getIdCompra());
        compra.get().setSaldo(compra.get().getSaldo() - pago.getMonto());
        if(compra.get().getSaldo()<0){
            throw new SaldoNegativeException("EL MONTO QUE DESEA PAGAR ES MAYOR AL SALDO DE LA FACTURA");
        }

        compraRepository.save(compra.get());
        return mapper.pagoAPagoDTO(pagoRepository.save(pago));
    }

    public void borrar(Long id){
        Optional<Pago> pago = pagoRepository.findById(id);
        Pago pago1 = pago.get();
        pago1.setEstado(false);

        Optional<Compra> compra = compraRepository.findById(pago1.getIdCompra());
        compra.get().setSaldo(compra.get().getSaldo() + pago1.getMonto());
        compraRepository.save(compra.get());

        pagoRepository.save(pago1);
    }
}
