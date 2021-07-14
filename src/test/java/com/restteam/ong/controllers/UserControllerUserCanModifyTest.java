package com.restteam.ong.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restteam.ong.models.Role;
import com.restteam.ong.models.User;
import com.restteam.ong.models.impl.UserDetailsImpl;
import com.restteam.ong.services.RoleService;
import com.restteam.ong.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

public class UserControllerUserCanModifyTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private final RoleService roleService = Mockito.mock(RoleService.class);

    @Mock
    private final UserService userService = Mockito.mock(UserService.class);

    @InjectMocks
    private UserController userController;


    private String jwt;

    @BeforeEach
    void setUp() throws Exception {
        //Inicializo los mocks del user controller
        MockitoAnnotations.openMocks(this);
        this.mockMvc =  MockMvcBuilders.standaloneSetup(userController).build();
        //Mockeo los metodos del user service
        //Primero creo algunos datos para mockear
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        User userAdmin = new User();
        userAdmin.setId(1L);
        userAdmin.setFirstName("test");
        userAdmin.setLastName("test");
        userAdmin.setPhoto("test");
        userAdmin.setEmail("test");
        userAdmin.setRole(roleAdmin);
        User registeredUser = new User();
        registeredUser.setId(2L);
        registeredUser.setFirstName("test");
        registeredUser.setLastName("test");
        registeredUser.setPhoto("test");
        registeredUser.setEmail("test1");
        registeredUser.setRole(roleUser);
        //Aca si pido el user con id 1 me trae un admin
        Mockito.when(userService.findById(1L)).thenReturn(userAdmin);
        //Aca si pido el user con id 2 me trae un user registrado
        Mockito.when(userService.findById(2L)).thenReturn(registeredUser);
        //Aca mockeo el findByName pero del roleservice
        Mockito.when(roleService.findByName("ROLE_ADMIN")).thenReturn(roleAdmin);
        //Aca mockeo el get para que retorne una lista de dos users
        ArrayList<User> userList = new ArrayList<>();
        userList.add(registeredUser);
        userList.add(userAdmin);
        Mockito.when(userService.findAll()).thenReturn(userList);
    }

    @Test
    void userCanModifyUserWithIdRoleAdminOkReturnsTrue() {
        //Aca retorna true porque de por si un administrador puede
        //modificar cualquier usuario
        User user = userService.findById(1L);
        //Creo el user details en base a ese user
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        //Esto deberia retornar true
        Assertions.assertTrue(userController.userCanModifyUserWithId(userDetails, 1L));
    }

    @Test
    void userCanModifyUserWithIdRoleUserReturnsTrue() {
        //Aca va a retornar true porque vamos a hacer que el user
        // modifique su propio usuario, esto esta permitido
        User user = userService.findById(2L);
        //Creo el user details en base a ese user
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        //Esto deberia retornar true
        Assertions.assertTrue(userController.userCanModifyUserWithId(userDetails, 2L));
    }

    @Test
    void userCanModifyUserOtherUserWithIdRoleUserReturnsTrue() {
        //Aca va a retornar false porque vamos a hacer que el user
        //modifique otro usuario, esto no esta permitido
        User user = userService.findById(2L);
        //Creo el user details en base a ese user
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        //Esto deberia retornar true
        Assertions.assertFalse(userController.userCanModifyUserWithId(userDetails, 1L));
    }
}
