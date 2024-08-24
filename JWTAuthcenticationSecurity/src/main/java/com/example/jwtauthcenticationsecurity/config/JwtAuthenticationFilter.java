package com.example.jwtauthcenticationsecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// @RequiredArgsConstructor generates a constructor with 1 parameter for each field that requires special handling.
// All non-initialized final fields get a parameter, as well as any fields that are marked as @NonNull that aren't initialized where they are declared.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // You can use @NonNull on a record component, or a parameter of a method or constructor. This will cause to lombok generate a null-check statement for you.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Get the header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Get the jwt
        jwt = authHeader.substring(7);
    }
}
