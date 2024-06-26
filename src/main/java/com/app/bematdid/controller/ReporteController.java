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
}
