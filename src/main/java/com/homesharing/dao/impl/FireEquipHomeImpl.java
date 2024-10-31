package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.FireEquipHomeDAO;
import com.homesharing.model.FireEquipmentsHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FireEquipHomeImpl implements FireEquipHomeDAO {
    @Override
    public int save(FireEquipmentsHome fireEquipmentsHome) {
        // First, check if the record already exists
        if (exists(fireEquipmentsHome)) {
            // If it exists, skip the save operation
            return -1; // or return a specific value indicating that the record already exists
        }

        String sql = "INSERT INTO [dbo].[FireEquipments_Homes] ([fireEquipmentsId],[homesId]) VALUES (?,?)";
        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Setting parameters for the PreparedStatement using the Home object
            preparedStatement.setInt(1, fireEquipmentsHome.getFireEquipmentsId());
            preparedStatement.setInt(2, fireEquipmentsHome.getHomesId());

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
                throw new SQLException("No amenities found for the specified home ID: " + homeId);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error clearing amenities from home ID " + homeId + ": " + e.getMessage(), e);
        }
        return affectedRows; // Return the number of affected rows
    }

    private boolean exists(FireEquipmentsHome fireEquipmentsHome) {
        String sql = "SELECT COUNT(*) FROM [dbo].[FireEquipments_Homes] WHERE [fireEquipmentsId] = ? AND [homesId] = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, fireEquipmentsHome.getFireEquipmentsId());
            preparedStatement.setInt(2, fireEquipmentsHome.getHomesId());

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
}
