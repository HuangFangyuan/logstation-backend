package com.hfy.elasticsearch.service.interfaces;


/**
 * Created by HuangFangyuan on 2018/3/13.
 */
public interface HealthService {

    String healthCondition(String index, int interval, String unit);
}
