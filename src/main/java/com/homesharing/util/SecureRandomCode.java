/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.util;

import java.security.SecureRandom;

/**
 * Utility class for generating secure random verification codes in the Home Sharing System.
 * This class provides a method to create a random verification code using secure random generation.
 * All methods are static, and the class cannot be instantiated.
 *
 * @version 1.0
 * @since 2024-09-18
 * @author ManhNC
 */
public class SecureRandomCode {

    // Private constructor to prevent instantiation
    private SecureRandomCode() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Valid characters for generating the verification code.
     */
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Length of the verification code.
     */
    private static final int CODE_LENGTH = 6; // Length of the verification code

    /**
     * Generates a secure random verification code.
     *
     * @return a randomly generated verification code as a string
     */
    public static String generateCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }

        return code.toString();
    }
}
