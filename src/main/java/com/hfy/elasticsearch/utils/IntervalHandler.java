package com.hfy.elasticsearch.utils;

import java.time.LocalDateTime;

/**
 * Created by HuangFangyuan on 2018/3/14.
 */
public class IntervalHandler {

    public static LocalDateTime handle(LocalDateTime now, int interval, String unit) {
        LocalDateTime result;
        switch (unit) {
            case "hour":
                result = now.minusHours(interval);
                break;
            case "day":
                result = now.minusDays(interval);
                break;
            case "week":
                result = now.minusWeeks(interval);
                break;
            case "month":
                result = now.minusMonths(interval);
                break;
            case "all":
                result = now.minusYears(60);
                break;
            default:
                throw new RuntimeException("unsupported unit");
        }
        return result;
    }

}
