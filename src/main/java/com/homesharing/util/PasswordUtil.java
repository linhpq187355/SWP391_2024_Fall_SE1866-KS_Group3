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

import com.homesharing.exception.GeneralException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Utility class for password hashing and management in the Home Sharing System.
 * This class provides methods for securely hashing passwords using SHA-256.
 * All methods are static, and the class cannot be instantiated.
 *
 * @version 1.0
 * @since 2024-09-18
 * @author ManhNC
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

