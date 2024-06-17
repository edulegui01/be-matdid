package com.app.bematdid.controller;

import com.app.bematdid.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    public HomeService homeService;

    @GetMapping("home/estadisticas")
    public Map<String, Integer> estadisticas() {
        return homeService.estadisticas();
    }

    @GetMapping("home/cantidad-anho/{anho}")
    public Map<String, Integer> cantidadPorAnho(@PathVariable String anho) {
        System.out.println(anho);
        return homeService.cantidadPorAnho(anho);
    }

    @GetMapping("home/cantidad-an/{anho}")
    public List<Integer> cantidadPorMes(@PathVariable Integer anho) {
        return homeService.cantidadPorMes(anho);
    }

}
