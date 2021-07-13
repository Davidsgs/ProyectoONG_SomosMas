package com.restteam.ong.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.AuthenticationRequest;
import com.restteam.ong.controllers.dto.UserRegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String mapToJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAthenticationTokenOkReturns200() throws Exception {
        String url = "http://localhost:9800/auth/login";
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("userDeveloper@email.com");
        request.setPassword("qwerty");

        Assertions.assertDoesNotThrow(
                () -> {
                    mapToJSON(request);
                }
        );
        String JSONRequest = mapToJSON(request);

        mockMvc.perform(post(url)
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createAthenticationTokenWithNotRegisteredEmailReturns404() throws Exception {
        String url = "http://localhost:9800/auth/login";
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("userInvalid@email.com");
        request.setPassword("qwerty");

        Assertions.assertDoesNotThrow(
                () -> {
                    mapToJSON(request);
                }
        );
        String JSONRequest = mapToJSON(request);

        mockMvc.perform(post(url)
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void createAthenticationTokenWithInvalidPasswordReturns403() throws Exception {
        String url = "http://localhost:9800/auth/login";
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("userDeveloper@email.com");
        request.setPassword("notValid");

        Assertions.assertDoesNotThrow(
                () -> {
                    mapToJSON(request);
                }
        );
        String JSONRequest = mapToJSON(request);

        mockMvc.perform(post(url)
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void registerOkReturns200() throws Exception {
        String url = "http://localhost:9800/auth/register";
        UserRegisterRequest request = new UserRegisterRequest();
        request.setFirstName("test");
        request.setLastName("test");
        request.setEmail("test@email.com");
        request.setPassword("test");

        Assertions.assertDoesNotThrow(
                () -> {
                    mapToJSON(request);
                }
        );
        String JSONRequest = mapToJSON(request);

        mockMvc.perform(post(url)
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void registerWithBlankEmailReturnsStatus400() throws Exception {
        String url = "http://localhost:9800/auth/register";
        UserRegisterRequest request = new UserRegisterRequest();
        request.setFirstName("test");
        request.setLastName("test");
        //Blank email
        request.setEmail("");
        request.setPassword("test");

        Assertions.assertDoesNotThrow(
                () -> {
                    mapToJSON(request);
                }
        );
        String JSONRequest = mapToJSON(request);

        mockMvc.perform(post(url)
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
/*
    @Test
    void getUserInfo() {
    }
     */
}