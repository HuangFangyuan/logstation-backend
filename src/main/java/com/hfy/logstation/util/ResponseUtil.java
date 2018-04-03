package com.hfy.logstation.util;

import com.hfy.logstation.entity.Response;
import com.hfy.logstation.exception.ResponseEnum;

/**
 * Created by HuangFangyuan on 2018/4/1.
 */
public class ResponseUtil {

    public static Response<Object> success(Object object) {
        return new Response<>(ResponseEnum.success, object);
    }

    public static Response<Object> success() {
        return new Response<>(ResponseEnum.success, null);
    }

    public static Response error(int code, String msg) {
        return new Response<>(code, msg, null);
    }

    public static Response error(ResponseEnum e) {
        return new Response<>(e, null);
    }
}
