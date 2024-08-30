package com.example.jwtauthcenticationsecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public static final String SECRET_KEY = "527052423849336f497761717263546265747741435363365a4f516a324c6d62";

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        // `Claims::getExpiration` is a method reference that refers to the `getExpiration()` method in the `Claims` class
        return extractClaim(token, Claims::getExpiration);
    }


    public <R> R extractClaim(String token, Function<Claims, R> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Generate a token from the UserDetail itself
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails
                                ) {
        return Jwts.builder()
                // Set custom claims provided in the extractClaims map
                .setClaims(extractClaims)
                // Set the subject of the JWT, typically the username of the user
                .setSubject(userDetails.getUsername())
                // Set the issued at date to the current date and time
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Set the expiration date for the token to 24 minutes from now
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                // Sign the JWT using the specified secret key and HS256 algorithm
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                // Compact the JWT to a URL-safe string
                .compact();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
