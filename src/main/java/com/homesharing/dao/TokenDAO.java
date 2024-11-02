/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.dao;

import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * TokenDAO is an interface that defines the operations for managing tokens in the system.
 * It provides methods to insert, find, and update token information related to email verification.
 *
 * @version 1.0
 * @since 2024-10-02
 * @author ManhNC
 */
public interface TokenDAO {

    /**
     * Inserts a token into the database.
     *
     * @param token the token to be inserted, must not be null.
     * @throws GeneralException if an error occurs while inserting the token.
     * @throws SQLException if a database error occurs during the insertion.
     */
    void insertToken(Token token) throws SQLException;

    /**
     * Finds a token in the database by user ID.
     *
     * @param userId the user ID associated with the token
     * @return the {@link Token} object if found, or {@code null} if no token exists for the given user ID
     * @throws GeneralException if an error occurs while finding the token in the database.
     * @throws SQLException if there is an error accessing the database.
     */
    Token findToken(int userId) throws SQLException, GeneralException;

    /**
     * Updates the token verification status for a user.
     * This method marks the token as verified after a successful email verification.
     *
     * @param userId The ID of the user whose token verification status is being updated.
     */
    void updateTokenVerification(int userId) throws SQLException;

    /**
     * Updates the token code and time for a user.
     * This method updates the token code and requested time instead of creating a new token.
     *
     * @param userId The ID of the user whose token verification status is being updated.
     * @param otp The new token code to be set for the user.
     * @param time The new requested time for the token.
     * @throws SQLException If a database error occurs during the update operation.
     * @throws GeneralException If the update operation fails due to other reasons.
     */
    void updateToken(int userId, String otp, LocalDateTime time) throws SQLException;
}
