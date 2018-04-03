package com.hfy.logstation.dto;

import lombok.Data;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
@Data
public class HealthDto {
    long info;
    long debug;
    long warn;
    long error;
    int score;
}
