package com.example.nada.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.annotations.OpenAPI31;

@OpenAPIDefinition(
        info=@Info(
                title="Library",
                version = "v1_1.0.0",
                description="Library API",
                contact=@Contact(
                        email="acuondala@gmail.com",
                        name="acuondala@gmail.com"
                )
        )
)
public class SwaggerConfig {
}
