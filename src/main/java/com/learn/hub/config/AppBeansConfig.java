package com.learn.hub.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppBeansConfig {

    private final UserDetailsService userService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
}
