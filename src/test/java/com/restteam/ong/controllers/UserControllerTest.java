package com.restteam.ong.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.UserDTO;
import com.restteam.ong.models.Role;
import com.restteam.ong.models.User;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.services.RoleService;
import com.restteam.ong.services.UserService;
import com.restteam.ong.services.impl.UserDetailsServiceImpl;
import com.restteam.ong.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    RoleService roleService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String mapToJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    Role roleAdmin = new Role();
    User userAdmin = new User();
    Role roleUser = new Role();
    User registeredUser = new User();
    UserDetailsImpl userDetailsUser;
    UserDetailsImpl userDetailsAdmin;
    ArrayList<User> users = new ArrayList<>();
    UserDTO userDTOOK;

    @BeforeEach
    void setUp() throws Exception {
        // Genero user details admin

        roleAdmin.setName("ROLE_ADMIN");
        userAdmin.setId(500L);
        userAdmin.setFirstName("test");
        userAdmin.setLastName("test");
        userAdmin.setEmail("userAdmin@email.com");
        userAdmin.setRole(roleAdmin);
        userDetailsAdmin = new UserDetailsImpl(userAdmin);

        // Genero user details user registrado
        roleUser.setName("ROLE_USER");
        registeredUser.setId(501L);
        registeredUser.setFirstName("test");
        registeredUser.setLastName("test");
        registeredUser.setEmail("registeredUser@email.com");
        registeredUser.setRole(roleUser);
        userDetailsUser = new UserDetailsImpl(registeredUser);

        // Genero un user dto para testear el update
        userDTOOK = new UserDTO();
        userDTOOK.setFirstName("TestDTO");
        userDTOOK.setLastName("TestDTO");
        userDTOOK.setEmail("testdto@email.com");
        userDTOOK.setPassword("testdto");
        userDTOOK.setPhoto("test");
        userDTOOK.setRole(roleUser);

        /* Aca mockeo el metodo userCanModify porque considero que es necesario ver su comportamiento
           real para testear el controller. Cuando se lo invoque, va a llamar al metodo real.
           Esto pasa debido a que la anotacion que uso --MockBean-- hace que los beans que son dependencias
           del controller tengan un comportamiento nulo.
        * */
        Mockito.when(userService.userCanModifyUserWithId(any(), any())).thenCallRealMethod();
        // Tambien, como estamos testeando el controller, solo se va a llamar a el delete
        // cuando la request sea valida, entonces puedo mockear ese metodo para que siempre
        // me retorne un true, es decir, como si la operacion de delete fuese siempre exitosa
        Mockito.when(userService.deleteUser(any())).thenReturn(Boolean.TRUE);

    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    void getUsersOkReturnsStatus200() throws Exception {
        String url = "/users";
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    @WithMockUser(username = "user@email.com", authorities = {"ROLE_USER"})
    void getUsersWithRoleUserReturnsStatus403() throws Exception {
        String url = "/users";
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void deleteUserWithRoleAdminReturnsStatus200() throws Exception {
        String url = "http://localhost:9800/users/{id}";

        //Aca anda porque estoy llamando con un user admin, notar que
        //el user desde el cual envio el id es diferente al de la url
        //de todas formas es admin y puede eliminar cualquier usuario
        mockMvc.perform(delete(url, 2)
                .with(user(userDetailsAdmin))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteUserWithRoleUserReturnsStatus403() throws Exception {

        String url = "http://localhost:9800/users/{id}";

        //Aca no va a dejar modificar porque voy a intentar eliminar
        // un user distinto al que hace el request pero que no es admin
        mockMvc.perform(delete(url, 1)
                .with(user(userDetailsUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void deleteUserWithRoleUserReturnsStatus200() throws Exception {
        String url = "http://localhost:9800/users/{id}";

        //Aca lo que quiero testear es que la operacion sea correcta
        //Dado que el usuario que va a ser eliminado, va a tener el
        // mismo id que el usuario que hace el request.

        mockMvc.perform(delete(url, 501)
                .with(user(userDetailsUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateUserWithRoleAdminReturnsStatus200() throws Exception {
        String url = "http://localhost:9800/users/{id}";

        //Aca anda porque estoy llamando con un user admin, notar que
        //el user desde el cual envio el id es diferente al de la url
        //de todas formas es admin y puede actualizar cualquier usuario

        Assertions.assertDoesNotThrow(
                () -> {
                    mapToJSON(userDTOOK);
                }
        );
        String JSONRequest = mapToJSON(userDTOOK);


        mockMvc.perform(patch(url, 2)
                .content(JSONRequest)
                .with(user(userDetailsAdmin))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateUserWithRoleUserReturnsStatus403() throws Exception {
        String url = "http://localhost:9800/users/{id}";
        //Aca no va a dejar modificar porque voy a intentar eliminar
        // un user distinto al que hace el request pero que no es admin

        Assertions.assertDoesNotThrow(
                () -> {
                    mapToJSON(userDTOOK);
                }
        );
        String JSONRequest = mapToJSON(userDTOOK);

        mockMvc.perform(patch(url, 1)
                .content(JSONRequest)
                .with(user(userDetailsUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void updateUserWithRoleUserReturnsStatus200() throws Exception {
        String url = "http://localhost:9800/users/{id}";

        //Aca lo que quiero testear es que la operacion sea correcta
        //Dado que el usuario que va a ser modificado, va a tener el
        // mismo id que el usuario que hace el request.

        Assertions.assertDoesNotThrow(
                () -> {
                    mapToJSON(userDTOOK);
                }
        );
        String JSONRequest = mapToJSON(userDTOOK);

        mockMvc.perform(patch(url, 501)
                .content(JSONRequest)
                .with(user(userDetailsUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateUserWithWrongContentReturnsStatus400() throws Exception {
        String url = "http://localhost:9800/users/{id}";

        //Aca yo quiero verificar que si le envio un content que no hace match
        //con lo que es requerido --un userDTO--, me responda con un bad request.

        Assertions.assertDoesNotThrow(
                () -> {
                    //Le mando un null, para que falle
                    mapToJSON(null);
                }
        );
        String JSONRequest = mapToJSON(null);

        mockMvc.perform(patch(url, 501)
                .content(JSONRequest)
                .with(user(userDetailsUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}