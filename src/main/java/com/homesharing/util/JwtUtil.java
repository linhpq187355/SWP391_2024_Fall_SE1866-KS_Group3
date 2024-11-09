/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

/**
 * Utility class for generating and verifying JWTs (JSON Web Tokens).
 */
public class JwtUtil {

    private static final String SECRET_KEY = System.getenv("SECRET_KEY"); // Retrieve the secret key from environment variables
    private static final long EXPIRATION_TIME = 86400000; // Token expiration time (1 day)

    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    // Private constructor to prevent object instantiation
    private JwtUtil() {
        // No action needed
    }

    /**
     * Generates a JWT with the provided username, role, and email.
     *
     * @param username The username of the user.
     * @param role The role of the user.
     * @param email The email of the user.
     * @return The generated JWT as a String.
     */
    public static String generateToken(String username, String role, String email) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)     // Add "role" claim
                .withClaim("email", email)   // Add "email" claim
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    /**
     * Retrieves all claims from a verified JWT.
     *
     * @param token The JWT to verify and extract claims from.
     * @return A map of claims, or `null` if the token is invalid or expired.
     */
    public static Map<String, Claim> getAllClaims(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaims(); // Get all claims as a Map
        } catch (JWTVerificationException e) {
            // Invalid or expired token
            return null;
        }
    }

}
