package com.homesharing.util;

import com.homesharing.exception.GeneralException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Utility class for password hashing and management.
 */
public class PasswordUtil {

    // Private constructor to prevent instantiation
    private PasswordUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Hashes the provided password using SHA-256 with a random salt.
     *
     * @param password The password to hash.
     * @return A string containing the salt and the hashed password, separated by a colon.
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

