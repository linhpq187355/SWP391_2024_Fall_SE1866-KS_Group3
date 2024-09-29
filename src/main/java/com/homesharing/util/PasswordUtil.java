package com.homesharing.util;

import com.homesharing.exception.GeneralException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for handling password-related operations.
 * This class provides methods for hashing passwords using SHA-256.
 */
public class PasswordUtil {

    // Private constructor to prevent instantiation
    private PasswordUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Hashes a given password using the SHA-256 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password represented as a hexadecimal string.
     * @throws GeneralException If there is an error while hashing the password.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new GeneralException("Error while hashing password", e);
        }
    }
}

