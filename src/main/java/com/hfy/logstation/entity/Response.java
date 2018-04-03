package com.hfy.logstation.entity;

import com.hfy.logstation.exception.ResponseEnum;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private T data;

    public Response(ResponseEnum e, T data) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.data = data;
    }

    public Response(T data) {
        this.code = ResponseEnum.success.getCode();
        this.message = ResponseEnum.success.getMessage();
        this.data = data;
    }
}
