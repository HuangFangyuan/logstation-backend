package com.hfy.logstation.service.impl;


import static com.hfy.logstation.util.JsonUtil.toJson;
import static org.elasticsearch.index.query.QueryBuilders.*;

import com.hfy.logstation.util.HealthAlgorithm;
import com.hfy.logstation.dto.HealthDto;
import com.hfy.logstation.service.interfaces.HealthService;
import com.hfy.logstation.util.IntervalHandler;
import lombok.Setter;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
@Service
@Setter
public class HealthServiceImpl implements HealthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthServiceImpl.class);

    private TransportClient client;

    @Resource(name = "v1")
    private HealthAlgorithm healthAlgorithm;

    @Autowired
    public HealthServiceImpl(TransportClient client) {
        this.client = client;
    }

    @Override
    public String healthCondition(String index, int interval, String unit) {
        HealthDto healthDto = new HealthDto();
        LocalDateTime ldt = LocalDateTime.now();
        long eTime = ldt.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long sTime = IntervalHandler.handle(ldt, interval, unit).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOGGER.info("start time is : {}", format.format(new Date(sTime)));
        LOGGER.info("end time is : {}", format.format(new Date(eTime)));
        long info = count(index, "INFO", sTime, eTime);
        long debug = count(index, "DEBUG", sTime, eTime);
        long warn = count(index, "WARN", sTime, eTime);
        long error = count(index, "ERROR", sTime, eTime);
        healthDto.setInfo(info);
        healthDto.setDebug(debug);
        healthDto.setWarn(warn);
        healthDto.setError(error);
        healthDto.setScore(healthAlgorithm.score(info, debug, warn, error));
        return toJson(healthDto);
    }

    private long count(String index, String level, long sTime, long eTime) {
        SearchResponse response = client.prepareSearch(index)
                .setTypes("doc")
                .setQuery(boolQuery()
                        .must(matchQuery("level", level))
                        .must(rangeQuery("@timestamp").from(sTime).to(eTime)))
                .get();
        return response.getHits().getTotalHits();
    }
}
