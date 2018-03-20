package com.hfy.elasticsearch.config;

import com.hfy.elasticsearch.utils.HealthAlgorithm;
import com.hfy.elasticsearch.utils.HealthAlgorithmV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
@Configuration
public class ServerConfig {

    @Bean(name = "v1")
    public HealthAlgorithm healthAlgorithm() {
        return new HealthAlgorithmV1();
    }
}
