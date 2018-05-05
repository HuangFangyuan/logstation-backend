package com.hfy.logstation.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModuleDto<T, E> {
    List<T> y;
    List<E> x;
}
