package com.app.bematdid.service;

import com.app.bematdid.dto.CompraDTO;
import com.app.bematdid.dto.DetalleFacturaIvaDTO;
import com.app.bematdid.dto.FacturaDTO;
import com.app.bematdid.error.StockNegativeException;
import com.app.bematdid.mapper.DetalleFacturaIvaMapper;
import com.app.bematdid.mapper.FacturaMapper;
import com.app.bematdid.model.*;
import com.app.bematdid.repository.FacturaRepository;
import com.app.bematdid.repository.FolioRepository;
import com.app.bematdid.repository.ProductoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturaMapper mapper;

    @Autowired
    private FolioRepository folioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    private DetalleFacturaIvaMapper detalleFacturaIvaMapper = new DetalleFacturaIvaMapper();

    public Page<FacturaDTO> listar (Pageable pageable, String nombrePersona, String numFactura) {
        Page<Factura> lista = facturaRepository.listarFacturas(pageable, nombrePersona, numFactura);
        List<FacturaDTO> dtos = mapper.facturasAFacturasDTO(lista.getContent());
        return new PageImpl<>(dtos, pageable, lista.getTotalElements());

    }

    public FacturaDTO guardar (FacturaDTO facturaDTO) throws StockNegativeException{
        Factura factura = mapper.facturaDTOAFactura(facturaDTO);
        Folio folio = folioRepository.getNumFolio();

        validationStock(factura);
        factura.getDetalleFacturas().forEach(detalleFactura -> {
            detalleFactura.setFactura(factura);

            Optional<Producto> producto = productoRepository.findById(detalleFactura.getId().getIdProducto());
            producto.get().setStockActual(producto.get().getStockActual()-detalleFactura.getCantidad());
            productoRepository.save(producto.get());
        });
        Factura factura1 =  facturaRepository.save(factura);

        factura1.setNumFactura(folio.getNumeracionFolio()+String.format("%07d",factura1.getIdFactura()));

        facturaRepository.save(factura1);

        return mapper.facturaAFacturaDTO(factura1);
    }


    public void validationStock(Factura factura) throws StockNegativeException {
        /*factura.getDetalleFacturas().forEach(detalleFactura -> {
            detalleFactura.setFactura(factura);
            Optional<Producto> producto = productoRepository.findById(detalleFactura.getId().getIdProducto());
            if(producto.get().getStockActual() - detalleFactura.getCantidad()<0){
                try {
                    throw new StockNegativeException(String.format("QUEDAN %s UNIDADES DE %s",producto.get().getStockActual(),producto.get().getNombre()));
                } catch (StockNegativeException e) {
                    throw new RuntimeException(e);
                }
            }

        });*/
        List<DetalleFactura> lista = new ArrayList<>();
        for (DetalleFactura detalleFactura : factura.getDetalleFacturas()) {
            boolean encontrado = false;
            for (DetalleFactura e : lista) {
                if (e.getId().getIdProducto() == detalleFactura.getId().getIdProducto()) {
                    e.setCantidad(e.getCantidad()+detalleFactura.getCantidad());

                    encontrado = true;
                }
            }
            if(!encontrado) {
                lista.add(detalleFactura);
            }
        };

        lista.forEach(detalleFactura -> {
            detalleFactura.setFactura(factura);
            Optional<Producto> producto = productoRepository.findById(detalleFactura.getId().getIdProducto());
            if(producto.get().getStockActual() - detalleFactura.getCantidad()<0){
                try {
                    throw new StockNegativeException(String.format("QUEDAN %s UNIDADES DE %s",producto.get().getStockActual(),producto.get().getNombre()));
                } catch (StockNegativeException e) {
                    throw new RuntimeException(e);
                }
            }

        });


    }


    public void borrar(Long idFactura){
        Optional<Factura> factura = facturaRepository.findById(idFactura);
        factura.get().setEstado("A");
        factura.get().getDetalleFacturas().forEach(detalleFactura -> {
            Optional<Producto> producto = productoRepository.findById(detalleFactura.getId().getIdProducto());
            producto.get().setStockActual(producto.get().getStockActual()+detalleFactura.getCantidad());
            productoRepository.save(producto.get());
        });

        facturaRepository.save(factura.get());
    }


    public ResponseEntity<Resource> imprimirFactura (long idFactura) {
        Optional<Factura> otpFactura = facturaRepository.findById(idFactura);

        if (otpFactura.isPresent()) {
            try {
                final Factura factura = otpFactura.get();
                final List<DetalleFactura> detalleFactura = factura.getDetalleFacturas();
                final List<DetalleFacturaIvaDTO> detalleFacturaIvaDTOS = detalleFacturaIvaMapper.aDetalleFacturaIvaDTOS(detalleFactura);
                final File file = ResourceUtils.getFile("classpath:reportes/facturaPdf.jasper");
                final File imgLogo = ResourceUtils.getFile("classpath:images/logo.png");
                final JasperReport report = (JasperReport) JRLoader.loadObject(file);
                final HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("total",factura.getMontoTotal());
                parameters.put("imgLogo",new FileInputStream(imgLogo));
                //parameters.put("detalle_factura", factura.getDetalleFacturas());
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(detalleFacturaIvaDTOS));
                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
                StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                        .filename(stringBuilder.append("NOMBRE")
                                .append("generateDate:")
                                .append(sdf)
                                .append(".pdf")
                                .toString())
                        .build();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long) reporte.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers).body(new ByteArrayResource(reporte));


            }catch (Exception e){
                e.printStackTrace();
            }


        } else {
            return ResponseEntity.noContent().build();
        }
        return null;
    }


}
