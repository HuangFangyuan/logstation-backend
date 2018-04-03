package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.Contact;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/12.
 */
public interface ContactService {

    int addContact(Contact contact);
    List<Contact> getContacts();
    Contact getContact(int id);
    Contact getDefaultContact();
    void deleteContact(int id);
}
