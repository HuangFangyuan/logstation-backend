package com.hfy.elasticsearch.service.impl;

import com.hfy.elasticsearch.service.interfaces.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by HuangFangyuan on 2018/3/14.
 */
@Service
public class RedisServiceImpl implements RedisService {

    private StringRedisTemplate template;

    @Autowired
    public RedisServiceImpl(StringRedisTemplate template) {
        this.template = template;
    }

    public void addMonitor(String key, String value) {


    }
}
