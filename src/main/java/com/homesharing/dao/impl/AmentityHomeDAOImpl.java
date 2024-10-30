package com.homesharing.dao.impl;
import com.homesharing.conf.DBContext;
import com.homesharing.dao.AmentityHomeDAO;
import com.homesharing.model.AmentityHome;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AmentityHomeDAOImpl implements AmentityHomeDAO {
    @Override
    public int save(AmentityHome amentityHome) {
        // First, check if the record already exists
        if (exists(amentityHome)) {
            // If it exists, skip the save operation
            return -1; // or return a specific value indicating that the record already exists
        }

        String sql = "INSERT INTO [dbo].[Amenities_Homes] ([amenitiesId],[homesId]) VALUES(?,?)";
        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Setting parameters for the PreparedStatement using the Home object
            preparedStatement.setInt(1, amentityHome.getAmenityId());
            preparedStatement.setInt(2, amentityHome.getHomesId());

            // Execute the insert statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // Retrieve the generated Home ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  // Return the generated Home ID
                    } else {
                        throw new SQLException("Creating amentity home failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating amentity home failed, no rows affected.");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving amentity home to the database: " + e.getMessage(), e);
        }
    }

    // Method to check if the record already exists
    private boolean exists(AmentityHome amentityHome) {
        String sql = "SELECT COUNT(*) FROM [dbo].[Amenities_Homes] WHERE [amenitiesId] = ? AND [homesId] = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, amentityHome.getAmenityId());
            preparedStatement.setInt(2, amentityHome.getHomesId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Return true if count is greater than 0
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error checking existence of amentity home: " + e.getMessage(), e);
        }
        return false; // Return false if no record exists
    }

    @Override
    public int clear(int homeId) {
        String sql = "DELETE FROM [dbo].[Amenities_Homes] WHERE [homesId] = ?";
        int affectedRows = 0; // To keep track of the number of affected rows
        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Setting the homeId parameter for the PreparedStatement
            preparedStatement.setInt(1, homeId);
            // Execute the delete statement and capture affected rows
            affectedRows = preparedStatement.executeUpdate();
            // Check if any rows were affected
            if (affectedRows == 0) {
                return 0;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error clearing amenities from home ID " + homeId + ": " + e.getMessage(), e);
        }
        return affectedRows; // Return the number of affected rows
    }
}