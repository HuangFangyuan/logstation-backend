package com.hfy.logstation.dto;

import lombok.Data;

@Data
public class HealthDto {
    long info;
    long warn;
    long error;
    int score;
}
