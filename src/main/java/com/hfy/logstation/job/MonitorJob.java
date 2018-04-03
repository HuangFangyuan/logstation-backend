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
        List<Hit> hits = logService.get(
                m.getIndex(),
                m.getField(),
                m.getValue(),
                currentMillisecond - 1000 * interval,
                currentMillisecond);
        if (m.getMethod().equals("email")) {
            for (Hit h: hits) {
                mailService.sendSimpleEmail(
                        "hfy_1996@163.com",
                        m.getContact(),
                        m.getSubject(),
                        m.getContent() + h.getSource().getMessage());
                Event event = new Event();
                event.setCreateTime(new Date());
                event.setMonitor(m);
                event.setSend(true);
                eventService.addEvent(event);
            }
        }
        else if (m.getMethod().equals("message")){
            System.out.println("message");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        LOGGER.info("正在监控索引{},字段{},值{},起始时间{},截止时间{},命中次数{}",
                m.getIndex(), m.getField(), m.getValue(),format.format(currentMillisecond - 1000 * interval),format.format(currentMillisecond),hits.size());
    }
}
