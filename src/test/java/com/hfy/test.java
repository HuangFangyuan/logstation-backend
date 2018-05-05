package com.hfy;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.UTCProvider;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.*;

/**
 * Created by HuangFangyuan on 2018/3/13.
 */
public class test {

    @Test
    public void test1() {
        System.out.println(new Date().getTime());
    }

    @Test
    public void test2() {
        Calendar date = Calendar.getInstance();
        System.out.println(date.getTime());
        date.add(Calendar.DATE, -2);
        System.out.println(date.getTime());
    }

    @Test
    public void test3() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time1 = LocalDateTime.now().toInstant(ZoneOffset.ofHours(0)).toEpochMilli();
        long time2 = new Date().getTime();
        System.out.println(format.format(new Date(time1)));
        System.out.println(format.format(new Date(time2)));
    }

    @Test
    public void test4() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        SearchResponse response = client.prepareSearch("applog")
                .setTypes("doc")
                .setQuery(boolQuery()
                        .must(matchQuery("level", "ERROR"))
                        .must(rangeQuery("@timestamp")
                                .from(LocalDateTime.now().minusHours(1).toInstant(ZoneOffset.of("+8")).toEpochMilli())
                                .to(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli())
                        )
                )
                .get();

        System.out.println(response.getHits().getTotalHits());
    }

    @Test
    public void test5() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        SearchResponse response = client.prepareSearch("applog")
                .setTypes("doc")
                .setQuery(boolQuery()
                        .must(matchQuery("level", "ERROR"))
                        .must(rangeQuery("timestamp")
                                .from(LocalDateTime.now().minusHours(1).toInstant(ZoneOffset.of("+8")).toEpochMilli())
                                .to(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli())
                        )
                )
                .get();

        System.out.println(response.getHits().getTotalHits());
    }

    @Test
    public void test6() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        long currentMillisecond = new Date().getTime();

        SearchResponse response = client.prepareSearch("applog")
                .setQuery(boolQuery()
                        .must(rangeQuery("@timestamp")
                                .from(currentMillisecond - 1000 * 300000)
                                .to(currentMillisecond)
                        )
                )
                .addAggregation(AggregationBuilders.count("count").field("costTime"))
                .get();
        System.out.println(response.getAggregations().get("count").toString());
    }
    @Test
    public void test7() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        long currentMillisecond = System.currentTimeMillis();

        SearchResponse response = client.prepareSearch("applog")
                .setQuery(boolQuery()
                        .must(rangeQuery("eventTime")
                                .from(currentMillisecond - 1000 * 300000)
                                .to(currentMillisecond)
                        )
                )
                .addAggregation(AggregationBuilders.avg("avgTime").field("costTime"))
                .get();

        Avg avg = response.getAggregations().get("avgTime");
        System.out.println(avg.getValue());
    }

    @Test
    public void countTest() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        long currentMillisecond = System.currentTimeMillis();
        QueryBuilder qb = rangeQuery("eventTime").from(currentMillisecond - 300000000).to(currentMillisecond);
        AggregationBuilder ab = terms("111").field("task");
        SearchResponse response = client.prepareSearch("applog")
                .setQuery(qb)
                .addAggregation(ab)
                .get();
        Terms terms = response.getAggregations().get("111");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        List<String> countX = new ArrayList<>();
        List<Integer> countY = new ArrayList<>();
        for (Terms.Bucket bucket: buckets) {
            countX.add((String) bucket.getKey());
            countY.add((int) bucket.getDocCount());
        }
        System.out.println(countX);
        System.out.println(countY);
    }

    @Test
    public void costTimeTest() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        long currentMillisecond = System.currentTimeMillis();
        QueryBuilder qb = rangeQuery("eventTime").from(currentMillisecond - 300000000).to(currentMillisecond);
        AggregationBuilder ab = terms("buckets").field("task").subAggregation(avg("averageCostTime").field("costTime"));
        SearchResponse response = client.prepareSearch("applog")
                .setQuery(qb)
                .addAggregation(ab)
                .get();
        Terms terms = response.getAggregations().get("buckets");
        System.out.println(terms);
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        List<String> countX = new ArrayList<>();
        List<Integer> countY = new ArrayList<>();
        for (Terms.Bucket bucket: buckets) {
            countX.add((String) bucket.getKey());
            Avg avg = bucket.getAggregations().get("averageCostTime");
            countY.add((int) avg.getValue());
        }
        System.out.println(countX);
        System.out.println(countY);
    }

    @Test
    public void successRateTest() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        long currentMillisecond = System.currentTimeMillis();
        QueryBuilder qb = rangeQuery("eventTime").from(currentMillisecond - 300000000).to(currentMillisecond);
        AggregationBuilder ab = terms("buckets").field("task").subAggregation(terms("successCount").field("result"));
        SearchResponse response = client.prepareSearch("applog")
                .setQuery(qb)
                .addAggregation(ab)
                .get();
        Terms terms = response.getAggregations().get("buckets");
        System.out.println(terms);
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        List<Object> countX = new ArrayList<>();
        List<Object> countY = new ArrayList<>();
        for (Terms.Bucket bucket: buckets) {
            countX.add(bucket.getKey());
            Terms terms1 = bucket.getAggregations().get("successCount");
            for (Terms.Bucket bucket1:terms1.getBuckets()) {
                if (bucket1.getKey().equals("success")) {
                    countY.add(bucket1.getDocCount()*100 / bucket.getDocCount());
                    break;
                }
            }
        }
        System.out.println(countX);
        System.out.println(countY);
    }

    @Test
    public void statusTest() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        long currentMillisecond = System.currentTimeMillis();
        QueryBuilder qb = boolQuery()
