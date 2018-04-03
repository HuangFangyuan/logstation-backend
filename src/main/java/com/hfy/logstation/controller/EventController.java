package com.hfy.logstation.controller;

import com.hfy.logstation.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by HuangFangyuan on 2018/3/19.
 */
@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getEvents() {
        return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public ResponseEntity getEvent(@PathVariable("id") int id) {
        return new ResponseEntity<>(eventService.getEvent(id), HttpStatus.OK);
    }
}
