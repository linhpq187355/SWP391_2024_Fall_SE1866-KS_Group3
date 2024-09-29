package com.homesharing.dao;

import com.homesharing.model.Token;

/**
 * TokenDAO interface defines methods for handling token-related database operations.
 */
public interface TokenDAO {

    /**
     * Inserts a new token into the database.
     *
     * @param token The Token object to be inserted into the database.
     */
    void insertToken(Token token);

    /**
     * Finds a token associated with the given user ID.
     *
     * @param userId The ID of the user whose token is to be retrieved.
     * @return The Token object found for the specified user ID, or null if no token is found.
     */
    Token findToken(int userId);

    /**
     * Updates the verification status of a token for the given user ID.
     *
     * @param userId The ID of the user whose token verification status is to be updated.
     */
    void updateTokenVerification(int userId);

}
