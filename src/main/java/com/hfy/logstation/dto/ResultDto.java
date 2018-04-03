package com.hfy.logstation.dto;

import com.hfy.logstation.entity.Hit;
import lombok.Data;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/2/28.
 */
@Data
public class ResultDto {
    long total;
    List<Hit> hits;

    public void addHit(Hit hit) {
        hits.add(hit);
    }

}
