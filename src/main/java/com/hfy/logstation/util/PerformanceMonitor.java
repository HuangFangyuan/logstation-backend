package com.hfy.logstation.util;

public class PerformanceMonitor {
    private static ThreadLocal<MethodPerformance> performanceRecord = new ThreadLocal<>();

    public static void begin(String method) {
        MethodPerformance mp = new MethodPerformance(method);
        performanceRecord.set(mp);
    }

    public static void end() {
        MethodPerformance mp = performanceRecord.get();
        mp.printPerformance();
    }
}
