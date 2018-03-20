package com.hfy.elasticsearch.service.impl;

import com.hfy.elasticsearch.entity.Contact;
import com.hfy.elasticsearch.repository.ContactRepository;
import com.hfy.elasticsearch.service.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HuangFangyuan on 2018/3/12.
 */
@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public void addContact(Contact contact) {
        if (contact.getDefaultUse()) {
            Contact pre = getDefaultContact();
            pre.setDefaultUse(false);
            contactRepository.save(pre);
        }
        contactRepository.save(contact);
    }

    @Override
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getContact(int id) {
        return contactRepository.getOne(id);
    }

    @Override
    public Contact getDefaultContact() {
        return contactRepository.getByDefaultUse(true);
    }
}
