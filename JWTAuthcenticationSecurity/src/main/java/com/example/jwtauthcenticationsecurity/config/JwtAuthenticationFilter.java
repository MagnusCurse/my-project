package com.example.jwtauthcenticationsecurity.config;

import com.example.jwtauthcenticationsecurity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    // You can use @NonNull on a record component, or a parameter of a method or constructor. This will cause to lombok generate a null-check statement for you.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Get the "Authorization" header from the HTTP request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Check if the Authorization header is null or does not start with "Bearer "
        // If so, pass the request to the next filter in the chain without doing anything
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header (skip the "Bearer " part)
        jwt = authHeader.substring(7);
        // Extract the user email from the JWT token using the jwtService
        userEmail = jwtService.extractUsername(jwt);

        // If the user email is not null and there is no authentication object in the SecurityContext
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the database using the email extracted from the JWT
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // Validate the JWT token with the user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Create an authentication token with user details and authorities (roles)
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // Credentials are not needed as JWT token is already verified
                                userDetails.getAuthorities()
                        );
                // Set additional authentication details like remote IP address, session ID, etc.
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Update the SecurityContext with the authenticated user
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continue with the next filter in the filter chain
        filterChain.doFilter(request, response);
    }
}
