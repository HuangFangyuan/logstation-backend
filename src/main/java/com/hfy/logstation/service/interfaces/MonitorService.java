package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.Monitor;
import org.quartz.SchedulerException;

import java.util.List;

public interface MonitorService {

    void addMonitor(Monitor monitor) throws SchedulerException;
    List<Monitor> getMonitors(boolean onlyActive);
    List<Monitor> getActiveMonitors();
    Monitor getMonitor(int id);
    Monitor updateMonitor(Monitor monitor);
    void deleteMonitor(Integer id) throws SchedulerException;
}
