package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDAO;
import com.homesharing.model.Home;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;



public class HomeDAOImpl implements HomeDAO {
    @Override
    public int saveHome(Home home) {
        String sql = "INSERT INTO Homes (name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, createdDate, modifiedDate, homeDescription, tenantDescription, wardId, homeTypeId, createdBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Setting parameters for the PreparedStatement using the Home object
            preparedStatement.setString(1, home.getName());
            preparedStatement.setString(2, home.getAddress());
            preparedStatement.setBigDecimal(3, home.getLongitude());
            preparedStatement.setBigDecimal(4, home.getLatitude());
            preparedStatement.setString(5, home.getOrientation());
            preparedStatement.setBigDecimal(6, home.getArea());
            preparedStatement.setInt(7, home.getLeaseDuration());
            preparedStatement.setTimestamp(8, java.sql.Timestamp.valueOf(home.getMoveInDate()));
            preparedStatement.setInt(9, home.getNumOfBedroom());
            preparedStatement.setInt(10, home.getNumOfBath());
            preparedStatement.setTimestamp(11, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(12, home.getModifiedDate() != null ? java.sql.Timestamp.valueOf(LocalDateTime.now()) : null);
            preparedStatement.setString(13, home.getHomeDescription());
            preparedStatement.setString(14, home.getTenantDescription());
            preparedStatement.setInt(15, home.getWardId());
            preparedStatement.setInt(16, home.getHomeTypeId());
            preparedStatement.setInt(17, home.getCreatedBy());

            // Execute the insert statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated Home ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  // Return the generated Home ID
                    } else {
                        throw new SQLException("Creating home failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating home failed, no rows affected.");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new RuntimeException("Error saving home to the database: " + e.getMessage(), e);
        }
    }
}
