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

import com.homesharing.model.Token;

import java.sql.SQLException;

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
     * Inserts a new token into the database.
     * This token is associated with a user and is used for email verification.
     *
     * @param token The Token object to be inserted.
     */
    void insertToken(Token token) throws SQLException;

    /**
     * Finds and retrieves a token based on the user ID.
     * The token is used to verify the user's email address.
     *
     * @param userId The ID of the user whose token needs to be retrieved.
     * @return The Token object associated with the given user ID.
     */
    Token findToken(int userId) throws SQLException;

    /**
     * Updates the token verification status for a user.
     * This method marks the token as verified after a successful email verification.
     *
     * @param userId The ID of the user whose token verification status is being updated.
     */
    void updateTokenVerification(int userId) throws SQLException;
}
