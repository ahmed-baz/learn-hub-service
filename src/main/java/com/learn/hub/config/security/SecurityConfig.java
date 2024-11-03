package com.learn.hub.config.security;


import com.learn.hub.enums.UserRoleEnum;
import com.learn.hub.filter.AuthFilter;
import com.learn.hub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;
    private final AuthFilter authFilter;

    private final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/v2/api-docs",
            "/configuration/ui", "/swagger-resources",
            "/configuration/security", "/swagger-ui.html",
            "/webjars/**", "/swagger-resources/configuration/ui"
    };

    private final String RESOURCE_COURSES_API = "/api/v1/courses";
    private final String COURSE_API = "/api/v1/courses/{id}";
    private final String REGISTER_COURSE_API = "/api/v1/courses/register";
    private final String UNREGISTER_COURSE_API = "/api/v1/courses/unregister";
    private final String SCHEDULE_COURSE_API = "/api/v1/courses/schedule";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(HttpMethod.GET, RESOURCE_COURSES_API).hasAnyAuthority(UserRoleEnum.STUDENT.name(), UserRoleEnum.INSTRUCTOR.name())
                                        .requestMatchers(HttpMethod.POST, REGISTER_COURSE_API).hasAnyAuthority(UserRoleEnum.STUDENT.name())
                                        .requestMatchers(HttpMethod.POST, UNREGISTER_COURSE_API).hasAnyAuthority(UserRoleEnum.STUDENT.name())
                                        .requestMatchers(HttpMethod.POST, RESOURCE_COURSES_API).hasAnyAuthority(UserRoleEnum.INSTRUCTOR.name())
                                        .requestMatchers(HttpMethod.PUT, COURSE_API).hasAnyAuthority(UserRoleEnum.INSTRUCTOR.name())
                                        .requestMatchers(HttpMethod.DELETE, COURSE_API).hasAnyAuthority(UserRoleEnum.INSTRUCTOR.name())
                                        .requestMatchers(HttpMethod.GET, COURSE_API).hasAnyAuthority(UserRoleEnum.STUDENT.name(), UserRoleEnum.INSTRUCTOR.name())
                                        .requestMatchers(HttpMethod.GET, SCHEDULE_COURSE_API).hasAuthority(UserRoleEnum.STUDENT.name())
                                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll().anyRequest().authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
