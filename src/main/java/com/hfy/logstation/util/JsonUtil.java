package com.hfy.logstation.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonUtil {

    private static Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T toList(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
