package com.hfy.elasticsearch.exception;

/**
 * Created by HuangFangyuan on 2018/2/26.
 */
public enum ExceptionEnum {
    NULL_PARAMS(001,"Params is null")
    ;
    int code;
    String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
