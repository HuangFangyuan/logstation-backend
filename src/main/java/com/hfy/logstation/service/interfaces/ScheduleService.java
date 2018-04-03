package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.Monitor;
import org.quartz.SchedulerException;

import java.io.IOException;

/**
 * Created by HuangFangyuan on 2018/3/16.
 */
public interface ScheduleService {
    void executeMonitors() throws SchedulerException, IOException;
    void executeMonitor(Monitor monitor) throws SchedulerException;
    void removeMonitor(Integer id) throws SchedulerException;
}
