package com.hfy.logstation.controller;

import com.hfy.logstation.entity.Contact;
import com.hfy.logstation.entity.Response;
import com.hfy.logstation.service.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.hfy.logstation.util.ResponseUtil.success;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping()
    public Response addContact(@RequestParam("name") String name,
                               @RequestParam("method") String method,
                               @RequestParam("value") String value,
                               @RequestParam("default") boolean defaultUse) {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setMethod(method);
        contact.setValue(value);
        contact.setDefaultUse(defaultUse);
        contactService.addContact(contact);
        return success();
    }

    @GetMapping()
    public Response getContacts() {
        return success(contactService.getContacts());
    }

    @GetMapping(value = "/{id}")
    public Response getContact(@PathVariable("id") int id) {
        return success(contactService.getContact(id));
    }

    @GetMapping("/default")
    public Response getDefaultContact() {
        return success(contactService.getDefaultContact());
    }

    @DeleteMapping("/{id}")
    public Response deleteContact(@PathVariable("id") int id) {
        contactService.deleteContact(id);
        return success();
    }
}
