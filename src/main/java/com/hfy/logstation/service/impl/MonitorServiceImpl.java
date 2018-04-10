package com.hfy.logstation.service.impl;

import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.repository.MonitorRepository;
import com.hfy.logstation.service.interfaces.MonitorService;
import com.hfy.logstation.service.interfaces.ScheduleService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return getActiveMonitor();
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

    //不从数据库中删除，而是将监视器置为非激活
    @Override
    public void deleteMonitor(Integer id) throws SchedulerException {
        Monitor m = getMonitor(id);
        m.setActive(false);
        updateMonitor(m);
        scheduleService.removeMonitor(id);
    }
}
