package edu.azati.marketservice.config.swagger;

import edu.azati.marketservice.util.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiSecurityConfig {


    @Value("${keycloak.auth-server-url}")
    String authServerUrl;
    @Value("${keycloak.realm}")
    String realm;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components()
                        .addSecuritySchemes(Constants.OAUTH_SCHEME_NAME, createOAuthScheme()))
                .addSecurityItem(new SecurityRequirement().addList(Constants.OAUTH_SCHEME_NAME))
                .info(new Info().title("Market-Service backend")
                        .description("Backend part of team project")
                        .version("0.0.1"));
    }

    private SecurityScheme createOAuthScheme() {
        OAuthFlows flows = createOAuthFlows();
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createOAuthFlows() {
        OAuthFlow flow = createAuthorizationCodeFlow();
        return new OAuthFlows().implicit(flow);
    }

    private OAuthFlow createAuthorizationCodeFlow() {
        String protocolUrl = String.format("%s/realms/%s/protocol/openid-connect", authServerUrl, realm);

        return new OAuthFlow()
                .authorizationUrl(String.format("%s/auth", protocolUrl))
                .tokenUrl(String.format("%s/token", protocolUrl))
                .scopes(new Scopes());
    }
}