//                .must(rangeQuery("eventTime").from(currentMillisecond - 300000000).to(currentMillisecond))
                .must(matchQuery("system", "taobao"))
                .must(matchQuery("module","order"))
                .must(matchQuery("task", "add_user"));
        AggregationBuilder ab = stats("successCount").field("costTime");
        SearchResponse response = client.prepareSearch("applog")
                .setQuery(qb)
                .addAggregation(ab)
                .get();
        Stats stats = response.getAggregations().get("successCount");
        System.out.println(stats);
//        List<? extends Terms.Bucket> buckets = terms.getBuckets();
//        List<Object> countX = new ArrayList<>();
//        List<Object> countY = new ArrayList<>();
//        for (Terms.Bucket bucket: buckets) {
//            countX.add(bucket.getKey());
//            Terms terms1 = bucket.getAggregations().get("successCount");
//            for (Terms.Bucket bucket1:terms1.getBuckets()) {
//                if (bucket1.getKey().equals("success")) {
//                    countY.add(bucket1.getDocCount()*100 / bucket.getDocCount());
//                    break;
//                }
//            }
//        }
//        System.out.println(countX);
//        System.out.println(countY);
    }

    @Test
    public void historygramTest() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        QueryBuilder qb = boolQuery()
                .must(matchQuery("system", "taobao"))
                .must(matchQuery("module","order"))
                .must(matchQuery("task", "add_user"));
        AggregationBuilder ab = dateHistogram("successCount").field("eventTime").interval(1000*3600*24).format("yyyy-MM-dd");
        SearchResponse response = client.prepareSearch("applog")
                .setQuery(qb)
                .addAggregation(ab)
                .get();
        Histogram histogram = response.getAggregations().get("successCount");
        System.out.println(histogram);
    }

    @Test
    public void resultTest() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","my-application")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        QueryBuilder qb = boolQuery()
                .must(matchQuery("system", "taobao"))
                .must(matchQuery("module","order"))
                .must(matchQuery("task", "add_user"));
        AggregationBuilder ab = terms("result").field("result");
        SearchResponse response = client.prepareSearch("applog")
                .setQuery(qb)
                .addAggregation(ab)
                .get();
        Terms terms = response.getAggregations().get("result");
        System.out.println(terms);
    }

}
