package com.restteam.ong.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI creditLimitsApi() {
        return new OpenAPI()
                .info(new Info().title("ALKEMY - SOMOS MÁS").description("Pagina desarrollada para la ONG 'somos mas'")
                .contact(new Contact().email("somosfundacionmas@gmail.com").name("Alkemy - Grupo 48.")));
    }
}