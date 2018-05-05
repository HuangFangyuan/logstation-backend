package com.hfy.logstation.dto;

import com.hfy.logstation.entity.Event;
import lombok.Data;

import java.util.List;

@Data
public class EventDto {

    List<Event> events;
    long total;
}
