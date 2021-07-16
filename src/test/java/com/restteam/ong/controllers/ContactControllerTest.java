package com.restteam.ong.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.ContactDTO;
import com.restteam.ong.models.Contact;
import com.restteam.ong.services.ContactService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ContactControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Mock
    private final ContactService contactService = Mockito.mock(ContactService.class);

    @InjectMocks
    private ContactController contactController;


    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        //Creamos la lista de contactos para testear el metodo GET.
        ContactDTO contact1 = new ContactDTO("test1","1111","test1@hotmail.com","TEST1");
        ContactDTO contact2 = new ContactDTO("test2","2222","test2@hotmail.com","TEST2");
        List<ContactDTO> listaContactos = new ArrayList<>();
        listaContactos.add(contact1);
        listaContactos.add(contact2);

        MockitoAnnotations.openMocks(this);
        this.mockMvc =  MockMvcBuilders.standaloneSetup(contactController).build();
        Mockito.when(contactService.sendWelcomeMail(any())).thenReturn(true);
        Mockito.when(contactService.getContacts()).thenReturn(listaContactos);
    }

    private String mapToJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void getContacts() throws Exception {
        mockMvc.perform(get("/contacts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        //Ademas de que no tira error, se puede ver en el responsebody como ambos contactos fueron retornados.
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    void createContact() throws Exception {
        Contact contact = new Contact();
        contact.setName("prueba1");
        contact.setPhone("11111111");
        contact.setEmail("test1@hotmail.com");
        contact.setMessage("TEST1");
        String userRequest = mapToJSON(contact);

        mockMvc.perform(post("/contacts").
                content(userRequest).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().is2xxSuccessful()).
                andReturn();
    }
}