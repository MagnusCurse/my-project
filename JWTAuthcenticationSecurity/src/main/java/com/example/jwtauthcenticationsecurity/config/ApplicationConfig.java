package com.example.jwtauthcenticationsecurity.config;

import com.example.jwtauthcenticationsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        // Go to the database finding weather the user exist
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Define a bean for password encoding using BCrypt
    // This bean will be used to encode and decode user passwords securely
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder is a strong one-way encryption algorithm
    }

    // Define a bean for the AuthenticationProvider
    // This provider uses a UserDetailsService to retrieve user details and a PasswordEncoder to validate passwords
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); // DaoAuthenticationProvider is a standard implementation of AuthenticationProvider
        authenticationProvider.setUserDetailsService(userDetailsService()); // Set the UserDetailsService for retrieving user details
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Set the PasswordEncoder for password hashing
        return authenticationProvider;
    }

    // Define a bean for AuthenticationManager
    // This manager is the main entry point for authentication, managing authentication requests
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Retrieve the default AuthenticationManager from the AuthenticationConfiguration
    }
}
