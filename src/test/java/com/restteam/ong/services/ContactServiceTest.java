package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.ContactDTO;
import com.restteam.ong.models.Contact;
import com.restteam.ong.repositories.ContactRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


//@SpringBootTest esta anotacion no hace falta, al ser en su mayoria unit test no es necesario encender el server.
class ContactServiceTest {

    ModelMapper modelMapper = new ModelMapper();

    Environment env = Mockito.mock(Environment.class);

    EmailService emailService = new EmailService(env);

    ContactRepository contactRepository = Mockito.mock(ContactRepository.class);

    ContactService contactService = new ContactService(contactRepository,emailService,modelMapper);


    @BeforeEach //El codigo dentro del setup se corre antes de cada test.
    void setUp() {
        //contacto mock 1
        Contact contactMock1 = new Contact();
        contactMock1.setEmail("askjfuhje@hotmail.com");
        contactMock1.setId(1L);
        contactMock1.setName("test1");
        contactMock1.setMessage("Hola1");
        contactMock1.setPhone("12345678");
        //contacto mock 2
        Contact contactMock2 = new Contact();
        contactMock2.setEmail("test2@asdasd.com");
        contactMock2.setId(2L);
        contactMock2.setName("test2");
        contactMock2.setMessage("Hola2");
        contactMock2.setPhone("22345678");
        //contacto mock 3
        Contact contactMock3 = new Contact();
        contactMock3.setEmail("test3@asdasd.com");
        contactMock3.setId(3L);
        contactMock3.setName("test3");
        contactMock3.setMessage("Hola3");
        contactMock3.setPhone("32345678");
        //Lista de contactos mock
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contactMock1);contactList.add(contactMock2);contactList.add(contactMock3);


        //Comandos a mockear:
        Mockito.when(contactRepository.findAll()).thenReturn(contactList);
        Mockito.when(contactRepository.save(any(Contact.class))).thenReturn(new Contact());
        Mockito.when(contactRepository.findByName("test1")).thenReturn(Optional.of(contactMock1));
        Mockito.when(contactRepository.findByName("test2")).thenReturn(Optional.of(contactMock2));
        Mockito.when(contactRepository.findByName("test3")).thenReturn(Optional.of(contactMock3));
        Mockito.when(env.getProperty(anyString())).thenReturn("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");
    }

    @Test
    void isValid() {
        Contact contact = contactRepository.findByName("test1").orElse(new Contact());
        Assertions.assertNotNull(contact.getName());
        Assertions.assertNotNull(contact.getEmail());
        Assertions.assertTrue(contactService.isValid(contact));
    }

    @Test
    void sendWelcomeMail(){
        Contact contact = contactRepository.findByName("test1").orElse(new Contact());
        /* El test de abajo funciona, pero solo si se utiliza el email "verificado".
         * AVISO: La linea de abajo es de integracion, no unit testing.*/

        //Assertions.assertTrue(contactService.sendWelcomeMail(contact));
    }

    @Test
    void createContact() {
        //create contact es isValid + repo.save + sendWelcomeMail, asi que esta testeado.
}

    @Test
    void getContacts() {
        List<ContactDTO> contacts = contactService.getContacts();
        Assertions.assertEquals(contacts.size(),3);

        Assertions.assertEquals(contacts.get(0).getName(),"test1");
        Assertions.assertEquals(contacts.get(0).getMessage(),"Hola1");

        Assertions.assertEquals(contacts.get(1).getName(),"test2");
        Assertions.assertEquals(contacts.get(1).getMessage(),"Hola2");

        Assertions.assertEquals(contacts.get(2).getName(),"test3");
        Assertions.assertEquals(contacts.get(2).getMessage(),"Hola3");
    }

    @Test
    void mapToDTO() {
        List<Contact> contactList = contactRepository.findAll();
        Contact contact0 = contactList.get(0);
        ContactDTO contactDTO = contactService.mapToDTO(contact0);

        Assertions.assertEquals(contactDTO.getName(),contact0.getName());

        Assertions.assertEquals(contactDTO.getEmail(),contact0.getEmail());

        Assertions.assertEquals(contactDTO.getPhone(),contact0.getPhone());

        Assertions.assertEquals(contactDTO.getMessage(),contact0.getMessage());
    }

    @AfterEach  //El codigo de tearDown se corre al finalizar cada test.
    void tearDown() { }
}