package com.app.bematdid.controller;

import com.app.bematdid.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    public HomeService homeService;

    @GetMapping("home/estadisticas")
    public Map<String, Integer> estadisticas() {
        return homeService.estadisticas();
    }

}
