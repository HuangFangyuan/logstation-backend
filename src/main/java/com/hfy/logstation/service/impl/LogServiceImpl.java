package com.hfy.logstation.service.impl;

import com.hfy.logstation.dto.LogDto;

import static com.hfy.logstation.constant.Constants.*;
import static com.hfy.logstation.util.EsUtil.query;
import static com.hfy.logstation.util.JsonUtil.toBean;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.*;
import com.hfy.logstation.entity.Hit;
import com.hfy.logstation.entity.QueryBean;
import com.hfy.logstation.entity.Source;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.service.interfaces.LogService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class LogServiceImpl implements LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    private static final String IS = "is";
    private static final String NOT = "not";
    private static final String BETWEEN = "between";

    @Override
    public LogDto getAll(String index, int from, int size) {
        return getAll(index, from, size, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);
    }

    @Override
    public LogDto getAll(String index, int from, int size, String sortField, SortOrder order) {
        LogDto logDto = packToDto(query(index, null, null, from, size, sortField, order));
        long current = System.currentTimeMillis();
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime zero = LocalDateTime.of(ldt.getYear(),ldt.getMonthValue(),ldt.getDayOfMonth(),0,0);
        long today = range(index, DEFAULT_RANGE_FIELD ,from, size, zero.getNano(), current).getTotal();
        logDto.setTotal(today);
        return logDto;
    }

    @Override
    public LogDto getByCondition(String index, List<QueryBean> queryList, int from, int size) {
        BoolQueryBuilder bqb = boolQuery();
        queryList.forEach(query -> {
            String field = query.getFiled();
            Object fromValue = query.getFromValue();
            Object toValue = query.getToValue();
            switch (query.getOperator()) {
                case IS:
                    bqb.must(matchQuery(field, fromValue));
                    break;
                case NOT:
                    bqb.mustNot(matchQuery(field, fromValue));
                    break;
                case BETWEEN:
                    bqb.must(rangeQuery(field).from(fromValue).to(toValue));
                    break;
                default:
                    throw new ServerException("not support this operate");
            }
        });
        return packToDto(query(index, bqb, null, from, size, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER));
    }

    @Override
    public LogDto match(String index, String matchField, Object value, int from, int size) {
        return match(index, matchField, value, from, size, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);
    }

    @Override
    public LogDto match(String index, String matchField, Object value, int from, int size, String sortField, SortOrder order) {
        QueryBuilder qb = matchQuery(matchField, value);
        return packToDto(query(index, qb, null, from, size, sortField, order));
    }

    @Override
    public LogDto notMatch(String index, String field, Object value, int from, int size) {
        return notMatch(index, field, value, from, size, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);
    }

    @Override
    public LogDto notMatch(String index, String field, Object value, int from, int size, String sortField, SortOrder order) {
        QueryBuilder qb = boolQuery().mustNot(matchQuery(field, value));
        return packToDto(query(index, qb, null, from, size, sortField, order));
    }

    @Override
    public LogDto range(String index, String rangField, int from, int size, Object start, Object end) {
        return range(index, rangField, from, size, start, end, DEFAULT_SORT_FIELD, DEFAULT_SORT_ORDER);
    }

    @Override
    public LogDto range(String index, String rangField, int from, int size, Object start, Object end, String sortField, SortOrder order) {
        LOGGER.info("{}, {}",start,end);
        QueryBuilder qb = rangeQuery(rangField).from(start).to(end);
        return packToDto(query(index, qb, null, from, size, sortField, order));
    }

    @Override
    public LogDto matchAndRange(String index, String matchField, Object value, String rangeField, Object start, Object end) {
        QueryBuilder qb = boolQuery()
                .must(matchQuery(matchField, value))
                .must(rangeQuery(rangeField).from(start).to(end));
        return packToDto(query(index, qb, null));
    }

    @Override
    public long matchAndRangeAndCount(String index, String matchField, Object value, String rangeField, Object start, Object end,String countField) {
        QueryBuilder qb = boolQuery()
                .must(matchQuery(matchField, value))
                .must(rangeQuery(rangeField).from(start).to(end));
        AggregationBuilder ab = count(countField);
        return query(index, qb, ab).getHits().getTotalHits();
    }

    @Override
    public Double matchAndRangeAndAvg(String index, String matchField, Object value, String rangeField, Object start, Object end, String avgField) {
        QueryBuilder qb = boolQuery()
                .must(matchQuery(matchField, value))
                .must(rangeQuery(rangeField).from(start).to(end));
        final String AVG = "avg";
        AggregationBuilder ab = avg(AVG).field(avgField);
        SearchResponse response = query(index, qb, ab);
        Avg avg = response.getAggregations().get(AVG);
        return avg.getValue();
    }

    private LogDto packToDto(SearchResponse response) {
        SearchHit[] hits = response.getHits().getHits();
        LogDto logDto = new LogDto();
        logDto.setHits(parseHits(hits));
        logDto.setTotal(response.getHits().getTotalHits());
        return logDto;
    }

    private List<Hit> parseHits(SearchHit[] hits) {
        List<Hit> result = new ArrayList<>();
        Arrays.stream(hits).forEach( h -> {
            Hit hit = new Hit();
            Source source = toBean(h.getSourceAsString(), Source.class);
            hit.setIndex(h.getIndex());
            hit.setType(h.getType());
            hit.setId(h.getId());
            hit.setVersion(h.getVersion());
            hit.setScore(h.getScore());
            hit.setSource(source);
            result.add(hit);
        });
        return result;
    }
}