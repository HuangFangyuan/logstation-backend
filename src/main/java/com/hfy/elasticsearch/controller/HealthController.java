package com.hfy.elasticsearch.controller;

import com.hfy.elasticsearch.service.interfaces.HealthService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    private HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity healthCondition(@RequestParam("index") String index,
                                          @RequestParam("time") int interval,
                                          @RequestParam("unit") String unit) {

        return new ResponseEntity<>(healthService.healthCondition(index, interval, unit), HttpStatus.OK);
    }
}
