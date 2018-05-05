package com.hfy.logstation.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerException extends RuntimeException {

    private int code;

    public ServerException(String message) {
        super(message);
        code = -1;
    }

    public ServerException(ResponseEnum e) {
        super(e.getMessage());
        this.code = e.code;
    }

}
