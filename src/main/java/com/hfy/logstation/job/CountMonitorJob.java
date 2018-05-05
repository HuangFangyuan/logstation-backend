package com.hfy.logstation.job;

import com.hfy.logstation.entity.Monitor;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountMonitorJob extends AbstractJob {

    private static Logger LOGGER = LoggerFactory.getLogger(CountMonitorJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail detail = jobExecutionContext.getJobDetail();
        JobDataMap map = detail.getJobDataMap();
        Monitor m = (Monitor) map.get("monitor");
        long end = System.currentTimeMillis();
        long start = end - 1000 * m.getInterval();
        long count = logService.matchAndRangeAndCount(
                m.getIndex(),
                m.getField(),
                m.getValue(),
                "eventTime",
                start,
                end,
                m.getField()
        );
        LOGGER.info("正在监控索引{},字段{},值{},过去{}中出现次数:{}",
                m.getIndex(), m.getField(), m.getValue(), m.getInterval(), count);
        if (m.getMethod().equals("email")) {
            sendEmail(m, count);
        }
        else if (m.getMethod().equals("message")){
            //todo
            sendMessage();
        }
    }
}
