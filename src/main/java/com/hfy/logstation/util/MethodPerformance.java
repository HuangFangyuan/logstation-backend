package com.hfy.logstation.util;

/**
 * Created by HuangFangyuan on 2018/3/21.
 */
public class MethodPerformance {
    private long begain;
    private long end;
    private String serviceMethod;

    public MethodPerformance(String serviceMethod) {
        this.serviceMethod = serviceMethod;
        begain = System.currentTimeMillis();
    }

    public void printPerformance() {
        end = System.currentTimeMillis();
        long elapse = end - begain;
        System.out.println(serviceMethod + " spend " + elapse +" millisecond");
    }
}
