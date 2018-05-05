package com.hfy.logstation.controller;

import com.hfy.logstation.service.interfaces.ScheduleService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by HuangFangyuan on 2018/3/18.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping()
    public void schedule() throws IOException, SchedulerException {
        scheduleService.executeMonitors();
    }

    @DeleteMapping("/{id}")
    public void removeSchedule(@PathVariable("id") int id) throws IOException, SchedulerException {
        scheduleService.removeMonitor(id);
    }

}
