package edu.azati.marketservice.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Market-Service backend",
                description = "Backend part of team project", version = "0.0.1",
                contact = @Contact(
                        name = "Stoma Mikhail",
                        email = "mikhail.stoma@azati.com"
                )
        )
)
public class OpenApiConfig {
}
