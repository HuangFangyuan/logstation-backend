package com.hfy.elasticsearch.service;

import com.google.gson.Gson;
import com.hfy.elasticsearch.dto.AlarmDto;
import com.hfy.elasticsearch.dto.ResultDto;

import static org.elasticsearch.index.query.QueryBuilders.*;
import com.hfy.elasticsearch.model.Hit;
import com.hfy.elasticsearch.model.Source;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


/**
 * Created by HuangFangyuan on 2018/2/25.
 */
@Service
public class LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    private TransportClient client;

    @Autowired
    public LogService(TransportClient client) {
        this.client = client;
    }

    public ResultDto getLogs(String index, int from, int size) throws IOException {
        SearchResponse response = client.prepareSearch(index)
                .setFrom(from)
                .setSize(size)
                .get();
        SearchHit[] hits = response.getHits().getHits();
        ResultDto resultDto = new ResultDto();
        resultDto.setHits(parseHits(hits));
        resultDto.setTotal(response.getHits().getTotalHits());
        return resultDto;
    }

    public ResultDto getLogsByTime(String index, int from, int size, Date sTime, Date eTime) throws IOException {
        SearchResponse response = client.prepareSearch(index)
                .setFrom(from)
                .setSize(size)
                .setQuery(rangeQuery("time").from(sTime).to(eTime))
                .get();
        SearchHit[] hits = response.getHits().getHits();
        ResultDto resultDto = new ResultDto();
        resultDto.setHits(parseHits(hits));
        resultDto.setTotal(response.getHits().getTotalHits());
        return resultDto;
    }

    public AlarmDto getErrorOrBug(String index) {
        SearchResponse response = client.prepareSearch(index)
                .setQuery(matchQuery("level","ERROR"))
                .get();
        SearchResponse response2 = client.prepareSearch(index)
                .setQuery(matchQuery("level","BUG"))
                .get();
        AlarmDto alarmDto = new AlarmDto();
        alarmDto.setBugList(parseHits(response.getHits().getHits()));
        alarmDto.setErrorList(parseHits(response2.getHits().getHits()));
        return alarmDto;
    }

    private List<Hit> parseHits(SearchHit[] hits) {
        if (null == hits) {
            throw new NullPointerException();
        }
        List<String> keys = new ArrayList<>();
        List<Map<String ,String>> values = new ArrayList<>();
        List<Hit> result = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            SearchHit h = hits[i];
            Hit hit = new Hit();
            Source source = new Gson().fromJson(h.getSourceAsString(), Source.class);
            hit.setIndex(h.getIndex());
            hit.setType(h.getType());
            hit.setId(h.getId());
            hit.setVersion(h.getVersion());
            hit.setScore(h.getScore());
            hit.setSource(source);
            result.add(hit);
        }
        return result;
    }

}
