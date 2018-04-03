package com.hfy.logstation.exception;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    NULL_PARAMS(1,"null param"),
    ILLEGAL_PARAMS(2,"illegal param"),
    success(200,"success"),
    ;
    int code;
    String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
