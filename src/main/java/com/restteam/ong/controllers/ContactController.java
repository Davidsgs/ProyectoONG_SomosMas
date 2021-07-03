package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.ContactDTO;
import com.restteam.ong.controllers.dto.EmailRequest;
import com.restteam.ong.models.Contact;
import com.restteam.ong.services.ContactService;
import com.restteam.ong.services.EmailService;
import com.sendgrid.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        if(contact.getName() == null || contact.getEmail() == null){
            return new ResponseEntity<>("Request must contain name and email values.", HttpStatus.BAD_REQUEST);
        }
        try {
            contactService.createContact(contact);
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(contact.getEmail());
            emailRequest.setSubject("Contacto completado con exito.");
            emailRequest.setBody(String.format("Hola %s! Te informamos que el formulario de contacto se completo con " +
                    "exito. Desde fundacion somos mas te agradecemos por contactarte. Saludos!", contact.getName()));
            Response emailResponse = emailService.sendTextEmail(emailRequest);
            if(emailResponse.getStatusCode() != 200){
                return new ResponseEntity<>("Contact created succesfully, but informative mail failed to send.", HttpStatus.MULTI_STATUS);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("We are sorry. Something failed, try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Contact created successfully ", HttpStatus.OK);
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
