package com.hfy.logstation.config;

import com.hfy.logstation.util.HealthAlgorithm;
import com.hfy.logstation.util.HealthAlgorithmV1;
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
