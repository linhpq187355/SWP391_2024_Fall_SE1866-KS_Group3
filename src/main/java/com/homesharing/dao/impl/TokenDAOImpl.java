/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.TokenDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implementation of the TokenDAO interface for managing token-related database operations.
 * @author ManhNC
 */
public class TokenDAOImpl extends DBContext implements TokenDAO {

    /**
     * Inserts a token into the database.
     *
     * @param token the token to be inserted
     * @throws GeneralException if an error occurs while inserting the token
     */
    @Override
    public void insertToken(Token token) throws SQLException {
        String sql = "INSERT INTO [dbo].[Token] " +
                "([userId], [otp], [requestedTime], [isVerified]) " +
                "VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, token.getUserId()); // Set userId
            preparedStatement.setString(2, token.getToken()); // Set otp (token)
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(token.getRequestedTime())); // Set requestedTime
            preparedStatement.setBoolean(4, token.isVerified()); // Set isVerified

            // Execute the update to insert the token into the database
            preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error inserting token into the database", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Finds a token in the database by user ID.
     *
     * @param userId the user ID associated with the token
     * @return the Token object if found, or null if no token exists for the given user ID
     * @throws GeneralException if an error occurs while finding the token
     */
    @Override
    public Token findToken(int userId) throws SQLException {
        String sql = "SELECT [otp], [requestedTime], [isVerified] FROM [dbo].[Token] WHERE [userId] = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, userId); // Set userId

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a token was found
            if (resultSet.next()) {
                String otp = resultSet.getString("otp");
                LocalDateTime requestedTime = resultSet.getTimestamp("requestedTime").toLocalDateTime();
                boolean isVerified = resultSet.getBoolean("isVerified");

                // Create and return the Token object
                return new Token(userId, otp, requestedTime, isVerified);
            } else {
                return null; // No token found for the given userId
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding token in the database", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Updates the verification status of a token for the given user ID.
     *
     * @param userId the user ID associated with the token
     * @throws GeneralException if an error occurs while updating the token verification
     */
    @Override
    public void updateTokenVerification(int userId) throws SQLException {
        String sql = "UPDATE [dbo].[Token] SET [isVerified] = ? WHERE [userId] = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set parameters for the prepared statement
            preparedStatement.setBoolean(1, true); // Set isVerified to true
            preparedStatement.setInt(2, userId); // Set userId

            // Execute the update to change the isVerified status
            int rowsAffected = preparedStatement.executeUpdate();

            // Optional: Check if the update was successful
            if (rowsAffected == 0) {
                throw new GeneralException("No token found to update for userId: " + userId);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error updating token verification in the database", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Updates the token code and time for a user.
     * This method update token code and time instead of create a new token
     *
     * @param userId The ID of the user whose token verification status is being updated.
     */
    @Override
    public void updateToken(int userId, String otp, LocalDateTime time) throws SQLException {
        String sql = "UPDATE [dbo].[Token] SET  [otp] = ?, [requestedTime] = ? WHERE [userId] = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set parameters for the prepared statement
            preparedStatement.setString(1, otp); // Set otp
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(time)); // Set requestedTime
            preparedStatement.setInt(3, userId); // Set userId

            // Execute the update to change the isVerified status
            int rowsAffected = preparedStatement.executeUpdate();

            // Optional: Check if the update was successful
            if (rowsAffected == 0) {
                throw new GeneralException("Error when update token for userID : " + userId);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error updating token code in the database", e);
        } finally {
            closeConnection();
        }
    }

}
