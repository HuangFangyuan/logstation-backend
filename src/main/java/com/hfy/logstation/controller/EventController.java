package com.hfy.logstation.controller;

import com.hfy.logstation.service.interfaces.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public ResponseEntity getEvents(@RequestParam("from") int from,
                                    @RequestParam("size") int size) {
        return new ResponseEntity<>(eventService.getEvents(from, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable("id") int id) {
        return new ResponseEntity<>(eventService.getEvent(id), HttpStatus.OK);
    }
}
