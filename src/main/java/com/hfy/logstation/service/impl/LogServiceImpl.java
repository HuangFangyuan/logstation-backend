package com.hfy.logstation.service.impl;

import com.hfy.logstation.dto.ResultDto;

import static com.hfy.logstation.util.JsonUtil.toBean;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.*;
import com.hfy.logstation.entity.Hit;
import com.hfy.logstation.entity.Source;
import com.hfy.logstation.service.interfaces.LogService;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LogServiceImpl implements LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    private static final String DEFAULT_RANGE_FIELD = "eventTime";

    private static final String DEFAULT_SORT_FIELD = "eventTime";

    private static final SortOrder DEFAULT_SORT_ORDER = SortOrder.DESC;

    private TransportClient client;

    @Autowired
    public LogServiceImpl(TransportClient client) {
        this.client = client;
    }

    //默认结果按时间降序
    @Override
    public ResultDto getAll(String index, int from, int size) {
        return getAll(index, from, size, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);
    }

    @Override
    public ResultDto getAll(String index, int from, int size, String sortField, SortOrder order) {
        return query(index, null, null, from, size, sortField, order);
    }

    @Override
    public ResultDto getByCondition(String index, String field, Object fromValue ,Object toValue, String operator, int from, int size) {
        ResultDto resultDto = null;
        switch (operator) {
            case "is" :
                resultDto = match(index, field, fromValue, from, size);
                break;
            case "not" :
                resultDto = notMatch(index, field, fromValue, from, size);
                break;
            case "between" :
                resultDto = range(index, field, from, size, fromValue, toValue);
                break;
        }
        return resultDto;
    }

    @Override
    public ResultDto match(String index, String matchField, Object value, int from, int size) {
        return match(index, matchField, value, from, size, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);
    }

    @Override
    public ResultDto match(String index, String matchField, Object value, int from, int size, String sortField, SortOrder order) {
        QueryBuilder qb = matchQuery(matchField, value);
        return query(index, qb, null, from, size, sortField, order);
    }

    @Override
    public ResultDto notMatch(String index, String field, Object value, int from, int size) {
        return notMatch(index, field, value, from, size, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);
    }

    @Override
    public ResultDto notMatch(String index, String field, Object value, int from, int size, String sortField, SortOrder order) {
        QueryBuilder qb = boolQuery().mustNot(matchQuery(field, value));
        return query(index, qb, null, from, size, sortField, order);
    }

    @Override
    public ResultDto range(String index, String rangField, int from, int size, Object start, Object end) {
        return range(index, rangField, from, size, start, end, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);
    }

    @Override
    public ResultDto range(String index, String rangField, int from, int size, Object start, Object end, String sortField, SortOrder order) {
        LOGGER.info("{},{}",start,end);
        if (rangField.contains("eventTime")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            try {
                start = sdf.parse(((String)start).replace("Z", " UTC")).getTime();
                end  = sdf.parse(((String)end).replace("Z", " UTC")).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        QueryBuilder qb = rangeQuery(rangField).from(start).to(end);
        return query(index, qb, null, from, size, sortField, order);
    }

    @Override
    public ResultDto matchAndRange(String index, String matchField, Object value, String rangeField, Object start, Object end) {
        QueryBuilder qb = boolQuery()
                .must(matchQuery(matchField, value))
                .must(rangeQuery(rangeField).from(start).to(end));
        return query(index, qb, null, 0, Integer.MAX_VALUE, null, null);
    }

    @Override
    public ResultDto matchAndRangeAndCount(String index, String matchField, Object value, String rangeField, Object start, Object end,String countField) {
        QueryBuilder qb = boolQuery()
                .must(matchQuery(matchField, value))
                .must(rangeQuery(rangeField).from(start).to(end));
        AggregationBuilder ab = count(countField);
        return query(index, qb, ab, 0, Integer.MAX_VALUE, null, null);
    }

    @Override
    public ResultDto matchAndRangeAndAvg(String index, String matchField, Object value, String rangeField, Object start, Object end,String avgField) {
        QueryBuilder qb = boolQuery()
                .must(matchQuery(matchField, value))
                .must(rangeQuery(rangeField).from(start).to(end));
        AggregationBuilder ab = avg(avgField);
        return query(index, qb, ab, 0, Integer.MAX_VALUE, null, null);
    }

    private ResultDto query(String index, QueryBuilder qb, AggregationBuilder ab, int from, int size, String sortField, SortOrder order) {
        LOGGER.info("开始查询日志");
        SearchRequestBuilder srb = client.prepareSearch(index).setFrom(from).setSize(size);
        if (qb != null) {
            srb.setQuery(qb);
        }
        if (ab != null) {
            srb.addAggregation(ab);
        }
        if (sortField != null && order != null) {
            srb.addSort(sortField, order);
        }
        SearchResponse response = srb.get();
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
