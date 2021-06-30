package com.restteam.ong.services;

import com.restteam.ong.models.Contact;
import com.restteam.ong.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }
}
