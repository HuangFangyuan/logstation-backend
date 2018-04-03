package com.hfy.logstation.service.impl;

import com.hfy.logstation.entity.Contact;
import com.hfy.logstation.repository.ContactRepository;
import com.hfy.logstation.service.interfaces.ContactService;
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
    public int addContact(Contact contact) {
        if (contact.getDefaultUse()) {
            Contact pre = getDefaultContact();
            if (pre != null) {
                pre.setDefaultUse(false);
                contactRepository.save(pre);
            }

        }
        return contactRepository.save(contact).getId();
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

    @Override
    public void deleteContact(int id) {
        contactRepository.delete(id);
    }
}
