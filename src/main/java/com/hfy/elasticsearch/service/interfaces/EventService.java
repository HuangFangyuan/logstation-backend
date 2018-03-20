package com.hfy.elasticsearch.service.interfaces;

import com.hfy.elasticsearch.entity.Event;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/19.
 */
public interface EventService {
    List<Event> getEvents();
    Event getEvent(Integer id);
    void addEvent(Event event);
}
