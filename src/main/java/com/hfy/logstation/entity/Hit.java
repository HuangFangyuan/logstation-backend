package com.hfy.logstation.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

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
