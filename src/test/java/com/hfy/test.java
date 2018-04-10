package com.hfy;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
                        .must(matchQuery("level", "ERROR"))
                        .must(rangeQuery("@timestamp")
                                .from(currentMillisecond - 1000 * 30000)
                                .to(currentMillisecond)
                        )
                )
                .addAggregation(AggregationBuilders.count("count").field("costTime"))
                .get();

        System.out.println(response.getHits().getTotalHits());
    }
}
