package com.example.nada.Configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openApiConfigs(){
        return new OpenAPI().info(
                new Info().title("Modern Library")
                        .description("API de Biblioteca Moderna com IA ")
                        .contact(
                                new Contact().email("acuondala@gmail.com")
                        )
        );
    }
}
