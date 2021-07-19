package com.restteam.ong.controllers;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.CategoryRequest;
import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;
import com.restteam.ong.repositories.NewsRepository;
import com.restteam.ong.services.NewsService;
import com.restteam.ong.services.impl.NewsServicelmpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    //private CategoryRequest categoryRequest;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsServicelmpl servicelmpl;

    @Mock
    NewsRepository repository;

    @Mock
    private NewsService service;

    @InjectMocks
    private NewsController controller;


    private String mapToJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
    /**
     * CASOS DE PRUEBA: CON DATOS VALIDOS.
     */

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void createAValidNews() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news";
        //Creamos un NewsDto.
        NewsDTO newsDTO = new NewsDTO();
        var cat= new CategoryRequest();
        cat.setName("Hola1");
        cat.setImage("url:1");
        cat.setDescription("test1");

        //Le agregamos sus campos correspondientes.
        newsDTO.setName("Test News");
        newsDTO.setContent("This is the content for example");
        newsDTO.setImage("image.png");
        newsDTO.setCategoryRequest(cat);


        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(newsDTO);
        //Hacemos las validaciones.
        mockMvc.perform(post(url) //Apuntamos a la ruta de la variable url.
                .content(JSONRequest) //Enviando el JSON.
                .contentType(MediaType.APPLICATION_JSON)) //Definimos que el tipo de dato que enviamos es un JSON.
                .andDo(print())
                .andExpect(status().isOk()) //Verificamos que el HttpStatus sea de OK.
                .andReturn();
    }
    //Preguntar a nico
    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void deleteNews() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news/{id}";
        //Creamos un News.
        News news = new News();
        //CARGO DATOS DE CATEGORY
        Categories cat = new Categories();
        cat.setName("Hola1");
        cat.setImage("url:1");
        cat.setDescription("test1");
        //Le agregamos sus campos correspondientes.
        news.setId(1L);
        news.setName("Test News");
        news.setContent("This is the content for example");
        news.setImage("image.png");
        news.setCategories(cat);

        Mockito.when(servicelmpl.existId(anyLong())).thenReturn(true);

        //Hacemos las validaciones.
        mockMvc.perform(delete(url,news.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()); //Verificamos que el HttpStatus sea de OK.
    }
    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void updateNews() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news/{id}";
        //Creamos un News.
        News news = new News();
        //Le agregamos sus campos correspondientes.
        Categories cat= new Categories();
        cat.setId(15L);
        cat.setName("Hola1");
        cat.setImage("urlfgh");
        cat.setDescription("test1");
        //Le agregamos sus campos correspondientes.
        news.setId(15L);
        news.setName("Test News");
        news.setContent("This is the content for example");
        news.setImage("image.png");
        news.setCategories(cat);

        //Creamos el DTO que va a tener información a modificar al News.
        //Creamos un NewsDto.
        NewsDTO newsDTO = new NewsDTO();
        var cata = new CategoryRequest();
        cata.setName("Holaaa");
        cata.setImage("ur1");
        cata.setDescription("texto");

        //Le agregamos sus campos correspondientes.
        newsDTO.setName("Test News");
        newsDTO.setContent("This is the content for example newsrt");
        newsDTO.setImage("image.png");
        newsDTO.setCategoryRequest(cata);
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });

        String JSONRequest = mapToJSON(newsDTO);

        //Le indicamos al service mockeado lo que debe responder.
        Mockito.when(servicelmpl.existId(anyLong())).thenReturn(true);

        //Hacemos las validaciones.
        mockMvc.perform(put(url,news.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()); //Verificamos que el HttpStatus sea de OK.
    }
    @Test
    @WithMockUser(username = "user@email.com", authorities = {"ROLE_USER"}) //Cuenta de usuario registrado.
    public void tryCreateAValidNewsWithNonAdminUser() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news";
        // creo categyRequest para pasar a newDTO
        var cata = new CategoryRequest();
        cata.setName("Holaaa");
        cata.setImage("ur1");
        cata.setDescription("texto");
        //Creamos un newsDTO.
        NewsDTO newsDTO = new NewsDTO();

        //Le agregamos sus campos correspondientes.
        newsDTO.setName("Test News");
        newsDTO.setContent("This is the content for example");
        newsDTO.setImage("image.png");
        newsDTO.setCategoryRequest(cata);
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(newsDTO);
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
    public void tryCreateANewsWithoutName() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news";
        // creo categyRequest para pasar a newDTO
        var cata = new CategoryRequest();;
        cata.setName("Holaaa");
        cata.setImage("ur1");
        cata.setDescription("texto");
        //Creamos un NewsDto.
        NewsDTO newsDTO = new NewsDTO();
        //Le agregamos sus campos correspondientes faltando el de name.
        newsDTO.setContent("This is the content for example");
        newsDTO.setImage("image.png");
        newsDTO.setCategoryRequest(cata);
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(newsDTO);
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
    public void tryCreateANewsWithoutImage() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news";
        var cata = new CategoryRequest();;
        cata.setName("Holaaa");
        cata.setImage("ur1");
        cata.setDescription("texto");
        //Creamos un newsDTO.
        NewsDTO newsDTO = new NewsDTO();
        //Le agregamos sus campos correspondientes faltando el de imagen.
        newsDTO.setName("Test");
        newsDTO.setContent("This is the content for example");
        newsDTO.setCategoryRequest(cata);
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(newsDTO);
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
    public void tryCreateANewsWithoutContent() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news";
        //Creo Categoryreques
        var cata = new CategoryRequest();
        cata.setName("Holaaa");
        cata.setImage("ur1");
        cata.setDescription("texto");
        //Creamos un NewsDto.
        NewsDTO newsDTO = new NewsDTO();
        //Le agregamos sus campos correspondientes faltando el de content.
        newsDTO.setName("Test");
        newsDTO.setImage("image.png");
        newsDTO.setCategoryRequest(cata);
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(newsDTO);
        //Hacemos las validaciones.
        mockMvc.perform(post(url) //Apuntamos a la ruta de la variable url.
                .content(JSONRequest) //Enviando el JSON.
                .contentType(MediaType.APPLICATION_JSON)) //Definimos que el tipo de dato que enviamos es un JSON.
                .andDo(print())
                .andExpect(status().isBadRequest()) //Verificamos que el HttpStatus sea de BAD_REQUEST.
                .andReturn();
    }
    //EN ESTE AGREGUE UN NOT NULL A NEWSDTO PARA QUE PUEDA PASAR EL TEST Y LE PUSE UN MENSAJE.
    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de usuario registrado.
    public void tryCreateANewsWithoutCategoryRecues() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news";
        //Creamos un NewsDto.
        CategoryRequest cata= new CategoryRequest();
        NewsDTO newsDTO = new NewsDTO();
        //Le agregamos sus campos correspondientes faltando el de content.
        newsDTO.setName("Test");
        newsDTO.setImage("image.png");
        // newsDTO.setCategoryRequest(cata);
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });
        //Armamos una variable con el JSON Request.
        String JSONRequest = mapToJSON(newsDTO);
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
    public void tryCreateANullNews() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news";
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
    public void tryDeleteNewsWithNonAdminUser() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news/{id}";
        //Creo Categories
        var cata= new CategoryRequest();
        cata.setName("Holaaa");
        cata.setImage("ur1");
        cata.setDescription("texto");
        //Creamos un News.
        NewsDTO newsDTO = new NewsDTO();
        //Le agregamos sus campos correspondientes.
        newsDTO.setName("Test News");
        newsDTO.setContent("This is the content for example");
        newsDTO.setImage("image.png");
        newsDTO.setCategoryRequest(cata);
        //Guardamos en el service el News.
        News news = service.postNews(newsDTO);
        //Le indicamos al repository mock que debe retornar cuando le pidan el news.
        given(service.deleteNewsById(news.getId())).willReturn(news.toString());
        //Hacemos las validaciones.
        mockMvc.perform(delete(url,news.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden()); //Verificamos que el HttpStatus sea de Forbidden.
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void tryDeleteNewsThatDoesntExist() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news/{id}";
        //Creo Categories
        var cata= new Categories();
        cata.setId(15L);
        cata.setName("Holaaa");
        cata.setImage("ur1");
        cata.setDescription("texto");
        //Creamos un news.
        News news = new News();
        //Le agregamos sus campos correspondientes.
        news.setId(1L);
        news.setName("Test News");
        news.setContent("This is the content for example");
        news.setImage("image.png");
        news.setCategories(cata);

        given(service.deleteNewsById(news.getId())).willThrow(IllegalStateException.class);

        //Hacemos las validaciones.
        mockMvc.perform(delete(url,news.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound()); //Verificamos que el HttpStatus sea de BAD_REQUEST.
    }
    //          -        Pruebas al Update        -

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void tryUpdateNewsWithInvalidId() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news/{id}";
        //Creamos una variable con un ID invalido.
        Long invalidId = 0L;
        //Creamos la categoria que se va a almacenar en el DTO.
        CategoryRequest cata= new CategoryRequest();
        cata.setName("Holaaa");
        cata.setImage("ur1");
        cata.setDescription("texto");
        //Creamos el DTO que va a tener información a modificar al News.
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setName("New Test News 2.0");
        newsDTO.setContent("New content");
        newsDTO.setImage("newImage.png");
        newsDTO.setCategoryRequest(cata);
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });
        //Asignamos el string en el JSONRequest.
        String JSONRequest = mapToJSON(newsDTO);
        //Creamos la respuesta de parte del service.
        Mockito.when(servicelmpl.updateNews(newsDTO,invalidId)).thenThrow(IllegalStateException.class);

        //Hacemos las validaciones.
        mockMvc.perform(put(url,invalidId) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound()); //Verificamos que el HttpStatus sea de NotFound.
    }
    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"}) //Cuenta de Admin.
    public void tryUpdateNewsWithNullBody() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news/{id}";
        //Creamos un News.
        News news = new News();
        //Le agregamos sus campos correspondientes.
        news.setId(15L);
        news.setName("Test News");
        news.setContent("This is the content for example");
        news.setImage("image.png");
        //Asignamos el string en el JSONRequest.
        String JSONRequest = mapToJSON(null);
        //Creamos la respuesta de parte del service.
        given(service.updateNews(null,null))
                .willThrow(IllegalStateException.class);

        //Hacemos las validaciones.
        mockMvc.perform(put(url,news.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); //Verificamos que el HttpStatus sea de BadRequest.
    }
    @Test
    @WithMockUser(username = "user@email.com", authorities = {"ROLE_USER"}) //Cuenta de Admin.
    public void tryUpdateWithNonAdminUser() throws Exception {
        //Definimos la ruta a la cual vamos a estar haciendo el test.
        String url = "/news/{id}";
        //Creamos un News.
        News news = new News();
        //Creo CategoryRequest
        Categories cata= new Categories();
        cata.setId(15L);
        cata.setName("Holaaa");
        cata.setImage("ur1");
        //Le agregamos sus campos correspondientes.
        news.setId(15L);
        news.setName("Test News");
        news.setContent("This is the content for example");
        news.setImage("image.png");
        news.setCategories(cata);

        //Creamos el DTO que va a tener información a modificar al News.
        NewsDTO newsDTO = new NewsDTO();
        var cat = new CategoryRequest();
        cat.setName("Holaaa");
        cat.setImage("ur1");
        cat.setDescription("texto");
        newsDTO.setName("New Test News 2.0");
        newsDTO.setContent("New content");
        newsDTO.setImage("newImage.png");
        newsDTO.setCategoryRequest(cat);
        //Verificamos que el JSON que vamos a enviar se cree sin errores.
        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(newsDTO);
        });

        String JSONRequest = mapToJSON(newsDTO);

        //Hacemos las validaciones.
        mockMvc.perform(put(url,news.getId()) //Apuntamos a la ruta de la variable url y le pasamos el parametro ID.
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden()); //Verificamos que el HttpStatus sea de OK.

    }



}
