package com.hfy.logstation.util;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.hfy.logstation.constant.Constants.DEFAULT_FROM;
import static com.hfy.logstation.constant.Constants.DEFAULT_SIZE;

public class EsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsUtil.class);

    private static TransportClient client = ApplicationContextReference.getApplicationContext().getBean(TransportClient.class);

    public static SearchResponse query(String index, QueryBuilder qb, AggregationBuilder ab) {
        return query(index, qb, ab, DEFAULT_FROM, DEFAULT_SIZE, null, null);
    }

    public static SearchResponse query(String index, QueryBuilder qb, AggregationBuilder ab, int from, int size) {
        return query(index, qb, ab, from, size, null, null);
    }

    public static SearchResponse query(String index, QueryBuilder qb, AggregationBuilder ab, int from, int size, String sortField, SortOrder order) {
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
        LOGGER.info("结束查询日志,共{}条数据", response.getHits().getTotalHits());
        return response;
    }
}
