package com.hfy.logstation.controller;

import com.hfy.logstation.entity.Response;
import com.hfy.logstation.exception.ResponseEnum;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.service.interfaces.LogService;
import com.hfy.logstation.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/logs")
public class LogController {

    private LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 根据index获取相应的日志
     *
     * @param index
     * @param from
     * @param size
     * @return Response
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response<Object> get(@RequestParam("index") String index,
                              @RequestParam("from") int from,
                              @RequestParam("size") int size) throws IOException {
        if (from < 0) {
            throw new ServerException(ResponseEnum.ILLEGAL_PARAMS);
        }
        if (size <= 0) {
            throw new ServerException(ResponseEnum.ILLEGAL_PARAMS);
        }

        return ResponseUtil.success(logService.get(index, from, size));

    }

    @RequestMapping(value = "/condition", method = RequestMethod.GET)
    public Response<Object> get(@RequestParam("index") String index,
                              @RequestParam("field") String field,
                              @RequestParam("value") String value,
                              @RequestParam("from") int from,
                              @RequestParam("size") int size) throws IOException {
        if (from < 0) {
            throw new ServerException(ResponseEnum.ILLEGAL_PARAMS);
        }
        if (size <= 0) {
            throw new ServerException(ResponseEnum.ILLEGAL_PARAMS);
        }
        return ResponseUtil.success(logService.get(index, field, value, from, size));

    }
}
