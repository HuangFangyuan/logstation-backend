package com.hfy.logstation.service.impl;

//job相关的builder
import static org.quartz.JobBuilder.*;

//trigger相关的builder
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

//日期相关的builder

import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.job.MonitorJob;
import com.hfy.logstation.service.interfaces.*;
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

    private static final HashMap<Integer, JobKey> ID2JOB = new HashMap<>();

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
        ID2JOB.put(monitor.getId(), job.getKey());
    }

    @Override
    public void removeMonitor(Integer id) throws SchedulerException {
        scheduler.deleteJob(ID2JOB.get(id));
    }

}
