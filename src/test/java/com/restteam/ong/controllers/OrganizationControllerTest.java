package com.restteam.ong.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.OrganizationCreateDTO;
import com.restteam.ong.controllers.dto.OrganizationDTO;
import com.restteam.ong.models.Organization;
import com.restteam.ong.services.OrganizationService;

import com.restteam.ong.services.SlideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {

    private MockMvc mockMvc;
    private ModelMapper modelMapper = new ModelMapper();

    @MockBean
    OrganizationService orgServMock;
    @MockBean
    SlideService slideService;
    @InjectMocks
    OrganizationController orgControllMock;

    ObjectMapper objectMapper = new ObjectMapper();

    // OrganizationEntity
    Organization orgEntity1;
    Organization orgEntity2;
    // DTO
    OrganizationCreateDTO orgCreateDTO1;
    OrganizationCreateDTO orgCreateDTO2;
    OrganizationDTO orgDTO1;
    OrganizationDTO orgDTO2;

    @BeforeEach
    public void setup() {

        orgCreateDTO1 = new OrganizationCreateDTO("name1", "image1", "address1", 123456789, "user1@email.com",
                "welcomeText", "aboutUsText", "http://facebookUrl.com", "http://instagramUrl.com",
                "http://linkedinUrl.com");

        orgCreateDTO2 = new OrganizationCreateDTO("name2", "image2", "address2", 123456789, "user2@email.com",
                "welcomeText", "aboutUsText", "http://facebookUrl.com", "http://instagramUrl.com",
                "http://linkedinUrl.com");

        orgDTO1= new OrganizationDTO("name11", "image11", 789456123, "address11", "http://facebookUrl11.com", 
                "http://instagramUrl11.com", "http://linkedingUrl11.com", null);

        orgDTO2= new OrganizationDTO("name22", "image22", 789456123, "address22", "http://facebookUrl22.com", 
                "http://instagramUrl22.com", "http://linkedingUrl22.com", null);

        orgEntity1 = new Organization();
        orgEntity2 = new Organization();

        this.modelMapper.map(orgCreateDTO1, orgEntity1);
        this.modelMapper.map(orgCreateDTO2, orgEntity2);
        this.orgEntity1.setId(1L);
        this.orgEntity2.setId(2L);
        this.orgEntity1.setDeleted(false);
        this.orgEntity2.setDeleted(false);
        this.orgEntity1.setCreatedAt(new Date().getTime());
        this.orgEntity2.setCreatedAt(new Date().getTime());
        this.orgEntity1.setUpdatedAt(this.orgEntity1.getCreatedAt());
        this.orgEntity2.setUpdatedAt(this.orgEntity2.getCreatedAt());

        List<Organization> orgAll = new ArrayList<>();
        orgAll.add(orgEntity1);
        orgAll.add(orgEntity2);

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orgControllMock).build();
        // Mockito.when(orgServMock.getAll()).thenReturn(orgAll);
        Mockito.when(orgServMock.create(orgCreateDTO1)).thenReturn(1L);
        Mockito.when(orgServMock.create(orgCreateDTO2)).thenReturn(2L);
        Mockito.when(orgServMock.update(orgDTO1, 1L)).thenReturn(1L);
        Mockito.when(orgServMock.update(orgDTO2, 2L)).thenReturn(2L);
        Mockito.when(orgServMock.getDetail(1L)).thenReturn(orgDTO1);
        Mockito.when(orgServMock.getDetail(2L)).thenReturn(orgDTO2);

    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = { "ROLE_ADMIN" })
    public void create() throws JsonProcessingException, Exception {
        mockMvc.perform(post("/organization/public").content(objectMapper.writeValueAsString(orgCreateDTO1))
                .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = { "ROLE_ADMIN" })
    public void update() throws Exception{
        mockMvc.perform(post("/organization/public/1").content(objectMapper.writeValueAsString(orgDTO1))
        .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isOk()).andReturn();

    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = { "ROLE_ADMIN" })
    public void getDetail() throws Exception {
        mockMvc.perform(get("/organization/public/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
    }

   }
