package com.hfy.logstation.handler;

import com.hfy.logstation.entity.Response;
import com.hfy.logstation.exception.ResponseEnum;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

     @ExceptionHandler(Exception.class)
     public Response handleException(Exception e) {
         if (e instanceof ServerException) {
             ServerException se = (ServerException) e;
             LOGGER.error("server exception {}", se);
             return ResponseUtil.error(se.getCode(), se.getMessage());
         }
         else if (e instanceof MissingServletRequestParameterException) {
             LOGGER.error("miss params exception {}", e);
             return ResponseUtil.error(ResponseEnum.NULL_PARAMS);
         }
         else {
             LOGGER.error("unknown exception {}", e);
             return ResponseUtil.error(-1, "Unknown exception");
         }

     }
}
