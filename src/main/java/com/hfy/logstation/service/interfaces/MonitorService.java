package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.Monitor;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/12.
 */
public interface MonitorService {

    void addMonitor(Monitor monitor) throws SchedulerException;
    List<Monitor> getMonitors();
    List<Monitor> getActiveMonitor();
    Monitor getMonitor(int id);
    void updateMonitor(Monitor monitor);
    void deleteMonitor(Integer id) throws SchedulerException;
}
