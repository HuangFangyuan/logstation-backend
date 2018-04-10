package com.hfy.logstation.job;

import com.hfy.logstation.entity.Event;
import com.hfy.logstation.entity.Hit;
import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.service.interfaces.EventService;
import com.hfy.logstation.service.interfaces.LogService;
import com.hfy.logstation.service.interfaces.MailService;
import com.hfy.logstation.util.ApplicationContextReference;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MonitorJob implements Job {

    private static Logger LOGGER = LoggerFactory.getLogger(MonitorJob.class);

    private LogService logService;
    private MailService mailService;
    private EventService eventService;

    public MonitorJob() {
        ApplicationContext context = ApplicationContextReference.getApplicationContext();
        Assert.notNull(context,"Illegal state!");
        logService = context.getBean(LogService.class);
        mailService = context.getBean(MailService.class);
        eventService = context.getBean(EventService.class);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail detail = jobExecutionContext.getJobDetail();
        JobDataMap map = detail.getJobDataMap();
        Monitor m = (Monitor) map.get("monitor");
        int interval =  map.getInt("interval");
        long currentMillisecond = new Date().getTime();
        List<Hit> hits = null;
        switch (m.getMethod()) {
            case "定值监控":
                simpleMonitor(m);
                break;
            case "次数监控":
                timeMonitor(m);
                break;
            case "平均值监控":
                avgMonitor(m);
                break;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        LOGGER.info("正在监控索引{},字段{},值{},起始时间{},截止时间{},命中次数{}",
                m.getIndex(), m.getField(), m.getValue(),format.format(currentMillisecond - 1000 * interval),format.format(currentMillisecond),hits.size());
    }

    private void simpleMonitor(Monitor m) {
        long end = System.currentTimeMillis();
        long start = end - 1000* m.getInterval();
        List<Hit> hits = logService.matchAndRange(
                m.getIndex(),
                m.getField(),
                m.getValue(),
                "eventTime",
                start,
                end
        ).getHits();
        if (m.getMethod().equals("email")) {
            for (Hit h: hits) {
                sendEmail(m, h);
            }
        }
        else if (m.getMethod().equals("message")){
            sendMessage();
        }
    }

    //次数监控
    //在某时间段内计算某个字段出现的次数
    private void timeMonitor(Monitor m) {
        long end = System.currentTimeMillis();
        long start = end - 1000* m.getInterval();
        List<Hit> hits = logService.matchAndRangeAndCount(
                m.getIndex(),
                m.getField(),
                m.getValue(),
                "eventTime",
                start,
                end,
                m.getField()
        )
                .getHits();
        if (m.getMethod().equals("email")) {
            sendEmail(m, null);
        }
        else if (m.getMethod().equals("message")){
            sendMessage();
        }
    }

    //平均值监控
    //在某时间段内计算某个字段的平均值
    private int avgMonitor(Monitor m) {
        long end = System.currentTimeMillis();
        long start = end - 1000* m.getInterval();
//        List<Hit> hits = logService.matchAndRange(
//                m.getIndex(),
//                m.getField(),
//                m.getValue(),
//                "eventTime",
//                currentMillisecond - 1000 * m.getInterval(),
//                currentMillisecond).getHits();
//        int sum = 0;
//        for (Hit h: hits) {
//            sum += h.getSource().getCostTime();
//        }
//        return sum/hits.size();
        logService.matchAndRangeAndAvg(m.getIndex(), m.getField(), m.getValue(), "eventTime", start, end, m.get)
    }

    private <T> void sendEmail(Monitor m, T message) {
        mailService.sendSimpleEmail(
                "hfy_1996@163.com",
                m.getContact(),
                m.getSubject(),
                m.getContent());
        Event event = new Event();
        event.setCreateTime(new Date());
        event.setMonitor(m);
        event.setSend(true);
        eventService.addEvent(event);
    }

    private void sendMessage() {
        System.out.println("message");
    }
}
