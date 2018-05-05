package com.hfy.logstation.service.impl;

import com.hfy.logstation.dto.EventDto;
import com.hfy.logstation.entity.Event;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.repository.EventRepository;
import com.hfy.logstation.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;


@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventDto getEvents(int from, int size) {
        EventDto eventDto = new EventDto();
        Page<Event> page = eventRepository.findAll(new PageRequest(from, size, Sort.Direction.DESC, "createTime"));
        eventDto.setEvents(page.getContent());
        eventDto.setTotal(page.getTotalElements());
        return eventDto;
    }

    @Override
    public Event getEvent(Integer id) {
        return Optional.ofNullable(eventRepository.findOne(id))
                .orElseThrow(() -> new ServerException("not exist this id:" + id));
    }

    @Override
    public Event addEvent(@NotNull Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event update(@NotNull Event event) {
        return eventRepository.save(event);
    }

}
