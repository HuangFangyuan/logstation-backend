package com.hfy.elasticsearch.api;

import com.hfy.elasticsearch.configuration.Cors;
import com.hfy.elasticsearch.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * Created by HuangFangyuan on 2018/2/25.
 */
@RestController
public class LogController {

    private LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    //根据index查询文档
    @GetMapping("/logs")
    public ResponseEntity get(@RequestParam("index") String index,
                              @RequestParam("from") int from,
                              @RequestParam("size") int size) throws IOException {
        return new ResponseEntity(logService.getLogs(index, from, size), HttpStatus.OK);

    }
}
