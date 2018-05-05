package com.hfy.logstation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss:SSS";
    private static final SimpleDateFormat FORMATTER= new SimpleDateFormat(DATE_FORMAT);

    public static String format(long time) {
        return FORMATTER.format(time);
    }

    public static String format(Date time) {
        return FORMATTER.format(time);
    }

    public static Date parse(String date) throws ParseException {
        return FORMATTER.parse(date);
    }
}
