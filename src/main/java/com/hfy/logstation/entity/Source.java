package com.hfy.logstation.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Source {

    //用到的部分
    String source;

    int costTime;

    String module;

    String task;

    String system;

    String host;

    String result;

    String information;

    @SerializedName("@timestamp")
    Date timestamp;

    @SerializedName("eventTime")
    Date eventTime;

    String level;

    @SerializedName("class")
    String className;

    String error;

}
