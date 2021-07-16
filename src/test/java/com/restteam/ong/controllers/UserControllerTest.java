package com.restteam.ong.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.AuthenticationRequest;
import com.restteam.ong.controllers.dto.AuthenticationResponse;

import com.restteam.ong.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String mapToJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }


    private String jwt;

    @BeforeEach
    void setUp() throws Exception {
        //Genero un token valido para poder usarlo despues
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setPassword("qwerty");
        authRequest.setUsername("userDeveloper@email.com");

        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJSON(authRequest))
                .characterEncoding("utf-8"))
                .andReturn();

        AuthenticationResponse authenticationResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), AuthenticationResponse.class);
        jwt = authenticationResponse.getJwt();
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
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_USER"})
    void getUsersWithRoleUserReturnsStatus403() throws Exception {
        String url = "/users";
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    //TODO: TEST RESTANTES DE USER CONTORLLER
/*
    @Test
    void deleteUserWithRoleAdminReturnsStatus200() throws Exception {
        String url = "http://localhost:9800/users/{id}";

        given(userService.deleteUser(any())).willReturn(Boolean.TRUE);

        mockMvc.perform(delete(url, 50)
                .header("Authorization", String.format("Bearer %s", jwt))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
/*
    @Test
    void deleteUserWithRoleUserReturnsStatus403() {
    }

    @Test
    void deleteUserWithRoleUserWithSameIdReturnsStatus200() {
    }
*/

}