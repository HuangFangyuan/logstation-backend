package com.hfy.logstation.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class QueryBean {

    @SerializedName("searchField")
    private String filed;
    private Object fromValue;
    private Object toValue;
    private String operator;
}
