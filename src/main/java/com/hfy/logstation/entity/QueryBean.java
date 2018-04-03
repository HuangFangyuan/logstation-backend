package com.hfy.logstation.entity;

import lombok.Data;
import org.elasticsearch.search.sort.SortOrder;

import javax.validation.constraints.NotNull;

@Data
public class QueryBean {
    private String index;
    private String matchField;
    private String value;
    private String rangeField;
    private Object start;
    private Object end;
    private SortOrder order;
}
