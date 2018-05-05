package com.hfy.logstation.service.impl;

import com.hfy.logstation.dto.ModuleDto;
import com.hfy.logstation.dto.TaskDto;
import com.hfy.logstation.service.interfaces.GraphService;
import com.hfy.logstation.constant.Fields;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.hfy.logstation.constant.Constants.DEFAULT_RANGE_FIELD;
import static com.hfy.logstation.util.EsUtil.query;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Service
public class GraphServiceImpl implements GraphService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphServiceImpl.class);

    @Override
    public ModuleDto taskCount(String index, String system, String module, String task, long start, long end) {
        BoolQueryBuilder bqb = boolQuery()
                .must(rangeQuery(DEFAULT_RANGE_FIELD).from(start).to(end))
                .must(matchQuery(Fields.SYSTEM, system))
                .must(matchQuery(Fields.MODULE, module));
        if (task != null) {
            bqb.must(matchQuery(Fields.TASK, task));
        }
        final String NAME = "buckets";
        AggregationBuilder ab = terms(NAME).field(Fields.TASK);
        SearchResponse response = query(index, bqb, ab);
        List<String> tasks = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        ((Terms)response.getAggregations().get(NAME))
                .getBuckets()
                .forEach(bucket -> {
                    tasks.add(bucket.getKeyAsString());
                    counts.add(bucket.getDocCount());
                });
        ModuleDto<String, Long> moduleDto = new ModuleDto<>();
        moduleDto.setX(counts);
        moduleDto.setY(tasks);
        return moduleDto;
    }

    @Override
    public ModuleDto taskCostTime(String index, String system, String module, String task, long start, long end) {
        BoolQueryBuilder bqb = boolQuery()
                .must(rangeQuery(DEFAULT_RANGE_FIELD).from(start).to(end))
                .must(matchQuery(Fields.SYSTEM, system))
                .must(matchQuery(Fields.MODULE, module));
        if (task != null) {
            bqb.must(matchQuery(Fields.TASK, task));
        }
        final String NAME = "buckets";
        final String AVERAGE = "averageCostTime";
        AggregationBuilder ab = terms(NAME).field(Fields.TASK).subAggregation(avg(AVERAGE).field(Fields.COST_TIME));
        SearchResponse response = query(index, bqb, ab);
        List<String> tasks = new ArrayList<>();
        List<Double> costs = new ArrayList<>();
        ((Terms)response.getAggregations().get(NAME))
                .getBuckets()
                .forEach(bucket -> {
                    tasks.add(bucket.getKeyAsString());
                    costs.add(((Avg)bucket.getAggregations().get(AVERAGE)).getValue());
                });
        ModuleDto<String, Double> moduleDto = new ModuleDto<>();
        moduleDto.setX(costs);
        moduleDto.setY(tasks);
        return moduleDto;
    }

    @Override
    public ModuleDto taskSuccessRate(String index, String system, String module, String task, long start, long end) {
        BoolQueryBuilder bqb = boolQuery()
                .must(rangeQuery(DEFAULT_RANGE_FIELD).from(start).to(end))
                .must(matchQuery(Fields.SYSTEM, system))
                .must(matchQuery(Fields.MODULE, module));
        if (task != null) {
            bqb.must(matchQuery(Fields.TASK, task));
        }
        final String NAME = "buckets";
        final String SUCCESS = "successCount";
        AggregationBuilder ab = terms(NAME).field(Fields.TASK).subAggregation(terms(SUCCESS).field(Fields.RESULT));
        SearchResponse response = query(index, bqb, ab);
        List<String> tasks = new ArrayList<>();
        List<Long> rates = new ArrayList<>();
        ((Terms)response.getAggregations().get(NAME))
                .getBuckets()
                .forEach(bucket -> {
                    tasks.add((String)bucket.getKey());
                    ((Terms)bucket.getAggregations().get(SUCCESS))
                            .getBuckets()
                            .stream()
                            .filter(bucket2 -> "success".equals(bucket2.getKey()))
                            .forEach(bucket2 -> rates.add(bucket2.getDocCount() * 100 / bucket.getDocCount()));
                });
        ModuleDto<String, Long> moduleDto = new ModuleDto<>();
        moduleDto.setX(rates);
        moduleDto.setY(tasks);
        return moduleDto;
    }

    @Override
    public TaskDto task(String index, String system, String module, String task, long start, long end) {
        TaskDto taskDto = new TaskDto();
        QueryBuilder qb = boolQuery()
                .must(rangeQuery(Fields.EVENT_TIME).from(start).to(end))
                .must(matchQuery(Fields.SYSTEM, system))
                .must(matchQuery(Fields.MODULE, module))
                .must(matchQuery(Fields.TASK, task));
        final String STATS = "stats";
        AggregationBuilder ab = stats(STATS).field(Fields.COST_TIME);
        SearchResponse response = query(index , qb , ab);
        Stats stats = response.getAggregations().get(STATS);
        taskDto.setCount(stats.getCount());
        taskDto.setCostTime(stats.getAvg());
        taskDto.setMax(stats.getMax());
        taskDto.setMin(stats.getMin());

        final String SUCCESS = "successCount";
        AggregationBuilder ab2 = terms(SUCCESS).field(Fields.RESULT);
        SearchResponse response2 = query(index , qb , ab2);
        Terms successCount = response2.getAggregations().get(SUCCESS);
        long sum = 0;
        long success = 0;
        for (Terms.Bucket bucket: successCount.getBuckets()) {
            if (bucket.getKey().equals("success")) {
                success = bucket.getDocCount();
            }
            sum += bucket.getDocCount();
        }
        taskDto.setSuccessRate(success * 100 / sum);

        final String HIS = "dataHistogram";
        AggregationBuilder ab3 = dateHistogram(HIS).field(Fields.EVENT_TIME).interval(1000 * 3600 * 24).format("yyyy-MM-dd");
        SearchResponse response3 = query(index, qb, ab3);
        List<String> dates = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        ((Terms)response3.getAggregations().get(HIS))
                .getBuckets()
                .forEach(bucket -> {
                    dates.add(bucket.getKeyAsString());
                    counts.add(bucket.getDocCount());
                });
        taskDto.setDates(dates);
        taskDto.setCounts(counts);

        final String RESULT = "result";
        AggregationBuilder ab4 = terms(RESULT).field(Fields.RESULT);
        SearchResponse response4 = query(index, qb, ab4);
        List<String> results = new ArrayList<>();
        List<Long> resultCounts = new ArrayList<>();
        ((Terms)response4.getAggregations().get(RESULT))
                .getBuckets()
                .forEach(bucket -> {
                    results.add(bucket.getKeyAsString());
                    resultCounts.add(bucket.getDocCount());
                });
        taskDto.setResults(results);
        taskDto.setResultCounts(resultCounts);

        return taskDto;
    }
}
