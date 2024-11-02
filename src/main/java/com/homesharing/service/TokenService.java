/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.service;

import com.homesharing.exception.GeneralException;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * TokenService is an interface that defines the operations for handling tokens,
 * which are primarily used for email verification in the system.
 * It provides methods for checking the validity of tokens and sending tokens to users.
 *
 * @version 1.0
 * @since 2024-10-02
 * @author ManhNC
 */
public interface TokenService {

    /**
     * Checks the validity of a token associated with a specific user.
     * The token is used to verify a user's email address.
     *
     * @param token  The verification token to be checked.
     * @param userId The ID of the user to whom the token belongs.
     * @return true if the token is valid, false otherwise.
     */
    boolean checkToken(String token, int userId, LocalDateTime requestedTime) throws SQLException;

    /**
     * Sends a verification email containing a token to the specified user.
     * Generates a new token or updates an existing one, then sends an email
     * with the token to the user's email address.
     *
     * @param email  The user's email address. Should not be null or empty.
     * @param userId The ID of the user.
     * @throws SQLException If a database error occurs during token operations.
     * @throws GeneralException If sending the email fails.
     */
    void sendToken(String email, int userId) throws SQLException, GeneralException;

    /**
     * ReSends a verification token to the user's email address.
     * The token is used to verify the user's email as part of the registration or email verification process.
     *
    * @param userId The ID of the user to whom the token belongs.
     */
    void reSendToken(int userId) throws SQLException;

}
