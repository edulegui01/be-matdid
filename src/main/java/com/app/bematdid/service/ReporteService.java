package com.app.bematdid.service;

import com.app.bematdid.model.Compra;
import com.app.bematdid.model.Factura;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.CompraRepository;
import com.app.bematdid.repository.FacturaRepository;
import com.app.bematdid.repository.ProductoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;

@Service
public class ReporteService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private CompraRepository compraRepository;



    public ResponseEntity<Resource> exportVendidosReport(LocalDate des, LocalDate has) throws FileNotFoundException, JRException {
        List<Producto> productos = productoRepository.listadoDeProducto();
        productos.forEach(producto -> producto.setStockActual(0));

        LocalDate desde = LocalDate.of(2023,01,30);
        java.sql.Date sqlDesde = java.sql.Date.valueOf(des);
        LocalDate hasta = LocalDate.of(2025,01,30);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(has.plusDays(1));

        //List<Factura> facturas = facturaRepository.findByFechaBetween(sqlDesde, sqlHasta);
        List<Factura> facturas = facturaRepository.facturasPorFecha(sqlDesde,sqlHasta);

        facturas.forEach(factura -> {
            factura.getDetalleFacturas().forEach(detalleFactura -> {
                productos.forEach(producto -> {
                    if(producto.getIdProducto() == detalleFactura.getId().getIdProducto()){
                        producto.setStockActual(producto.getStockActual() + detalleFactura.getCantidad());
                    }

                });
            });
        });

        if(!productos.isEmpty()){
            final File file = ResourceUtils.getFile("classpath:reportes/ReportVendidos.jasper");
            final File imgLogo = ResourceUtils.getFile("classpath:images/logo.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            final Map<String,Object> parameters = new HashMap<>();
            parameters.put("imgLogo",new FileInputStream(imgLogo));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,new JRBeanCollectionDataSource(productos));
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new java.util.Date());
            StringBuilder stringBuilder = new StringBuilder().append("invetarioPDF");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append("inventario")
                            .append("generateDay:")
                            .append(sdf)
                            .append(".pdf").toString()).build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);
            return ResponseEntity.ok().contentLength((long) reporte.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers).body(new ByteArrayResource(reporte));

        }else{
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<Resource> exportCompradosReport() throws FileNotFoundException, JRException {
        List<Producto> productos = productoRepository.listadoDeProducto();
        productos.forEach(producto -> producto.setStockActual(0));

        LocalDate desde = LocalDate.of(2023,01,30);
        java.sql.Date sqlDesde = java.sql.Date.valueOf(desde);
        LocalDate hasta = LocalDate.of(2025,01,30+1);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(hasta);

        List<Compra> compras = compraRepository.findByFechaBetween(sqlDesde, sqlHasta);

        compras.forEach(compra -> {
            compra.getDetalleCompra().forEach(detalleCompra -> {
                productos.forEach(producto -> {
                    if(producto.getIdProducto() == detalleCompra.getId().getIdProducto()){
                        producto.setStockActual(producto.getStockActual() + detalleCompra.getCantidad());
                    }

                });
            });
        });

        if(!productos.isEmpty()){
            final File file = ResourceUtils.getFile("classpath:reportes/ReportVendidos.jasper");
            final File imgLogo = ResourceUtils.getFile("classpath:images/logo.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            final Map<String,Object> parameters = new HashMap<>();
            parameters.put("imgLogo",new FileInputStream(imgLogo));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,new JRBeanCollectionDataSource(productos));
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new java.util.Date());
            StringBuilder stringBuilder = new StringBuilder().append("invetarioPDF");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append("inventario")
                            .append("generateDay:")
                            .append(sdf)
                            .append(".pdf").toString()).build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);
            return ResponseEntity.ok().contentLength((long) reporte.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers).body(new ByteArrayResource(reporte));

        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
