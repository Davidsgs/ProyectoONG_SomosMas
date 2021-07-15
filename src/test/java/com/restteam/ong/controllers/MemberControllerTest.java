package com.restteam.ong.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.models.Member;
import com.restteam.ong.repositories.MemberRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    Member memberMock1;

    Member memberMockWithoutName;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        memberMock1 = new Member();
        memberMock1.setId(1l);
        memberMock1.setName("member1");
        memberMock1.setFacebookUrl("htt´://facebook.member1");
        memberMock1.setInstagramUrl("htt´://instagram.member1");
        memberMock1.setLinkedinUrl("htt´://linkedin.member1");
        memberMock1.setImage("imagenMember1");
        memberMock1.setDescription("descripcionMember1");
        memberMock1.setDeleted(false);
        memberMock1.setCreatedAt(null);
        memberMock1.setUpdatedAt(20210709L);

        memberMockWithoutName = new Member();
        memberMockWithoutName.setName("");
        memberMockWithoutName.setFacebookUrl("htt´://facebook.member1");
        memberMockWithoutName.setInstagramUrl("htt´://instagram.member1");
        memberMockWithoutName.setLinkedinUrl("htt´://linkedin.member1");
        memberMockWithoutName.setImage("imagenMember1");
        memberMockWithoutName.setDescription("descripcionMember1");
        memberMockWithoutName.setDeleted(false);
        memberMockWithoutName.setCreatedAt(null);
        memberMockWithoutName.setUpdatedAt(20210709L);
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void add_member_ok() throws Exception {

        String url = "/members";

        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(memberMock1);
        });

        String JSONRequest = mapToJSON(memberMock1);

        mockMvc.perform(post(url)
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void add_member_bad_request() throws Exception {

        String url = "/members";

        Assertions.assertDoesNotThrow(() -> {
            mapToJSON(memberMockWithoutName);
        });

        String JSONRequest = mapToJSON(memberMockWithoutName);

        mockMvc.perform(post(url)
                .content(JSONRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void get_member_ok() throws Exception {

        String url = "/members";

        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void update_member_ok() throws Exception {

        String url = "/members/{id}";

        MultiValueMap<String, String> params =  new LinkedMultiValueMap<>();

        params.add("name", memberMock1.getName());
        params.add("facebook",memberMock1.getFacebookUrl());
        params.add("instagram",memberMock1.getInstagramUrl());
        params.add("linkedin",memberMock1.getLinkedinUrl());
        params.add("image",memberMock1.getImage());
        params.add("description",memberMock1.getDescription());

        given(memberRepository.findById(memberMock1.getId())).willReturn(Optional.of(memberMock1));

        mockMvc.perform(put(url,memberMock1.getId()).queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void update_member_bad_request() throws Exception {

        String url = "/members/{id}";

        MultiValueMap<String, String> params =  new LinkedMultiValueMap<>();

        params.add("name", memberMock1.getName());
        params.add("facebook",memberMock1.getFacebookUrl());
        params.add("instagram",memberMock1.getInstagramUrl());
        params.add("linkedin",memberMock1.getLinkedinUrl());
        params.add("image",memberMock1.getImage());
        params.add("description",memberMock1.getDescription());

        given(memberRepository.findById(memberMock1.getId())).willReturn(Optional.empty());

        mockMvc.perform(put(url,memberMock1.getId()).queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void delete_member_ok() throws Exception {

        String url = "/members/{id}";

        given(memberRepository.existsById(memberMock1.getId())).willReturn(Boolean.TRUE);

        mockMvc.perform(delete(url,memberMock1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "userDeveloper@email.com", authorities = {"ROLE_ADMIN"})
    public void delete_member_bad_request() throws Exception {

        String url = "/members/{id}";

        given(memberRepository.existsById(memberMock1.getId())).willReturn(Boolean.FALSE);

        mockMvc.perform(delete(url,memberMock1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private String mapToJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
