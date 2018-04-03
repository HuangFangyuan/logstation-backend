package com.hfy.logstation.controller;

import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.service.interfaces.MonitorService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by HuangFangyuan on 2018/3/11.
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addMonitor(@RequestParam("name") String name,
                                     @RequestParam("index") String index,
                                     @RequestParam("type") String type,
                                     @RequestParam("field") String field,
                                     @RequestParam("value") String value,
                                     @RequestParam("interval") Integer interval,
                                     @RequestParam("method") String method,  //通知方式
                                     @RequestParam("contact") String contact,
                                     @RequestParam("subject") String subject,
                                     @RequestParam("content")String content) throws SchedulerException {

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

        monitorService.addMonitor(monitor);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getMonitors() {
        return new ResponseEntity<>(monitorService.getActiveMonitor(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getMonitor(@PathVariable("id") int id) {
        return new ResponseEntity<>(monitorService.getMonitor(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity modifyMonitor(@RequestParam("id") int id,
                                        @RequestParam("name") String name,
                                        @RequestParam("index") String index,
                                        @RequestParam("type") String type,
                                        @RequestParam("field") String field,
                                        @RequestParam("value") String value,
                                        @RequestParam("interval") Integer interval,
                                        @RequestParam("method") String method,  //通知方式
                                        @RequestParam("contact") String contact,
                                        @RequestParam("subject") String subject,
                                        @RequestParam("content")String content) {

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

        monitorService.updateMonitor(monitor);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMonitor(@PathVariable("id") Integer id) throws SchedulerException {
        monitorService.deleteMonitor(id);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
