package com.hfy.elasticsearch.exception;

import lombok.Data;

/**
 * Created by HuangFangyuan on 2018/2/26.
 */
@Data
public class ServerException extends RuntimeException {

    private int code;
    private String message;

    public ServerException(ExceptionEnum e) {
        this.code = e.code;
        this.message = e.message;
    }
}
