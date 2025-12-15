package com.example.nada.Configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openApiConfigs(){

        SecurityScheme securityScheme= new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT"); // Indica ao Swagger que é token JWT
        // Aplica globalmente a necessidade de autenticação
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");
        return new OpenAPI().info(
                new Info().title("Modern Library")
                        .description("API de Biblioteca Moderna com IA ")
                        .contact(
                                new Contact().email("acuondala@gmail.com")
                        )
        ).addSecurityItem(securityRequirement)
                .components(
                        new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth",securityScheme));
    }
}
