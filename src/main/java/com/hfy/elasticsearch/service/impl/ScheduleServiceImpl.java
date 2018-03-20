package com.hfy.elasticsearch.service.impl;

//job相关的builder
import static org.quartz.JobBuilder.*;

//trigger相关的builder
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.DailyTimeIntervalScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;

//日期相关的builder
import static org.quartz.DateBuilder.*;

import com.hfy.elasticsearch.config.AutowiringSpringBeanJobFactory;
import com.hfy.elasticsearch.entity.Monitor;
import com.hfy.elasticsearch.job.MonitorJob;
import com.hfy.elasticsearch.service.interfaces.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * Created by HuangFangyuan on 2018/3/16.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private MonitorService monitorService;

    private static final HashMap<Integer, JobKey> ID2JOBMAP = new HashMap<>();

    @Override
    public void executeMonitors() throws SchedulerException, IOException {
        List<Monitor> monitorList = monitorService.getActiveMonitor();
        for (Monitor m : monitorList) {
            executeMonitor(m);
        }
    }

    @Override
    public void executeMonitor(Monitor monitor) throws SchedulerException {
        int interval = 5;
        JobDetail job = newJob()
                .ofType(MonitorJob.class)
                .usingJobData("interval", interval)
                .build();
        job.getJobDataMap().put("monitor", monitor);
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(interval)
                        .repeatForever())
                .build();
        scheduler.scheduleJob(job, trigger);
        ID2JOBMAP.put(monitor.getId(), job.getKey());
    }

    @Override
    public void removeMonitor(Integer id) throws SchedulerException {
        scheduler.deleteJob(ID2JOBMAP.get(id));
    }

}
