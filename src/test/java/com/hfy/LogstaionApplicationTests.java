package com.hfy;

import com.hfy.elasticsearch.service.LogService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogstaionApplicationTests {

	@Autowired
	LogService logService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test1() throws IOException {
		logService.getLogs("applog", 0 , 5);
	}

	@Test
	public void test2() throws IOException {

	}

}
