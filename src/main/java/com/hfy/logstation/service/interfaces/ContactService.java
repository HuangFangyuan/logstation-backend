package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> getContacts();
    Contact getContact(int id);
    Contact addContact(Contact contact);
    Contact getDefaultContact();
    void deleteContact(int id);
    Contact updateContact(Contact contact);

}
