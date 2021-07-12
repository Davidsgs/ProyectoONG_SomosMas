package com.restteam.ong.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.TestimonialDto;
import com.restteam.ong.models.Testimonial;
import com.restteam.ong.repositories.TestimonialRepository;
import com.restteam.ong.services.TestimonialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestimonialControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TestimonialRepository repository;

    @MockBean
    private TestimonialService service;

    @InjectMocks
    private TestimonialController controller;

    private String mapToJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * CASOS DE PRUEBA: CON DATOS VALIDOS.
     */

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void createAValidTestimonial() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials";
        //Creamos un TestimonialDto.
        TestimonialDto testimonialDto = new TestimonialDto();
        //Le agregamos sus campos correspondientes.
        testimonialDto.setName("Test Testimonial");
        testimonialDto.setContent("This is the content for example");
        testimonialDto.setImage("image.png");
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(testimonialDto);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(testimonialDto);
        //Hacemos las validaciones.
        mockMvc.perform(post(url) //Apuntamos a la ruta de la variable url.
                    .content(JSONRequest) //Enviando el JSON.
                    .contentType(MediaType.APPLICATION_JSON)) //Definimos que el tipo de dato que enviamos es un JSON.
                .andDo(print())
                .andExpect(status().isOk()) //Verificamos que el HttpStatus sea de OK.
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void deleteTestomonial() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials/{id}";
        //Creamos un Testimonial.
        Testimonial testimonial = new Testimonial();
        //Le agregamos sus campos correspondientes.
        testimonial.setId(1L);
        testimonial.setName("Test Testimonial");
        testimonial.setContent("This is the content for example");
        testimonial.setImage("image.png");

        //Hacemos las validaciones.
        mockMvc.perform(delete(url,testimonial.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()); //Verificamos que el HttpStatus sea de OK.
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void updateTestomonial() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials/{id}";
        //Creamos un Testimonial.
        Testimonial testimonial = new Testimonial();
        //Le agregamos sus campos correspondientes.
        testimonial.setId(15L);
        testimonial.setName("Test Testimonial");
        testimonial.setContent("This is the content for example");
        testimonial.setImage("image.png");

        //Creamos el DTO que va a tener información a modificar al testimonial.
        TestimonialDto testimonialDto = new TestimonialDto();
        testimonialDto.setName("New Test Testimonial 2.0");
        testimonialDto.setContent("New content");
        testimonialDto.setImage("newImage.png");
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(testimonialDto);
        });

        String JSONRequest = mapToJSON(testimonialDto);

        //Hacemos las validaciones.
        mockMvc.perform(put(url,testimonial.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                    .content(JSONRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()); //Verificamos que el HttpStatus sea de OK.
    }

    /**
     * CASOS DE PRUEBA: CON DATOS INVALIDOS.
     */

    //          -        Pruebas al Create        -
    @Test
    @WithMockUser(username = "user@email.com", authorities = {"ROLE_USER"}) //Cuenta de usuario registrado.
    public void tryCreateAValidTestimonialWithNonAdminUser() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials";
        //Creamos un TestimonialDto.
        TestimonialDto testimonialDto = new TestimonialDto();
        //Le agregamos sus campos correspondientes.
        testimonialDto.setName("Test Testimonial");
        testimonialDto.setContent("This is the content for example");
        testimonialDto.setImage("image.png");
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(testimonialDto);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(testimonialDto);
        //Hacemos las validaciones.
        mockMvc.perform(post(url) //Apuntamos a la ruta de la variable url.
                        .content(JSONRequest) //Enviando el JSON.
                        .contentType(MediaType.APPLICATION_JSON)) //Definimos que el tipo de dato que enviamos es un JSON.
                .andDo(print())
                .andExpect(status().isForbidden()) //Verificamos que el HttpStatus sea de FORBIDDEN.
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de usuario registrado.
    public void tryCreateATestimonialWithoutName() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials";
        //Creamos un TestimonialDto.
        TestimonialDto testimonialDto = new TestimonialDto();
        //Le agregamos sus campos correspondientes faltando el de name.
        testimonialDto.setContent("This is the content for example");
        testimonialDto.setImage("image.png");
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(testimonialDto);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(testimonialDto);
        //Hacemos las validaciones.
        mockMvc.perform(post(url) //Apuntamos a la ruta de la variable url.
                .content(JSONRequest) //Enviando el JSON.
                .contentType(MediaType.APPLICATION_JSON)) //Definimos que el tipo de dato que enviamos es un JSON.
                .andDo(print())
                .andExpect(status().isBadRequest()) //Verificamos que el HttpStatus sea de BAD_REQUEST.
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de usuario registrado.
    public void tryCreateATestimonialWithoutImage() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials";
        //Creamos un TestimonialDto.
        TestimonialDto testimonialDto = new TestimonialDto();
        //Le agregamos sus campos correspondientes faltando el de imagen.
        testimonialDto.setName("Test");
        testimonialDto.setContent("This is the content for example");
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(testimonialDto);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(testimonialDto);
        //Hacemos las validaciones.
        mockMvc.perform(post(url) //Apuntamos a la ruta de la variable url.
                .content(JSONRequest) //Enviando el JSON.
                .contentType(MediaType.APPLICATION_JSON)) //Definimos que el tipo de dato que enviamos es un JSON.
                .andDo(print())
                .andExpect(status().isBadRequest()) //Verificamos que el HttpStatus sea de BAD_REQUEST.
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de usuario registrado.
    public void tryCreateATestimonialWithoutContent() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials";
        //Creamos un TestimonialDto.
        TestimonialDto testimonialDto = new TestimonialDto();
        //Le agregamos sus campos correspondientes faltando el de content.
        testimonialDto.setName("Test");
        testimonialDto.setImage("image.png");
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(testimonialDto);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(testimonialDto);
        //Hacemos las validaciones.
        mockMvc.perform(post(url) //Apuntamos a la ruta de la variable url.
                .content(JSONRequest) //Enviando el JSON.
                .contentType(MediaType.APPLICATION_JSON)) //Definimos que el tipo de dato que enviamos es un JSON.
                .andDo(print())
                .andExpect(status().isBadRequest()) //Verificamos que el HttpStatus sea de BAD_REQUEST.
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de usuario registrado.
    public void tryCreateANullTestimonial() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials";
        //Mandamos el JSON en null.
        String JSONRequest = mapToJSON(null);
        //Hacemos las validaciones.
        mockMvc.perform(post(url) //Apuntamos a la ruta de la variable url.
                .content(JSONRequest) //Enviando el JSON.
                .contentType(MediaType.APPLICATION_JSON)) //Definimos que el tipo de dato que enviamos es un JSON.
                .andDo(print())
                .andExpect(status().isBadRequest()) //Verificamos que el HttpStatus sea de BAD_REQUEST.
                .andReturn();
    }

    //          -        Pruebas al Delete        -

    @Test
    @WithMockUser(username = "user@email.com", authorities = {"ROLE_USER"}) //Cuenta de usuario registrado.
    public void tryDeleteTestomonialWithNonAdminUser() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials/{id}";
        //Creamos un Testimonial.
        Testimonial testimonial = new Testimonial();
        //Le agregamos sus campos correspondientes.
        testimonial.setId(15L);
        testimonial.setName("Test Testimonial");
        testimonial.setContent("This is the content for example");
        testimonial.setImage("image.png");
        //Guardamos en el service el testimonial.
        service.createTestimonial(testimonial);
        //Le indicamos al repository mock que debe retornar cuando le pidan el testimonial.
        given(service.findById(testimonial.getId())).willReturn(testimonial);
        //Revisamos que lo que devuelva el services sea igual.
        Assertions.assertEquals(service.findById(testimonial.getId()),testimonial);
        //Hacemos las validaciones.
        mockMvc.perform(delete(url,testimonial.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden()); //Verificamos que el HttpStatus sea de FORBIDDEN.
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void tryDeleteTestomonialThatDoesntExist() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials/{id}";
        //Creamos un Testimonial.
        Testimonial testimonial = new Testimonial();
        //Le agregamos sus campos correspondientes.
        testimonial.setId(1L);
        testimonial.setName("Test Testimonial");
        testimonial.setContent("This is the content for example");
        testimonial.setImage("image.png");

        given(service.deleteSoft(testimonial.getId())).willThrow(IllegalStateException.class);

        //Hacemos las validaciones.
        mockMvc.perform(delete(url,testimonial.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); //Verificamos que el HttpStatus sea de BAD_REQUEST.
    }

    //          -        Pruebas al Update        -

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void tryUpdateTestomonialWithInvalidId() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials/{id}";
        //Creamos una variable con un ID invalido.
        Long invalidId = 0L;
        //Creamos el DTO que va a tener información a modificar al testimonial.
        TestimonialDto testimonialDto = new TestimonialDto();
        testimonialDto.setName("New Test Testimonial 2.0");
        testimonialDto.setContent("New content");
        testimonialDto.setImage("newImage.png");
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(testimonialDto);
        });
        //Asignamos el string en el JSONRequest.
        String JSONRequest = mapToJSON(testimonialDto);
        ModelMapper mapper = new ModelMapper();
        //Creamos la respuesta de parte del service.
        Testimonial testimonialToUpdate = mapper.map(testimonialDto,Testimonial.class);
        given(service.updateTestimonial(testimonialToUpdate,invalidId))
                .willThrow(IllegalStateException.class);

        //Hacemos las validaciones.
        mockMvc.perform(put(url,invalidId) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); //Verificamos que el HttpStatus sea de BadRequest.
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void tryUpdateTestomonialWithNullBody() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials/{id}";
        //Creamos un Testimonial.
        Testimonial testimonial = new Testimonial();
        //Le agregamos sus campos correspondientes.
        testimonial.setId(15L);
        testimonial.setName("Test Testimonial");
        testimonial.setContent("This is the content for example");
        testimonial.setImage("image.png");
        //Asignamos el string en el JSONRequest.
        String JSONRequest = mapToJSON(null);
        //Creamos la respuesta de parte del service.
        given(service.updateTestimonial(null,testimonial.getId()))
                .willThrow(IllegalStateException.class);

        //Hacemos las validaciones.
        mockMvc.perform(put(url,testimonial.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); //Verificamos que el HttpStatus sea de BadRequest.
    }

    @Test
    @WithMockUser(username = "user@email.com", authorities = {"ROLE_USER"}) //Cuenta de Admin.
    public void tryUpdateTestomonialWithNonAdminUser() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/testimonials/{id}";
        //Creamos un Testimonial.
        Testimonial testimonial = new Testimonial();
        //Le agregamos sus campos correspondientes.
        testimonial.setId(15L);
        testimonial.setName("Test Testimonial");
        testimonial.setContent("This is the content for example");
        testimonial.setImage("image.png");

        //Creamos el DTO que va a tener información a modificar al testimonial.
        TestimonialDto testimonialDto = new TestimonialDto();
        testimonialDto.setName("New Test Testimonial 2.0");
        testimonialDto.setContent("New content");
        testimonialDto.setImage("newImage.png");
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(testimonialDto);
        });

        String JSONRequest = mapToJSON(testimonialDto);

        //Hacemos las validaciones.
        mockMvc.perform(put(url,testimonial.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden()); //Verificamos que el HttpStatus sea de OK.
    }
}
