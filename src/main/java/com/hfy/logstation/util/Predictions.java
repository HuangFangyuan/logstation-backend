package com.hfy.logstation.util;

public class Predictions {

    public static void checkNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
    }

    public static void checkNotNull(Object object, String msg) {
        if (object == null) {
            throw new NullPointerException(msg);
        }
    }
}
