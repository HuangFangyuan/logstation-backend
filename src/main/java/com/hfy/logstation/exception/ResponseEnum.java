package com.hfy.logstation.exception;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    NULL_PARAMS(1,"null param"),
    ILLEGAL_PARAMS(2,"illegal param"),
    MISS_TOKEN(3, "miss token"),
    WRONG_PASS(4, "password is wrong"),
    EXPIRE_TOKEN(5, "token is expire"),
    INVALID_TOKEN(6, "token is invalid"),
    NO_SUCH_INDEX(7, "no such index"),
    success(200,"success"),
    ;
    int code;
    String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
