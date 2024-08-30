package com.example.jwtauthcenticationsecurity.service;

import com.example.jwtauthcenticationsecurity.auth.AuthenticationRequest;
import com.example.jwtauthcenticationsecurity.auth.AuthenticationResponse;
import com.example.jwtauthcenticationsecurity.auth.RegisterRequest;
import com.example.jwtauthcenticationsecurity.entity.Role;
import com.example.jwtauthcenticationsecurity.entity.User;
import com.example.jwtauthcenticationsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // This annotation generates a constructor for all final fields, simplifying dependency injection
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder; // PasswordEncoder bean for encoding passwords securely
    private final JwtService jwtService; // JwtService for generating JWT tokens
    private final UserRepository userRepository; // Repository for accessing and managing user data
    private final AuthenticationManager authenticationManager; // AuthenticationManager for handling authentication requests

    /**
     * Registers a new user with the provided registration details.
     *
     * @param request the registration request containing user details (first name, last name, email, password)
     * @return an AuthenticationResponse containing a JWT token for the newly registered user
     */
    public AuthenticationResponse register(RegisterRequest request) {
        // Create a new User object using the details provided in the registration request
        User user = User.builder()
                .firstName(request.getFirstName()) // Set the user's first name
                .lastName(request.getLastName()) // Set the user's last name
                .email(request.getEmail()) // Set the user's email
                .password(passwordEncoder.encode(request.getPassword())) // Encode the user's password for secure storage
                .role(Role.USER) // Set the user's role to 'USER'
                .build(); // Build the User object

        // Save the new user to the database using the userRepository
        userRepository.save(user);

        // Generate a JWT token for the newly registered user using the jwtService
        String jwtToken = jwtService.generateToken(user);

        // Return an AuthenticationResponse containing the generated JWT token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user with the provided authentication request.
     *
     * @param request the authentication request containing user credentials (email and password)
     * @return an AuthenticationResponse containing a JWT token for the authenticated user
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Attempt to authenticate the user using the provided email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), // Principal: the user's email
                        request.getPassword() // Credentials: the user's password
                )
        );

        // If authentication is successful, retrieve the user from the database
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(); // Throw an exception if the user is not found

        // Generate a JWT token for the authenticated user using the jwtService
        String jwtToken = jwtService.generateToken(user);

        // Return an AuthenticationResponse containing the generated JWT token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
