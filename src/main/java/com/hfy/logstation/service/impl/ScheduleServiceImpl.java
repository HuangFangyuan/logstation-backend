package com.hfy.logstation.service.impl;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.job.*;
import com.hfy.logstation.service.interfaces.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private static final HashMap<Integer, JobKey> ID2JOB = new HashMap<>();

    private Scheduler scheduler;
    private MonitorService monitorService;

    @PostConstruct
    public void init() throws InterruptedException {
        while (monitorService == null) {
            Thread.sleep(1000);
        }
        executeMonitors();
    }

    @Override
    public void executeMonitors() {
        Optional.ofNullable(monitorService.getActiveMonitors())
                .ifPresent(l -> l.forEach(this::executeMonitor));
    }

    @Override
    public void executeMonitor(Monitor monitor) {
        JobDetail job = JobFactory.createJobDetail(monitor.getType());
        job.getJobDataMap().put("monitor", monitor);
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(monitor.getInterval())
                        .repeatForever())
                .build();
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        ID2JOB.put(monitor.getId(), job.getKey());
    }

    @Override
    public void removeMonitor(Integer id) throws SchedulerException {
        scheduler.deleteJob(ID2JOB.get(id));
    }

    @Override
    public void shutDown() throws SchedulerException {
        scheduler.deleteJobs(new ArrayList<>(ID2JOB.values()));
    }

    @Autowired
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Autowired
    public void setMonitorService(MonitorService monitorService) {
        this.monitorService = monitorService;
    }
}
