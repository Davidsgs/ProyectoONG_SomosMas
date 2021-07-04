package com.restteam.ong.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private final String swaggerDescription = "API desarrollada por el rest team, de Alkemy.\n\n" +
            "para probar los endpoints, va a ser necesario que te autentices, pasos detallados a continuacion:\n\n" +
            "1 - Dirigirse al endpoint authentication-controller\n\n" +
            "2 - en auth/login, realizar el post con los siguientes datos:\n\n" +
            "user: userDeveloper@email.com\n\n" +
            "password: qwerty\n\n" +
            "3 - El response body deberia devolvernos un JWT, debemos copiarlo.\n\n" +
            "4 - Ir a la parte superior de la pagina y darle al boton 'authorize'\n\n" +
            "5 - Pegar el codigo y darle a authorize y listo, todos los endpoints deberian andar! :)";

    @Bean
    public OpenAPI creditLimitsApi() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info().title("ALKEMY - SOMOS MÁS").description(swaggerDescription)
                .contact(new Contact().email("somosfundacionmas@gmail.com").name("Somos más - ONG.")));
    }


}