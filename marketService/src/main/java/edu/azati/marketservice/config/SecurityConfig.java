package edu.azati.marketservice.config;

import edu.azati.marketservice.handler.KeycloakLogoutHandler;
import edu.azati.marketservice.model.enums.Role;
import edu.azati.marketservice.util.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final KeycloakLogoutHandler keycloakLogoutHandler;

    SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth

                .requestMatchers(antMatcher(Constants.ADDRESSES_PATH))
                .authenticated()

                .requestMatchers(antMatcher(HttpMethod.POST, Constants.CATEGORIES_PATH))
                .hasRole(Role.ADMIN.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.PATCH, Constants.CATEGORIES_PATH))
                .hasRole(Role.ADMIN.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.DELETE, Constants.CATEGORIES_PATH))
                .hasRole(Role.ADMIN.getAuthority())

                .requestMatchers(antMatcher(HttpMethod.POST, Constants.PAYMENTS_PATH))
                .hasRole(Role.CUSTOMER.getAuthority())

                .requestMatchers(antMatcher(HttpMethod.POST, Constants.PDF_ORDER_CONVERTER_PATH))
                .hasRole(Role.CUSTOMER.getAuthority())

                .requestMatchers(antMatcher(HttpMethod.POST, Constants.PRODUCTS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.SELLER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.PATCH, Constants.PRODUCTS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.SELLER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.DELETE, Constants.PRODUCTS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.SELLER.getAuthority())

                .requestMatchers(antMatcher(HttpMethod.POST, Constants.PRODUCT_ORDERS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.PATCH, Constants.PRODUCT_ORDERS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.DELETE, Constants.PRODUCT_ORDERS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())

                .requestMatchers(antMatcher(HttpMethod.POST, Constants.PRODUCT_REVIEWS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.PATCH, Constants.PRODUCT_REVIEWS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.DELETE, Constants.PRODUCT_REVIEWS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())

                .requestMatchers(antMatcher(HttpMethod.POST, Constants.SERVICE_ORDERS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.PATCH, Constants.SERVICE_ORDERS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.DELETE, Constants.SERVICE_ORDERS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())

                .requestMatchers(antMatcher(HttpMethod.POST, Constants.SERVICE_REVIEWS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.PATCH, Constants.SERVICE_REVIEWS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.DELETE, Constants.SERVICE_REVIEWS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority(), Role.CUSTOMER.getAuthority())

                .requestMatchers(antMatcher(HttpMethod.PATCH, Constants.USERS_PATH))
                .authenticated()
                .requestMatchers(antMatcher(HttpMethod.DELETE, Constants.USERS_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority())
                .requestMatchers(antMatcher(HttpMethod.POST, Constants.USERS_PATH))
                .permitAll()

                .requestMatchers(antMatcher(Constants.ROLES_PATH))
                .hasAnyRole(Role.ADMIN.getAuthority())

                .requestMatchers(Constants.SWAGGER_WHITELIST).permitAll()

                .requestMatchers(antMatcher(Constants.DEFAULT_PATH))
                .permitAll()
                .anyRequest()
                .authenticated()
        );

        http.csrf(AbstractHttpConfigurer::disable);

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults()));

        http.oauth2Login(Customizer.withDefaults())
                .logout(logout -> {
                    logout.clearAuthentication(true);
                    logout.deleteCookies("JSESSIONID", "remember-me");
                    logout.invalidateHttpSession(true);
                    logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl(Constants.DEFAULT_PATH);
                });
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
