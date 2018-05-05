package com.hfy.logstation.service.impl;


import static com.hfy.logstation.constant.Constants.DEFAULT_FROM;
import static com.hfy.logstation.constant.Constants.DEFAULT_SIZE;
import static com.hfy.logstation.util.EsUtil.query;
import static com.hfy.logstation.util.JsonUtil.toJson;
import static org.elasticsearch.index.query.QueryBuilders.*;

import com.hfy.logstation.util.DateUtil;
import com.hfy.logstation.util.HealthAlgorithm;
import com.hfy.logstation.dto.HealthDto;
import com.hfy.logstation.service.interfaces.HealthService;
import com.hfy.logstation.util.IntervalHandler;
import lombok.Setter;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class HealthServiceImpl implements HealthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthServiceImpl.class);

    private HealthAlgorithm healthAlgorithm = (info, warn, error) -> 100;

    @Override
    public String healthCondition(String index, int interval, String unit) {
        HealthDto healthDto = new HealthDto();
        LocalDateTime ldt = LocalDateTime.now();
        long eTime = ldt.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long sTime = IntervalHandler.handle(ldt, interval, unit).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        LOGGER.info("start time is : {}", DateUtil.format(new Date(sTime)));
        LOGGER.info("end time is : {}", DateUtil.format(new Date(eTime)));
        long info = count(index, "INFO", sTime, eTime);
        long warn = count(index, "WARN", sTime, eTime);
        long error = count(index, "ERROR", sTime, eTime);
        healthDto.setInfo(info);
        healthDto.setWarn(warn);
        healthDto.setError(error);
        healthDto.setScore(healthAlgorithm.score(info, warn, error));
        return toJson(healthDto);
    }

    private long count(String index, String level, long sTime, long eTime) {
        QueryBuilder qb = boolQuery().must(matchQuery("level", level))
                .must(rangeQuery("eventTime").from(sTime).to(eTime));
        SearchResponse response = query(index, qb, null, DEFAULT_FROM, DEFAULT_SIZE, null, null);
        return response.getHits().getTotalHits();
    }
}
