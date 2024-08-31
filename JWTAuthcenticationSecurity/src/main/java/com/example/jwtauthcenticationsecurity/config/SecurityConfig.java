package com.example.jwtauthcenticationsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    /**
     *  This code defines a SecurityFilterChain configuration method for Spring Security,
     *  which is typically used to configure the security policies of a web application
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection as we are using JWT for authentication
                .authorizeHttpRequests(authorize -> authorize
                        // Allow public access to authentication endpoints
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Require authentication for all other requests.
                        // This means that any request not matching the public paths must have a valid authentication token.
                        // The authentication object must be set in the SecurityContext, which is managed by Spring Security.
                        // If no valid authentication is found in the SecurityContext, the request will be denied.
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session, no session is stored on the server side
                )
                // Specify custom authentication provider (e.g., JWT-based authentication)
                .authenticationProvider(authenticationProvider)
                // Add JWT authentication filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
