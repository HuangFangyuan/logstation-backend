package com.hfy;

import com.hfy.elasticsearch.utils.HttpUtil;
import org.junit.Test;

/**
 * Created by HuangFangyuan on 2018/2/25.
 */
public class HttpUtilTest {

    @Test
    public void testGet() {
        System.out.println(HttpUtil.simpleGet("http://localhost:9200/applog/doc/_search"));
    }

}
