package com.hfy.logstation.controller;

import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.entity.Response;
import com.hfy.logstation.service.interfaces.MonitorService;
import com.hfy.logstation.util.ResponseUtil;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @PostMapping()
    public Response addMonitor(@RequestParam("name") String name,
                               @RequestParam("index") String index,
                               @RequestParam("type") String type,
                               @RequestParam("field") String field,
                               @RequestParam("value") String value,
                               @RequestParam("interval") Integer interval,
                               @RequestParam("method") String method,  //通知方式
                               @RequestParam("contact") String contact,
                               @RequestParam("subject") String subject,
                               @RequestParam("content")String content,
                               @RequestParam("frequency")Integer frequency,
                               @RequestParam("avgField")String avgField) throws SchedulerException {

        Monitor monitor = new Monitor();
        monitor.setName(name);
        monitor.setIndex(index);
        monitor.setType(type);
        monitor.setField(field);
        monitor.setValue(value);
        monitor.setInterval(interval);
        monitor.setMethod(method);
        monitor.setContact(contact);
        monitor.setSubject(subject);
        monitor.setContent(content);
        monitor.setActive(true);
        monitor.setCreateTime(new Date());
        monitor.setFrequency(frequency);
        monitor.setAvgField(avgField);

        monitorService.addMonitor(monitor);
        return ResponseUtil.success();
    }

    @GetMapping()
    public Response getMonitors(@RequestParam("onlyActive")boolean onlyActive) {
        return ResponseUtil.success(monitorService.getMonitors(onlyActive));
    }

    @GetMapping("/{id}")
    public Response getMonitor(@PathVariable("id") int id) {
        return ResponseUtil.success(monitorService.getMonitor(id));
    }

    @PutMapping()
    public Response modifyMonitor(@RequestParam("id") int id,
                                  @RequestParam("name") String name,
                                  @RequestParam("index") String index,
                                  @RequestParam("type") String type,
                                  @RequestParam("field") String field,
                                  @RequestParam("value") String value,
                                  @RequestParam("interval") Integer interval,
                                  @RequestParam("method") String method,  //通知方式
                                  @RequestParam("contact") String contact,
                                  @RequestParam("subject") String subject,
                                  @RequestParam("content")String content,
                                  @RequestParam("frequency")Integer frequency,
                                  @RequestParam("avgField")String avgField) {

        Monitor monitor = new Monitor();
        monitor.setId(id);
        monitor.setName(name);
        monitor.setIndex(index);
        monitor.setType(type);
        monitor.setField(field);
        monitor.setValue(value);
        monitor.setInterval(interval);
        monitor.setMethod(method);
        monitor.setContact(contact);
        monitor.setSubject(subject);
        monitor.setContent(content);
        monitor.setActive(true);
        monitor.setCreateTime(new Date());
        monitor.setFrequency(frequency);
        monitor.setAvgField(avgField);
        monitorService.updateMonitor(monitor);

        return ResponseUtil.success();
    }

    @DeleteMapping("/{id}")
    public Response deleteMonitor(@PathVariable("id") Integer id) throws SchedulerException {
        monitorService.deleteMonitor(id);
        return ResponseUtil.success();
    }
}
