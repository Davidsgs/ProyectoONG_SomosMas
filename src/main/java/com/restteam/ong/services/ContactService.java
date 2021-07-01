package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.ContactDTO;
import com.restteam.ong.models.Contact;
import com.restteam.ong.repositories.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;
    ModelMapper modelMapper = new ModelMapper();

    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }

    public List<ContactDTO> getContacts() {
        List<Contact> contacts = contactRepository.findAll();
        List<ContactDTO> contactsDTO = new ArrayList<>();
        for (Contact c:
             contacts) {
            contactsDTO.add(mapToDTO(c));
        }
        return contactsDTO;
    }

    public ContactDTO mapToDTO(Contact contact){
        return modelMapper.map(contact, ContactDTO.class);
    }
}
