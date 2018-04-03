package com.hfy.logstation.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by HuangFangyuan on 2018/2/25.
 */
@Data
public class Hit {

    @SerializedName("_index")
    String index;

    @SerializedName("_type")
    String type;

    @SerializedName("_id")
    String id;

    @SerializedName("_version")
    long version;

    @SerializedName("_score")
    Float score;

    @SerializedName("_source")
    Source source;

}
