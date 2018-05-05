package com.hfy.logstation.service.impl;

import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.repository.MonitorRepository;
import com.hfy.logstation.service.interfaces.MonitorService;
import com.hfy.logstation.service.interfaces.ScheduleService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MonitorServiceImpl implements MonitorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorServiceImpl.class);

    private MonitorRepository monitorRepository;
    private ScheduleService scheduleService;

    @Override
    public void addMonitor(@NotNull Monitor monitor) throws SchedulerException {
        monitorRepository.save(monitor);
        scheduleService.executeMonitor(monitor);
    }

    @Override
    public List<Monitor> getMonitors(boolean onlyActive) {
        if (onlyActive) return getActiveMonitors();
        else return monitorRepository.findAll();
    }

    @Override
    public List<Monitor> getActiveMonitors() {
        return Optional.ofNullable(monitorRepository.findByActive(true))
                .orElse(Collections.emptyList());
    }

    @Override
    public Monitor getMonitor(int id) {
        return Optional.ofNullable(monitorRepository.findOne(id))
                .orElseThrow(() -> new ServerException("not exist this id:" + id));
    }

    @Override
    public Monitor updateMonitor(@NotNull Monitor monitor) {
        return monitorRepository.save(monitor);
    }

    //将激活的监视器置为非激活
    //将非激活的监视器删除
    @Override
    public void deleteMonitor(Integer id) {
        Optional.ofNullable(getMonitor(id))
                .ifPresent(this::deleteMonitor);
    }

    private void deleteMonitor(@NotNull Monitor m) {
        if (m.isActive()) {
            m.setActive(false);
            updateMonitor(m);
            try {
                scheduleService.removeMonitor(m.getId());
            } catch (SchedulerException e) {
                LOGGER.info("{}", e);
            }
        }
        else {
            monitorRepository.delete(m);
        }
    }

    @Autowired
    public void setMonitorRepository(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
