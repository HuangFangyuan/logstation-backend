package com.hfy.logstation.dto;

import com.hfy.logstation.entity.Hit;
import lombok.Data;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/6.
 */
@Data
public class AlarmDto {
    List<Hit> errorList;
    List<Hit> bugList;

    public void addError(Hit hit) {
        errorList.add(hit);
    }

    public void addBug(Hit hit) {
        bugList.add(hit);
    }
}
