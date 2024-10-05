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
}
