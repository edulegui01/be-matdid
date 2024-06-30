package com.app.bematdid.service;

import com.app.bematdid.model.*;
import com.app.bematdid.repository.*;
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

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CobroRepository cobroRepository;



    public ResponseEntity<Resource> exportVendidosReport(LocalDate des, LocalDate has) throws FileNotFoundException, JRException {
        List<Producto> productos = productoRepository.listadoDeProducto();
        productos.forEach(producto -> producto.setStockActual(0));


        java.sql.Date sqlDesde = java.sql.Date.valueOf(des);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(has.plusDays(1));


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
            parameters.put("desde",new SimpleDateFormat("dd-MM-yyyy").format(sqlDesde));
            parameters.put("hasta",new SimpleDateFormat("dd-MM-yyyy").format(sqlHasta));


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

    public ResponseEntity<Resource> exportCompradosReport(LocalDate des, LocalDate has) throws FileNotFoundException, JRException {
        List<Producto> productos = productoRepository.listadoDeProducto();
        productos.forEach(producto -> producto.setStockActual(0));

        java.sql.Date sqlDesde = java.sql.Date.valueOf(des);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(has.plusDays(1));

        List<Compra> compras = compraRepository.comprasPorFecha(sqlDesde, sqlHasta);

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
            final File file = ResourceUtils.getFile("classpath:reportes/ReportComprados.jasper");
            final File imgLogo = ResourceUtils.getFile("classpath:images/logo.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            final Map<String,Object> parameters = new HashMap<>();
            parameters.put("imgLogo",new FileInputStream(imgLogo));
            parameters.put("desde",new SimpleDateFormat("dd-MM-yyyy").format(sqlDesde));
            parameters.put("hasta",new SimpleDateFormat("dd-MM-yyyy").format(sqlHasta));

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

    public ResponseEntity<Resource> exportPagosReport(LocalDate des, LocalDate has) throws FileNotFoundException, JRException {

        java.sql.Date sqlDesde = java.sql.Date.valueOf(des);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(has.plusDays(1));

        List<Pago> pagos = pagoRepository.listarPorFecha(sqlDesde, sqlHasta);
        pagos.forEach(pago -> pago.setComentario(pago.getCompra().getPersona().getRazonSocial()));


        if(!pagos.isEmpty()){
            final File file = ResourceUtils.getFile("classpath:reportes/ReportPagos.jasper");
            final File imgLogo = ResourceUtils.getFile("classpath:images/logo.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            final Map<String,Object> parameters = new HashMap<>();
            parameters.put("imgLogo",new FileInputStream(imgLogo));
            parameters.put("desde",new SimpleDateFormat("dd-MM-yyyy").format(sqlDesde));
            parameters.put("hasta",new SimpleDateFormat("dd-MM-yyyy").format(sqlHasta));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,new JRBeanCollectionDataSource(pagos));
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new java.util.Date());
            StringBuilder stringBuilder = new StringBuilder().append("pagosPDF");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append("pagos")
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

    public ResponseEntity<Resource> exportCobrosReport(LocalDate des, LocalDate has) throws FileNotFoundException, JRException {

        java.sql.Date sqlDesde = java.sql.Date.valueOf(des);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(has.plusDays(1));

        List<Cobro> cobros = cobroRepository.listarPorFecha(sqlDesde, sqlHasta);
        cobros.forEach(cobro -> {
            cobro.setComentario(cobro.getFactura().getPersona().getRazonSocial());
            String fac = cobro.getFactura().getNumFactura();
            fac = fac.substring(0,3).concat("-").concat(fac.substring(3,6)).concat("-").concat(fac.substring(6,13));
            cobro.setTipoCobro(fac);
        });


        if(!cobros.isEmpty()){
            final File file = ResourceUtils.getFile("classpath:reportes/ReportCobros.jasper");
            final File imgLogo = ResourceUtils.getFile("classpath:images/logo.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            final Map<String,Object> parameters = new HashMap<>();
            parameters.put("imgLogo",new FileInputStream(imgLogo));
            parameters.put("desde",new SimpleDateFormat("dd-MM-yyyy").format(sqlDesde));
            parameters.put("hasta",new SimpleDateFormat("dd-MM-yyyy").format(sqlHasta));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,new JRBeanCollectionDataSource(cobros));
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new java.util.Date());
            StringBuilder stringBuilder = new StringBuilder().append("cobrosPDF");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append("cobros")
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

    public ResponseEntity<Resource> exportFacturasReport(LocalDate des, LocalDate has) throws FileNotFoundException, JRException {

        java.sql.Date sqlDesde = java.sql.Date.valueOf(des);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(has.plusDays(1));

        List<Factura> facturas = facturaRepository.facturasPorFecha(sqlDesde, sqlHasta);
        facturas.forEach(factura -> {
            factura.setTipoFactura(factura.getPersona().getRazonSocial());
            if(factura.getEstado().equals("SC")) {
                factura.setEstado("PENDIENTE DE COBRO");
            } else if(factura.getEstado().equals("CO")) {
                factura.setEstado("COBRADO");
            } else if(factura.getEstado().equals("CP")) {
                factura.setEstado("COBRO PARCIAL");
            }
        });


        if(!facturas.isEmpty()){
            final File file = ResourceUtils.getFile("classpath:reportes/ReportFacturas.jasper");
            final File imgLogo = ResourceUtils.getFile("classpath:images/logo.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            final Map<String,Object> parameters = new HashMap<>();
            parameters.put("imgLogo",new FileInputStream(imgLogo));
            parameters.put("desde",new SimpleDateFormat("dd-MM-yyyy").format(sqlDesde));
            parameters.put("hasta",new SimpleDateFormat("dd-MM-yyyy").format(sqlHasta));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,new JRBeanCollectionDataSource(facturas));
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new java.util.Date());
            StringBuilder stringBuilder = new StringBuilder().append("facturasPDF");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append("facturas")
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

    public ResponseEntity<Resource> exportComprasReport(LocalDate des, LocalDate has) throws FileNotFoundException, JRException {

        java.sql.Date sqlDesde = java.sql.Date.valueOf(des);
        java.sql.Date sqlHasta = java.sql.Date.valueOf(has.plusDays(1));

        List<Compra> compras = compraRepository.comprasPorFecha(sqlDesde, sqlHasta);
        compras.forEach(compra -> {
            compra.setTipoFactura(compra.getPersona().getRazonSocial());
            if(compra.getEstado().equals("SP")) {
                compra.setEstado("PENDIENTE DE PAGO");
            } else if(compra.getEstado().equals("PA")) {
                compra.setEstado("PAGADO");
            } else if(compra.getEstado().equals("PP")) {
                compra.setEstado("PAGO PARCIAL");
            }
        });



        if(!compras.isEmpty()){
            final File file = ResourceUtils.getFile("classpath:reportes/ReportCompras.jasper");
            final File imgLogo = ResourceUtils.getFile("classpath:images/logo.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);

            final Map<String,Object> parameters = new HashMap<>();
            parameters.put("imgLogo",new FileInputStream(imgLogo));
            parameters.put("desde",new SimpleDateFormat("dd-MM-yyyy").format(sqlDesde));
            parameters.put("hasta",new SimpleDateFormat("dd-MM-yyyy").format(sqlHasta));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,new JRBeanCollectionDataSource(compras));
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new java.util.Date());
            StringBuilder stringBuilder = new StringBuilder().append("comprasPDF");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append("compras")
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
