package com.app.bematdid.service;


import com.app.bematdid.dto.MovimientoDTO;
import com.app.bematdid.dto.MovimientoSaveDTO;
import com.app.bematdid.error.StockNegativeException;
import com.app.bematdid.mapper.MovimientoMapper;
import com.app.bematdid.model.*;
import com.app.bematdid.repository.MotivoRepository;
import com.app.bematdid.repository.MovimientoRepository;
import com.app.bematdid.repository.ProductoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private MotivoRepository motivoRepository;

    @Autowired
    private MovimientoMapper mapper;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    EntityManager entityManager;

    public Page<MovimientoDTO> listar (Pageable pageable, String motivo) {
        Optional<List<MovimientoDTO>> lista = movimientoRepository.listarMovimiento(motivo).map(movimientos -> mapper.movimientosAMovimientosDTO(movimientos));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());

    }

    public Page<MovimientoDTO> listado(Pageable pageable, String nombreFuncionario, String idMotivo){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movimiento> query = criteriaBuilder.createQuery(Movimiento.class);
        Root<Movimiento> movimientoRoot = query.from(Movimiento.class);
        Join<Movimiento, Funcionario> funcioanario = movimientoRoot.join("funcionario");

        List<Predicate> searchCriteria = new ArrayList<>();

        //se agrega el where estado = true
        searchCriteria.add(criteriaBuilder.isTrue(movimientoRoot.get("estado")));

        if(nombreFuncionario != null && !nombreFuncionario.isEmpty()){
            Expression<String> exp1 = criteriaBuilder.concat(funcioanario.get("nombre")," ");
            searchCriteria.add(criteriaBuilder.like(criteriaBuilder.upper(criteriaBuilder.concat(exp1,funcioanario.get("apellido"))),
                    "%"+nombreFuncionario.toUpperCase()+"%"));
        }
        if(idMotivo != null && !idMotivo.isEmpty()){
            searchCriteria.add(criteriaBuilder.equal(movimientoRoot.get("idMotivo"),idMotivo));
        }


        Predicate[] restriccions = searchCriteria.toArray(new Predicate[0]);
        query.select(movimientoRoot).where(criteriaBuilder.and(restriccions)).orderBy(criteriaBuilder.
                desc(movimientoRoot.get("fecha")));

        TypedQuery<Movimiento> queryResult = entityManager.createQuery(query);

        int total =queryResult.getResultList().size();

        List<Movimiento> pagedData = paginateQuery(entityManager.createQuery(query), pageable).getResultList();

        List<MovimientoDTO> pagedDataDTO = mapper.movimientosAMovimientosDTO(pagedData);



        //calcular cantidad de item en la consulta
        /*CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Producto> productoCountRoot = countQuery.from(Producto.class);
        countQuery
                .select(criteriaBuilder.count(productoCountRoot))
                .where(criteriaBuilder.
                        and(restriccions));
        var totalCount = entityManager.createQuery(countQuery).getSingleResult();*/

        return new PageImpl<>(pagedDataDTO,pageable,total);




    }

    public static <T> TypedQuery<T> paginateQuery(TypedQuery<T> query, Pageable pageable) {
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        return query;
    }



    public MovimientoDTO guardar(MovimientoSaveDTO movimientoDTO) throws StockNegativeException {
        Movimiento movimiento = mapper.movimientoDTOAMovimiento(movimientoDTO);
        /*movimiento.getDetalleMovimientos().forEach(detalleMovimiento -> {
            detalleMovimiento.setMovimiento(movimiento);
            if(movimiento.getEsIngreso()) {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() + detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            } else {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() - detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            }
        });*/




        Optional<Motivo> motivo = motivoRepository.findById(movimiento.getIdMotivo());

        if(motivo.get().getEsIngreso()){
            movimiento.getDetalleMovimientos().forEach(detalleMovimiento -> {
            detalleMovimiento.setMovimiento(movimiento);
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() + detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            });
        }else{
            validationStock(movimiento);
            movimiento.getDetalleMovimientos().forEach(detalleMovimiento -> {
            detalleMovimiento.setMovimiento(movimiento);
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() - detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());

            });
        }

        movimientoRepository.save(movimiento);
        return mapper.movimientoAMovimientoDTO(movimiento);
    }

    public void validationStock(Movimiento movimiento) throws StockNegativeException{
        movimiento.getDetalleMovimientos().forEach(detalleMovimiento -> {
            detalleMovimiento.setMovimiento(movimiento);
            Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
            if(producto.get().getStockActual() - detalleMovimiento.getCantidad()<0){
                try {
                    throw new StockNegativeException(String.format("QUEDAN %s UNIDADES DE %s",producto.get().getStockActual(),producto.get().getNombre()));
                } catch (StockNegativeException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public void borrar(Long idMovimiento){
        Optional<Movimiento> movimiento = movimientoRepository.findById(idMovimiento);
        movimiento.get().setEstado(false);
        /*movimiento.get().getDetalleMovimientos().forEach(detalleMovimiento -> {
            if(movimiento.get().getEsIngreso()) {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() - detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            }else {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() + detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            }
        });*/

        Optional<Motivo> motivo = motivoRepository.findById(movimiento.get().getMotivo().getIdMotivo());

        if(motivo.get().getEsIngreso()){
            movimiento.get().getDetalleMovimientos().forEach(detalleMovimiento -> {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() - detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());
            });
        }else{
            movimiento.get().getDetalleMovimientos().forEach(detalleMovimiento -> {
                Optional<Producto> producto = productoRepository.findById(detalleMovimiento.getId().getIdProducto());
                producto.get().setStockActual(producto.get().getStockActual() + detalleMovimiento.getCantidad());
                productoRepository.save(producto.get());

            });
        }



        movimientoRepository.save(movimiento.get());
    }
}
