package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.dto.EventDto;
import com.hfy.logstation.entity.Event;

import java.util.List;

public interface EventService {
    EventDto getEvents(int from, int size);
    Event getEvent(Integer id);
    Event addEvent(Event event);
    Event update(Event event);
}
