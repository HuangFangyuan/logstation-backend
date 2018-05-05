package com.hfy.logstation.dto;

import com.hfy.logstation.entity.Hit;
import lombok.Data;

import java.util.List;


@Data
public class LogDto {
    long total;
    List<Hit> hits;
    long today;
}
