package com.hfy;

import com.hfy.logstation.entity.Monitor;
import com.hfy.logstation.service.interfaces.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogstationApplicationTests {

	@Autowired
	MonitorService monitorService;

	@Autowired
	LogService logService;

	@Autowired
	HealthService healthService;

	@Autowired
	MailService mailService;

	@Autowired
	ScheduleService scheduleService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test1() throws IOException, SchedulerException {
		Monitor monitor = new Monitor();
		monitor.setIndex("applog");
		monitor.setField("level");
		monitor.setValue("debug");
		monitor.setMethod("电话");
		monitor.setActive(true);
		monitor.setContact("15528117076");
		monitorService.addMonitor(monitor);
	}

	@Test
	public void test5() throws IOException {
		System.out.println(logService.get("applog", 0 , 5));
	}

	@Test
	public void test2()  {
		System.out.println(monitorService.getMonitors());
	}

	@Test
	public void test3() {
		System.out.println(monitorService.getMonitor(1));
	}

	@Test
	public void test4() {
//		System.out.println(healthService.healthCondition("applog",1, "hour"));
//		System.out.println(healthService.healthCondition("applog",1, "day"));
//		System.out.println(healthService.healthCondition("applog",1, "week"));
		System.out.println(healthService.healthCondition("applog",1, "hour"));
	}

	@Test
	public void testMailService() {

	}

	@Test
	public void testScheduleService() throws IOException, SchedulerException {
		scheduleService.executeMonitors();
	}


}
