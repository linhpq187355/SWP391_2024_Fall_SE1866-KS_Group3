package com.homesharing.util;

import java.security.SecureRandom;

/**
 * Utility class for generating secure random verification codes.
 * This class provides a method to generate a random code consisting
 * of alphanumeric characters.
 */
public class SecureRandomCode {

    // Private constructor to prevent instantiation
    private SecureRandomCode() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // Valid characters for generating the code
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6; // Length of the verification code

    /**
     * Generates a random verification code.
     *
     * @return A string representing the generated verification code,
     *         consisting of alphanumeric characters.
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
