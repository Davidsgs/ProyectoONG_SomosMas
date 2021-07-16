package com.restteam.ong.controllers;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.ActivityRequest;
import com.restteam.ong.models.Activity;
import com.restteam.ong.repositories.ActivityRepository;
import com.restteam.ong.services.ActivityService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class ActivityControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ActivityRepository repository;

    @MockBean
    private ActivityService service;

    @InjectMocks
    private ActivityController controller;

    private String mapToJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void testCreateActivity() throws Exception {

        String url = "/activities";

        ActivityRequest activityDto = new ActivityRequest();

        activityDto.setName("actividad");
        activityDto.setContent("esto es una actividad");
        activityDto.setImage("url");

        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(activityDto);
        });

        String JSONRequest = mapToJSON(activityDto);

        mockMvc.perform(post(url)
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void testUpdateActivity() throws Exception {

        String url = "/activities/{id}";

        Activity activity = new Activity();

        activity.setId(1L);
        activity.setName("activity");
        activity.setContent("esto es una actividad");
        activity.setImage("url");
        activity.setUpdatedAt(new Date().getTime());
        activity.setCreatedAt(new Date().getTime());
        activity.setDeleted(false);

        ActivityRequest activityDto = new ActivityRequest();

        activityDto.setName(" nueva actividad");
        activityDto.setContent("esto es una nueva actividad");
        activityDto.setImage("url 2.0");



        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(activityDto);
        });

        String JSONRequest = mapToJSON(activityDto);


        mockMvc.perform(put(url,activity.getId())
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@email.com", authorities = {"ROLE_USER"}) //Cuenta de usuario registrado.
    public void tryCreateAValidActivityWithNonAdminUser() throws Exception {

        String url = "/activities";

        ActivityRequest activityRequest = new ActivityRequest();

        activityRequest.setName("actividad test");
        activityRequest.setContent("esto es una actividad test");
        activityRequest.setImage("image.png");

        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(activityRequest);
        });

        String JSONRequest = mapToJSON(activityRequest);

        mockMvc.perform(post(url) //
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }
    @Test
    @WithMockUser(username = "user@email.com", authorities = {"ROLE_USER"})
    public void tryDeleteActivityWithNonAdminUser() throws Exception {

        String url = "/activities/{id}";

        Activity activity = new Activity();

        activity .setId(15L);
        activity .setName("Test Testimonial");
        activity .setContent("This is the content for example");
        activity .setImage("image.png");

        service.saveActivity(activity);

        given(service.getActivityById(activity.getId())).willReturn(activity);

        Assertions.assertEquals(service.getActivityById(activity.getId()),activity);

        mockMvc.perform(delete(url,activity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}