package com.app.bematdid.repository;

import com.app.bematdid.model.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query(value = "SELECT id_compra, fecha, id_funcionario,c.id_persona, monto_total, saldo,num_folio, c.estado,c.timbrado,c.tipo_factura,c.fecha_vencimiento   " +
            "FROM compra c JOIN persona p ON c.id_persona = p.id_persona " +
            "WHERE  p.razon_social ilike %:nombrePersonaFilter% and c.num_folio ilike %:numFolioFilter% " +
            "order by id_compra desc;", nativeQuery = true)
    Page<Compra> listarCompras (Pageable pageable, @Param("nombrePersonaFilter") String nombrePersona, @Param("numFolioFilter") String numFolio);

    @Query(value ="SELECT coalesce(sum(monto_total),0) FROM compra WHERE estado != 'A'", nativeQuery = true)
    Integer totalComprado();

    @Query(value ="SELECT coalesce(sum(saldo),0) FROM compra WHERE estado != 'A'", nativeQuery = true)
    Integer porPagar();

    List<Compra> findByFechaBetween(Date des, Date has);

    @Query(value ="SELECT c.id_compra, estado, fecha, fecha_vencimiento, id_funcionario, id_persona, monto_total, num_folio, timbrado, tipo_factura, saldo\n" +
            "FROM compra c\n" +
            "JOIN detalle_compra d\n" +
            "ON d.id_compra = c.id_compra\n" +
            "WHERE c.estado != 'A' AND (c.fecha BETWEEN :fechaInicio AND :fechaFin)", nativeQuery = true)
    List<Compra> comprasPorFecha(@Param("fechaInicio") Date fechaInico, @Param("fechaFin") Date fechafin);
}
