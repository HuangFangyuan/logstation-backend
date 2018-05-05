package com.hfy.logstation.util;

import com.hfy.logstation.entity.Response;
import com.hfy.logstation.exception.ResponseEnum;

public class ResponseUtil {

    public static <T> Response<T> success(T t) {
        return new Response<>(t);
    }

    public static <T> Response<T> success() {
        return new Response<>(null);
    }

    public static Response error(int code, String msg) {
        return new Response<>(code, msg, null);
    }

    public static Response error(ResponseEnum e) {
        return new Response<>(e, null);
    }
}
