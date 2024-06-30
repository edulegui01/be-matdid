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
import java.util.regex.Pattern;

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
                parameters.put("imgLogo",new FileInputStream(imgLogo));
                parameters.put("timbrado", factura.getTimbrado().getNumero());
                parameters.put("fechaInicio", new SimpleDateFormat("dd-MM-yyyy").format(factura.getTimbrado().getFechainicio()));
                parameters.put("fechaVencimiento", new SimpleDateFormat("dd-MM-yyyy").format(factura.getTimbrado().getFechaVencimiento()));
                parameters.put("numFactura", factura.getNumFactura().substring(0,3).concat("-").concat(factura.getNumFactura().substring(3,6)).concat("-").concat(factura.getNumFactura().substring(6,13)));
                parameters.put("fecha", new SimpleDateFormat("dd-MM-yyyy").format(factura.getFecha()));
                if(factura.getTipoFactura().equals("contado")){
                    parameters.put("contado", "X");
                    parameters.put("credito", " ");
                } else {
                    parameters.put("contado", " ");
                    parameters.put("credito", "X");
                }
                parameters.put("razonSocial",factura.getPersona().getRazonSocial());
                parameters.put("ruc",factura.getPersona().getRuc());
                parameters.put("direccion",factura.getPersona().getDireccion());
                parameters.put("telefono",factura.getPersona().getTelefono());
                int sub0 = 0;
                int sub5 = 0;
                int sub10 = 0;
                for (DetalleFactura df : detalleFactura) {
                    if(df.getProducto().getIva() == 0) {
                        sub0 += df.getCantidad() * df.getPrecio();
                    } else if(df.getProducto().getIva() == 5){
                        sub5 += df.getCantidad() * df.getPrecio();
                    } else {
                        sub10 += df.getCantidad() * df.getPrecio();
                    }
                }
                parameters.put("sub0", sub0);
                parameters.put("sub5", sub5);
                parameters.put("sub10", sub10);
                parameters.put("iva5",(int)(sub5 * 0.05));
                parameters.put("iva10",(int)(sub10 * 0.1));
                parameters.put("totaliva", (int)(sub5 * 0.05 + sub10 * 0.1));
                parameters.put("letras", convertirALetras(Long.toString(factura.getMontoTotal()), true));




                parameters.put("total",factura.getMontoTotal());

                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(detalleFacturaIvaDTOS));
                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MMMM/yyyy")).format(new Date());
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

    private final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
    private final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
            "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
            "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
    private final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
            "setecientos ", "ochocientos ", "novecientos "};



    public String convertirALetras(String numero, boolean mayusculas) {
         final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
         final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
                "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
                "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
         final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
                "setecientos ", "ochocientos ", "novecientos "};








        String literal = "";
        String parte_decimal;
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        numero = numero.replace(".", ",");
        //si el numero no tiene parte decimal, se le agrega ,00
        if(numero.indexOf(",")==-1){
            numero = numero + ",00";
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
            //se divide el numero 0000000,00 -> entero y decimal
            String Num[] = numero.split(",");
            //de da formato al numero decimal
            parte_decimal = Num[1] + "/100 soles.";
            //se convierte el numero a literal
            if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
                literal = "cero ";
            } else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
                literal = getMillones(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 999) {//si es miles
                literal = getMiles(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 99) {//si es centena
                literal = getCentenas(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 9) {//si es decena
                literal = getDecenas(Num[0]);
            } else {//sino unidades -> 9
                literal = getUnidades(Num[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if (mayusculas) {
                return (literal).toUpperCase() + ".-";
            } else {
                return (literal) + ".-";
            }
        } else {//error, no se puede convertir
            return literal = null;
        }
    }

    /* funciones para convertir los numeros a literales */

    private String getUnidades(String numero) {// 1 - 9
        //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
        String num = numero.substring(numero.length() - 1);
        return UNIDADES[Integer.parseInt(num)];
    }

    private String getDecenas(String num) {// 99
        int n = Integer.parseInt(num);
        if (n < 10) {//para casos como -> 01 - 09
            return getUnidades(num);
        } else if (n > 19) {//para 20...99
            String u = getUnidades(num);
            if (u.equals("")) { //para 20,30,40,50,60,70,80,90
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
            }
        } else {//numeros entre 11 y 19
            return DECENAS[n - 10];
        }
    }

    private String getCentenas(String num) {// 999 o 099
        if( Integer.parseInt(num)>99 ){//es centena
            if (Integer.parseInt(num) == 100) {//caso especial
                return " cien ";
            } else {
                return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
            }
        }else{//por Ej. 099
            //se quita el 0 antes de convertir a decenas
            return getDecenas(Integer.parseInt(num)+"");
        }
    }

    private String getMiles(String numero) {// 999 999
        //obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        //obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n="";
        //se comprueba que miles tenga valor entero
        if (Integer.parseInt(m) > 0) {
            n = getCentenas(m);
            return n + "mil " + getCentenas(c);
        } else {
            return "" + getCentenas(c);
        }

    }

    private String getMillones(String numero) { //000 000 000
        //se obtiene los miles
        String miles = numero.substring(numero.length() - 6);
        //se obtiene los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n = "";
        if(millon.length()>1){
            n = getCentenas(millon) + "millones ";
        }else{
            n = getUnidades(millon) + "millon ";
        }
        return n + getMiles(miles);
    }




}
