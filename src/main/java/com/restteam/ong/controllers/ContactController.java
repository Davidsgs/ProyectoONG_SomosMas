package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.ContactDTO;
import com.restteam.ong.models.Contact;
import com.restteam.ong.services.ContactService;
import com.restteam.ong.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity<String> createContact(@RequestBody Contact contact){
        try{
            contactService.createContact(contact);
        }
        catch(IllegalStateException ise){
            return new ResponseEntity<>(ise.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(RuntimeException re){
            return new ResponseEntity<>(re.getMessage(),HttpStatus.FAILED_DEPENDENCY);
        }
        return new ResponseEntity<>("La solicitud se ejecuto correctamente!",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getContacts(){
        List<ContactDTO> contacts;
        try {
            contacts = contactService.getContacts();
        } catch (Exception e) {
            return new ResponseEntity<>("We are sorry, something failed. Try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(contacts,HttpStatus.OK);
    }
}
