package com.hfy.logstation.service.impl;

import com.hfy.logstation.entity.Contact;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.repository.ContactRepository;
import com.hfy.logstation.service.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @NotNull
    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact addContact(@NotNull Contact contact) {
        if (contact.getDefaultUse()) {
            Optional.ofNullable(getDefaultContact())
                    .ifPresent(pre -> {
                        pre.setDefaultUse(false);
                        updateContact(pre);
                    });
        }
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getContacts() {
        return Optional.ofNullable(contactRepository.findAll())
                .orElse(Collections.emptyList());
    }

    @Override
    public Contact getContact(int id) {
        return Optional.ofNullable(contactRepository.findOne(id))
                .orElseThrow(() -> new ServerException("not exist this id:" + id));
    }

    @Override
    public Contact getDefaultContact() {
        return Optional.ofNullable(contactRepository.getByDefaultUse(true))
                .orElseThrow(() -> new ServerException("not exist default contact"));
    }

    @Override
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(int id) {
        Optional.ofNullable(getContact(id))
                .ifPresent(c -> {
                    contactRepository.delete(c);
                    if (c.getDefaultUse()) {
                        getContacts().stream()
                                .findAny()
                                .ifPresent(any -> {
                                    any.setDefaultUse(true);
                                    updateContact(any);
                                });
                    }
                });
    }
}
