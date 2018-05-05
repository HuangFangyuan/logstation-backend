package com.hfy.logstation.controller;

import com.hfy.logstation.service.interfaces.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {

    private HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping()
    public ResponseEntity healthCondition(@RequestParam("index") String index,
                                          @RequestParam("time") int interval,
                                          @RequestParam("unit") String unit) {

        return new ResponseEntity<>(healthService.healthCondition(index, interval, unit), HttpStatus.OK);
    }
}
