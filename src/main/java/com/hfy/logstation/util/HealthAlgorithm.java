package com.hfy.logstation.util;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
public interface HealthAlgorithm {

    int score(long info, long debug, long warn, long error);
}
