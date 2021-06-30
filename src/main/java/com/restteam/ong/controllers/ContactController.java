package com.restteam.ong.controllers;

import com.restteam.ong.models.Contact;
import com.restteam.ong.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;

    @PostMapping
    public ResponseEntity<String> createContact(@RequestBody Contact contact){
        if(contact.getName() == null || contact.getEmail() == null){
            return new ResponseEntity<>("Request must contain name and email values.", HttpStatus.BAD_REQUEST);
        }
        try {
            contactService.createContact(contact);
        } catch (Exception e) {
            return new ResponseEntity<>("We are sorry. Something failed, try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Contact created successfully ", HttpStatus.OK);
    }
}
