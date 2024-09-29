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
 * Implementation of the TokenDAO interface.
 * This class handles database operations related to tokens.
 */
public class TokenDAOImpl implements TokenDAO {

    /**
     * Inserts a new token into the database.
     *
     * @param token The token object containing the token data to be inserted.
     * @throws GeneralException If there is an error inserting the token into the database.
     */
    @Override
    public void insertToken(Token token) {
        String sql = "INSERT INTO [dbo].[Token] " +
                "([userId], [otp], [requestedTime], [isVerified]) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, token.getUserId()); // Set userId
            preparedStatement.setString(2, token.getToken()); // Set otp (token)
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(token.getRequestedTime())); // Set requestedTime
            preparedStatement.setBoolean(4, token.isVerified()); // Set isVerified

            // Execute the update to insert the token into the database
            preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error inserting token into the database", e);
        }
    }

    /**
     * Retrieves a token for a specified user from the database.
     *
     * @param userId The ID of the user whose token is being retrieved.
     * @return The Token object if found, or null if no token is found for the specified user.
     * @throws GeneralException If there is an error finding the token in the database.
     */
    @Override
    public Token findToken(int userId) {
        String sql = "SELECT [otp], [requestedTime], [isVerified] FROM [dbo].[Token] WHERE [userId] = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, userId); // Set userId

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a token was found
            if (resultSet.next()) {
                String otp = resultSet.getString("otp");
                LocalDateTime requestedTime = resultSet.getTimestamp("requestedTime").toLocalDateTime();
                boolean isVerified = resultSet.getBoolean("isVerified");

                // Return the Token object
                return new Token(userId, otp, requestedTime, isVerified);
            } else {
                return null; // No token found for the given userId
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding token in the database", e);
        }
    }

    /**
     * Updates the verification status of a token for a specific user in the database.
     *
     * @param userId The ID of the user whose token's verification status is being updated.
     * @throws GeneralException If there is an error updating the token verification in the database or no token is found for the user.
     */
    @Override
    public void updateTokenVerification(int userId) {
        String sql = "UPDATE [dbo].[Token] SET [isVerified] = ? WHERE [userId] = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            preparedStatement.setBoolean(1, true); // Set isVerified to true
            preparedStatement.setInt(2, userId); // Set userId

            // Execute the update to change the isVerified status
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new GeneralException("No token found to update for userId: " + userId);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error updating token verification in the database", e);
        }
    }

}
