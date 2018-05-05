package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.Monitor;
import org.quartz.SchedulerException;

import java.io.IOException;

public interface ScheduleService {
    void executeMonitors();
    void executeMonitor(Monitor monitor) throws SchedulerException;
    void removeMonitor(Integer id) throws SchedulerException;
    void shutDown() throws SchedulerException;
}
