package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.Event;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/19.
 */
public interface EventService {
    List<Event> getEvents();
    Event getEvent(Integer id);
    int addEvent(Event event);
}
