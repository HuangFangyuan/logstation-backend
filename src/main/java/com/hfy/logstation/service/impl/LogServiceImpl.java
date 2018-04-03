package com.hfy.logstation.service.impl;

import com.hfy.logstation.dto.ResultDto;

import static com.hfy.logstation.util.JsonUtil.toBean;
import static org.elasticsearch.index.query.QueryBuilders.*;
import com.hfy.logstation.entity.Hit;
import com.hfy.logstation.entity.Source;
import com.hfy.logstation.service.interfaces.LogService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogServiceImpl implements LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    private TransportClient client;

    @Autowired
    public LogServiceImpl(TransportClient client) {
        this.client = client;
    }

    @Override
    public ResultDto get(String index, int from, int size) {
        return get(index, from, size,"@timestamp", SortOrder.DESC);
    }

    @Override
    public ResultDto get(String index, String field, String value, int from, int size) {
        return get(index, field, value, from, size,"@timestamp", SortOrder.DESC);
    }

    @Override
    public List<Hit> get(String index, String field, String value, Object start, Object to) {
        return get(index, field, value, "@timestamp", start, to);
    }

    @Override
    public ResultDto get(String index, int from, int size, String sortField, SortOrder order) {
        LOGGER.info("开始查询日志");
        SearchResponse response = client.prepareSearch(index)
                .setFrom(from)
                .setSize(size)
                .addSort(sortField, order)
                .get();
        SearchHit[] hits = response.getHits().getHits();
        ResultDto resultDto = new ResultDto();
        resultDto.setHits(parseHits(hits));
        resultDto.setTotal(response.getHits().getTotalHits());
        LOGGER.info("结束查询日志,共{}条数据", response.getHits().getTotalHits());
        return resultDto;
    }
    @Override
    public List<Hit> get(String index, String field, String value, String rangeField, Object start, Object end) {
        LOGGER.info("开始查询日志");
        SearchResponse response = client.prepareSearch(index)
                .setQuery(boolQuery()
                        .must(matchQuery(field, value))
                        .must(rangeQuery(rangeField).from(start).to(end)
                        )
                )
                .get();
        List<Hit> result = parseHits(response.getHits().getHits());
        LOGGER.info("结束查询日志,共{}条数据", response.getHits().getTotalHits());
        return result;
    }

    @Override
    public ResultDto get(String index, String field, String value, int from, int size, String sortField, SortOrder order) {
        LOGGER.info("开始查询日志");
        SearchResponse response = client.prepareSearch(index)
                .setQuery(matchQuery(field, value))
                .addSort(sortField, order)
                .setFrom(from)
                .setSize(size)
                .get();
        SearchHit[] hits = response.getHits().getHits();
        ResultDto resultDto = new ResultDto();
        resultDto.setHits(parseHits(hits));
        resultDto.setTotal(response.getHits().getTotalHits());
        LOGGER.info("结束查询日志,共{}条数据", response.getHits().getTotalHits());
        return resultDto;
    }

    private List<Hit> parseHits(SearchHit[] hits) {
        if (null == hits) {
            throw new NullPointerException();
        }
        List<Hit> result = new ArrayList<>();
        for (SearchHit h : hits) {
            Hit hit = new Hit();
            Source source = toBean(h.getSourceAsString(), Source.class);
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
