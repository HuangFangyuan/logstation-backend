package com.hfy.elasticsearch.controller;

import com.hfy.elasticsearch.entity.Contact;
import com.hfy.elasticsearch.service.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * Created by HuangFangyuan on 2018/3/11.
 */
@RestController
@RequestMapping("/contact")
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addContact(@RequestParam("name") String name,
                                     @RequestParam("method") String method,
                                     @RequestParam("value") String value,
                                     @RequestParam("default") boolean defaultUse) {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setMethod(method);
        contact.setValue(value);
        contact.setDefaultUse(defaultUse);
        contactService.addContact(contact);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getContacts() {
        return new ResponseEntity<>(contactService.getContacts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getContact(@PathParam("id") int id) {
        return new ResponseEntity<>(contactService.getContact(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public ResponseEntity getDefaultContact() {
        return new ResponseEntity<>(contactService.getDefaultContact(), HttpStatus.OK);
    }
}
