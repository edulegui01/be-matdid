package com.app.bematdid.controller;

import com.app.bematdid.service.ReporteService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalDate;

@RestController
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("reporte/vendidos")
    public ResponseEntity<Resource> exportVendidosReport(@RequestParam LocalDate des, @RequestParam LocalDate has) throws JRException, FileNotFoundException {
        return reporteService.exportVendidosReport(des, has);
    }

    @GetMapping("reporte/comprados")
    public ResponseEntity<Resource> exportCompradosReport(@RequestParam LocalDate des, @RequestParam LocalDate has) throws JRException, FileNotFoundException {
        return reporteService.exportCompradosReport(des, has);
    }

    @GetMapping("reporte/pagos")
    public ResponseEntity<Resource> exportPagosReport(@RequestParam LocalDate des, @RequestParam LocalDate has) throws JRException, FileNotFoundException {
        return reporteService.exportPagosReport(des, has);
    }

    @GetMapping("reporte/cobros")
    public ResponseEntity<Resource> exportCobrosReport(@RequestParam LocalDate des, @RequestParam LocalDate has) throws JRException, FileNotFoundException {
        return reporteService.exportCobrosReport(des, has);
    }

    @GetMapping("reporte/facturas")
    public ResponseEntity<Resource> exportFacturasReport(@RequestParam LocalDate des, @RequestParam LocalDate has) throws JRException, FileNotFoundException {
        return reporteService.exportFacturasReport(des, has);
    }

    @GetMapping("reporte/compras")
    public ResponseEntity<Resource> exportComprasReport(@RequestParam LocalDate des, @RequestParam LocalDate has) throws JRException, FileNotFoundException {
        return reporteService.exportComprasReport(des, has);
    }

    @GetMapping("reporte/facturas-detallado")
    public ResponseEntity<Resource> exportFacturasDetalladoReport(@RequestParam LocalDate des, @RequestParam LocalDate has) throws JRException, FileNotFoundException {
        return reporteService.exportFacturasDetalladoReport(des, has);
    }

}
