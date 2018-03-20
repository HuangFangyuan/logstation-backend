package com.hfy.elasticsearch.service.interfaces;

import com.hfy.elasticsearch.entity.Contact;
import com.hfy.elasticsearch.entity.Monitor;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/12.
 */
public interface ContactService {

    void addContact(Contact contact);
    List<Contact> getContacts();
    Contact getContact(int id);
    Contact getDefaultContact();
}
