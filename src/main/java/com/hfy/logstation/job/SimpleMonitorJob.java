package com.hfy.logstation.job;

import com.hfy.logstation.entity.Hit;
import com.hfy.logstation.entity.Monitor;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.hfy.logstation.constant.Constants.DEFAULT_RANGE_FIELD;
import static com.hfy.logstation.util.DateUtil.format;

public class SimpleMonitorJob extends AbstractJob {

    private static Logger LOGGER = LoggerFactory.getLogger(SimpleMonitorJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail detail = jobExecutionContext.getJobDetail();
        JobDataMap map = detail.getJobDataMap();
        Monitor m = (Monitor) map.get("monitor");
        long end = System.currentTimeMillis();
        long start = end - 1000 * m.getInterval();
        List<Hit> hits = logService.matchAndRange(m.getIndex(), m.getField(), m.getValue(), "@timestamp", start, end).getHits();
        LOGGER.info("正在监控索引{},字段{},值{},起始时间{},截止时间{},命中次数{}", m.getIndex(), m.getField(), m.getValue(),format(start), format(end),hits.size());
        if ("email".equals(m.getMethod())) {
            hits.forEach(h -> {
                sendEmail(m, h);
            });
        }
        else if ("message".equals(m.getMethod())){
            //todo
            sendMessage();
        }
    }
}
