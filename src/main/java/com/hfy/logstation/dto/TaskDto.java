package com.hfy.logstation.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskDto {
    long count;
    double costTime;
    double max;
    double min;
    double successRate;

    List<String> dates;
    List<Long> counts;

    List<String> results;
    List<Long> resultCounts;
}
