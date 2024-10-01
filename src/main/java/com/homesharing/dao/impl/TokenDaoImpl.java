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

public class TokenDaoImpl implements TokenDAO {

    @Override
    // Method to insert a token into the database
    public void insertToken(Token token) {
        String sql = "INSERT INTO [dbo].[Token] " +
                "([userId], [otp], [requestedTime], [isVerified]) " +
                "VALUES (?, ?, ?, ?)";

        // Using try-with-resources to ensure automatic resource management
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

    @Override
    public Token findToken(int userId) {
        String sql = "SELECT [otp], [requestedTime], [isVerified] FROM [dbo].[Token] WHERE [userId] = ?";

        // Using try-with-resources to ensure automatic resource management
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

                // Create and return the Token object
                return new Token(userId, otp, requestedTime, isVerified);
            } else {
                return null; // No token found for the given userId
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding token in the database", e);
        }
    }

    @Override
    public void updateTokenVerification(int userId) {
        String sql = "UPDATE [dbo].[Token] SET [isVerified] = ? WHERE [userId] = ?";

        // Using try-with-resources to ensure automatic resource management
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

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
        }
    }

}
