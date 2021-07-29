package com.restteam.ong.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restteam.ong.controllers.dto.AuthenticationRequest;
import com.restteam.ong.controllers.dto.AuthenticationResponse;
import com.restteam.ong.controllers.dto.UserRegisterRequest;

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

	private String jwt;

	@BeforeEach
	void setUp() throws Exception {
		// Genero un token valido para poder usarlo despues
		AuthenticationRequest authRequest = new AuthenticationRequest();
		authRequest.setPassword("qwerty");
		authRequest.setUsername("adminUser1@email.com");

		MvcResult result = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(mapToJSON(authRequest)).characterEncoding("utf-8")).andReturn();

		AuthenticationResponse authenticationResponse = objectMapper.readValue(result.getResponse().getContentAsByteArray(), AuthenticationResponse.class);
		jwt = authenticationResponse.getJwt();
		// Elimino el usuario con el que hago pruebas
	}

	@Test
	void createAthenticationTokenOkReturns200() throws Exception {
		String url = "http://localhost:9800/auth/login";
		AuthenticationRequest request = new AuthenticationRequest();
		request.setUsername("adminUser1@email.com");
		request.setPassword("qwerty");

		Assertions.assertDoesNotThrow(() -> {
			mapToJSON(request);
		});
		String JSONRequest = mapToJSON(request);

		mockMvc.perform(post(url).content(JSONRequest).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	void createAthenticationTokenWithNotRegisteredEmailReturns404() throws Exception {
		String url = "http://localhost:9800/auth/login";
		AuthenticationRequest request = new AuthenticationRequest();
		// Aca seteo el email que no existe
		// El unico mail existente en la bd es: "userDeveloper@email.com"
		request.setUsername("userInvalid@email.com");
		request.setPassword("qwerty");

		Assertions.assertDoesNotThrow(() -> {
			mapToJSON(request);
		});
		String JSONRequest = mapToJSON(request);

		mockMvc.perform(post(url).content(JSONRequest).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	void createAthenticationTokenWithInvalidPasswordReturns403() throws Exception {
		String url = "http://localhost:9800/auth/login";
		AuthenticationRequest request = new AuthenticationRequest();
		request.setUsername("adminUser1@email.com");
		// Aca seteo password no valida, la correcta es : "qwerty"
		request.setPassword("notValid");

		Assertions.assertDoesNotThrow(() -> {
			mapToJSON(request);
		});
		String JSONRequest = mapToJSON(request);

		mockMvc.perform(post(url).content(JSONRequest).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isForbidden()).andReturn();
	}

	@Test
    void registerReturns207() throws Exception {
        //Genero un user con un string random para asegurarme de que no esta en la base ede datos
        String url = "http://localhost:9800/auth/register";
        UserRegisterRequest request = new UserRegisterRequest();
        String generatedString = RandomStringUtils.randomAlphabetic(5) + new Date().getTime();
        request.setFirstName(generatedString);
        request.setLastName(generatedString);
        request.setEmail(String.format("%s@email.com",generatedString));
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
                .andExpect(status().isMultiStatus()) //Esperamos 207 ya que el Mail de bienvenida no se va a enviar correctamente la dirección del usuario de pruebas.
                .andReturn();
    }

	@Test
	void registerWithBlankEmailReturnsStatus400() throws Exception {
		String url = "http://localhost:9800/auth/register";
		UserRegisterRequest request = new UserRegisterRequest();
		request.setFirstName("test");
		request.setLastName("test");
		// Blank email
		request.setEmail("");
		request.setPassword("test");

		Assertions.assertDoesNotThrow(() -> {
			mapToJSON(request);
		});
		String JSONRequest = mapToJSON(request);

		mockMvc.perform(post(url).content(JSONRequest).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	void getUserInfoOkReturnsStatus200() throws Exception {
		String url = "http://localhost:9800/auth/me";
		mockMvc.perform(get(url).header("Authorization", String.format("Bearer %s", jwt)).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@Test
	void getUserInfoWithoutAuthorizationReturnsStatus403() throws Exception {
		String url = "http://localhost:9800/auth/me";
		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isForbidden()).andReturn();
	}
}