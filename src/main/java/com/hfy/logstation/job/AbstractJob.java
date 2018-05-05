package com.hfy.logstation.job;

import com.hfy.logstation.entity.Event;
import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.service.interfaces.EventService;
import com.hfy.logstation.service.interfaces.LogService;
import com.hfy.logstation.service.interfaces.MailService;
import com.hfy.logstation.util.ApplicationContextReference;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;


public abstract class AbstractJob implements Job {

    LogService logService;
    private MailService mailService;
    private EventService eventService;

    AbstractJob() {
        ApplicationContext context = ApplicationContextReference.getApplicationContext();
        logService = context.getBean(LogService.class);
        mailService = context.getBean(MailService.class);
        eventService = context.getBean(EventService.class);
    }

    @Override
    public abstract void execute(JobExecutionContext context) throws JobExecutionException;

    void sendEmail(Monitor m, Object msg) {
        Event event = new Event();
        event.setMonitor(m);
        event = eventService.addEvent(event);
        mailService.sendEmail(m, msg);
        event.setSend(true);
        eventService.update(event);
    }

    void sendMessage(){

    }
}
