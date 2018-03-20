package com.hfy.elasticsearch.utils;


/**
 * Created by HuangFangyuan on 2018/3/13.
 */
public class HealthAlgorithmV1 implements HealthAlgorithm {

    @Override
    public int score(long info, long debug, long warn, long error) {
        return 100;
    }
}
