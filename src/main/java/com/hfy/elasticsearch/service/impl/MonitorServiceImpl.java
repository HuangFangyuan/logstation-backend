package com.hfy.elasticsearch.service.impl;

import com.hfy.elasticsearch.entity.Monitor;
import com.hfy.elasticsearch.repository.MonitorRepository;
import com.hfy.elasticsearch.service.interfaces.MonitorService;
import com.hfy.elasticsearch.service.interfaces.ScheduleService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/12.
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;
    @Autowired
    private ScheduleService scheduleService;


    @Override
    public void addMonitor(Monitor monitor) throws SchedulerException {
        monitorRepository.save(monitor);
        scheduleService.executeMonitor(monitor);
    }

    @Override
    public List<Monitor> getMonitors() {
        List<Monitor> monitorList = getActiveMonitor();
        return monitorList;
    }

    @Override
    public List<Monitor> getActiveMonitor() {
        return monitorRepository.findByActive(true);
    }

    @Override
    public Monitor getMonitor(int id) {
        return monitorRepository.findById(id);
    }

    @Override
    public void updateMonitor(Monitor monitor) {
        monitorRepository.save(monitor);
    }

    @Override
    public void deleteMonitor(Integer id) throws SchedulerException {
//        monitorRepository.delete(id);
        Monitor m = getMonitor(id);
        m.setActive(false);
        updateMonitor(m);
        scheduleService.removeMonitor(id);
    }
}
