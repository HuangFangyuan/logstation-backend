package com.hfy.logstation.job;

import com.hfy.logstation.entity.Monitor;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AvgMonitorJob extends AbstractJob {

    private static Logger LOGGER = LoggerFactory.getLogger(AvgMonitorJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail detail = jobExecutionContext.getJobDetail();
        JobDataMap map = detail.getJobDataMap();
        Monitor m = (Monitor) map.get("monitor");
        long end = System.currentTimeMillis();
        long start = end - 1000 * m.getInterval();
        double avg = logService.matchAndRangeAndAvg(m.getIndex(), m.getField(), m.getValue(), "eventTime", start, end, m.getAvgField());
        LOGGER.info("正在监控索引{},字段{},值{},过去{}中{}的平均值:{}",
                m.getIndex(), m.getField(), m.getValue(), m.getInterval(), m.getAvgField(), avg);
        if (m.getMethod().equals("email")) {
            sendEmail(m, avg);
        }
        else if (m.getMethod().equals("message")){
            //todo
            sendMessage();
        }
    }
}
