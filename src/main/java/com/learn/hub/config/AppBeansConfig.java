package com.learn.hub.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppBeansConfig {

    @Value("#{'${app.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("${tesla.oauth2.url}")
    private String serverUrl;
    @Value("${tesla.oauth2.username}")
    private String username;
    @Value("${tesla.oauth2.password}")
    private String password;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedHeaders(
                List.of(
                        HttpHeaders.ORIGIN,
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.ACCEPT)
        );
        config.setAllowedMethods(
                List.of(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.OPTIONS.name()
                )
        );
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public OpenAPI openAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:9999");
        devServer.setDescription("Server URL in the development environment");

        Server prodServer = new Server();
        prodServer.setUrl("http://localhost:9999");
        prodServer.setDescription("Server URL in the production environment");

        Contact contact = new Contact();
        contact.setEmail("developer.baz@gmail.com");
        contact.setName("Ahmed Baz");
        contact.setUrl("https://github.com/ahmed-baz");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        final String description = """
                This is a Spring Boot application that demonstrates how to build a RESTful service with CRUD operations,
                using the power of Spring Security for authentication and authorization, and use PostgreSQL for data persistence.
                The service manages a basic User with Role one of Student, Instructor, or an Admin, and Course entity with fields such as title,
                description,start date, and more.
                """;
        Info info = new Info()
                .title("learn-hub API")
                .version("1.0.0")
                .contact(contact)
                .description(description)
                .termsOfService("https://github.com/ahmed-baz/learn-hub-service/blob/master/README.md")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }

    @Bean
    Keycloak keycloak() {
        ResteasyClient resteasyClient = new ResteasyClientBuilderImpl().connectionPoolSize(10).build();
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId("admin-cli")
                .grantType(OAuth2Constants.PASSWORD)
                .username(username)
                .password(password)
                .resteasyClient(resteasyClient)
                .build();
    }
}
