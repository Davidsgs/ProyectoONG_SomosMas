package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.ContactDTO;
import com.restteam.ong.controllers.dto.EmailRequest;
import com.restteam.ong.models.Contact;
import com.restteam.ong.repositories.ContactRepository;
import com.sendgrid.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    EmailService emailService;

    ModelMapper modelMapper = new ModelMapper();

    public void createContact(Contact contact) {
        if(!isValid(contact)){
            throw new IllegalStateException("El email o el usuario no son validos. Intente de nuevo.");
        }
        contactRepository.save(contact);
        if(!sendWelcomeMail(contact)){
            throw new UnsatisfiedDependencyException("Se creo el usuario, pero fallo el envio de correo.","","","");
        }
    }

    public boolean isValid(Contact contact){
        return contact.getName() != null && contact.getEmail() != null;
    }

    public boolean sendWelcomeMail(Contact contact) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(contact.getEmail());
        emailRequest.setSubject("Contacto completado con exito.");
        emailRequest.setBody(String.format("Hola %s! Te informamos que el formulario de contacto se completo con " +
                "exito. Desde fundacion SOMOS MÁS te agradecemos por contactarte. Saludos!", contact.getName()));
        Response emailResponse = emailService.sendTextEmail(emailRequest);

        return emailResponse.getStatusCode() == 200 || emailResponse.getStatusCode() == 202;
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
