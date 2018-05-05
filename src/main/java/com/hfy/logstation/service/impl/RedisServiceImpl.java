package com.hfy.logstation.service.impl;

import com.hfy.logstation.service.interfaces.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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
