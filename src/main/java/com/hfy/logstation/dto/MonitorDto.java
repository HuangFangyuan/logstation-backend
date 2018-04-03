package com.hfy.logstation.dto;

import com.hfy.logstation.entity.Monitor;
import lombok.Data;


/**
 * Created by HuangFangyuan on 2018/3/16.
 */
@Data
public class MonitorDto {

    Monitor monitor;

    String operate = "修改";

    boolean editable = false;

}
