package com.hfy.elasticsearch.service.impl;

import com.hfy.elasticsearch.entity.Event;
import com.hfy.elasticsearch.repository.EventRepository;
import com.hfy.elasticsearch.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/19.
 */
@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEvent(Integer id) {
        return eventRepository.getOne(id);
    }

    @Override
    public void addEvent(Event event) {
        eventRepository.save(event);
    }
}
