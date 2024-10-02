package com.homesharing.dao.impl;
import com.homesharing.conf.DBContext;
import com.homesharing.dao.AmentityHomeDAO;
import com.homesharing.model.AmentityHome;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AmentityHomeDAOImpl implements AmentityHomeDAO {
    @Override
    public int save(AmentityHome amentityHome) {
        String sql = "INSERT INTO [dbo].[Amenities_Homes] ([amenitiesId],[homesId]) VALUES(?,?)";
        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Setting parameters for the PreparedStatement using the Home object
            preparedStatement.setInt(1, amentityHome.getHomesId());
            preparedStatement.setInt(2, amentityHome.getAmenityId());
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