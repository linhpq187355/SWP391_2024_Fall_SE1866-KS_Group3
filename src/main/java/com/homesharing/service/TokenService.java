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
     * Sends a verification token to the user's email address.
     * The token is used to verify the user's email as part of the registration or email verification process.
     *
     * @param email  The email address to which the token should be sent.
     * @param userId The ID of the user to whom the token belongs.
     */
    void sendToken(String email, int userId) throws SQLException;

    /**
     * ReSends a verification token to the user's email address.
     * The token is used to verify the user's email as part of the registration or email verification process.
     *
    * @param userId The ID of the user to whom the token belongs.
     */
    void reSendToken(int userId) throws SQLException;

}
