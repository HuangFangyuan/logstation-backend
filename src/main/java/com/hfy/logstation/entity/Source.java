package com.hfy.logstation.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by HuangFangyuan on 2018/2/23.
 */
@Data
public class Source {

    int offset;

    Prospector prospector;

    String source;

    Beat beat;

    String message;

    String host;

    @SerializedName("@version")
    String version;

    List<String> tags;

    @SerializedName("@timestamp")
    Date timestamp;

    @SerializedName("timestamp")
    Date time;

    String level;

    String text;

    @SerializedName("class")
    String className;

    @Data
    public class Prospector {
        String type;
    }

    @Data
    public class Beat {
        String hostname;
        String version;
        String name;
    }

}
