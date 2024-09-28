package com.homesharing.dao.impl;
import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDetailDAO;
import com.homesharing.model.Home;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeDetailDAOImpl implements HomeDetailDAO {
    @Override
    public Home getHomeById(int homeId) {
        String sql = "SELECT id, name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, createdDate, modifiedDate, homeDescription, tenantDescription, wardId, homeTypeId, createdBy " +
                "FROM Homes WHERE id = ?";  // SQL query to get home details by ID

        Home home = null;

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the ID parameter
            preparedStatement.setInt(1, homeId);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    home = new Home();
                    home.setId(resultSet.getInt("id"));
                    home.setName(resultSet.getString("name"));
                    home.setAddress(resultSet.getString("address"));
                    home.setLongitude(resultSet.getBigDecimal("longitude"));
                    home.setLatitude(resultSet.getBigDecimal("latitude"));
                    home.setOrientation(resultSet.getString("orientation"));
                    home.setArea(resultSet.getBigDecimal("area"));
                    home.setLeaseDuration(resultSet.getInt("leaseDuration"));
                    home.setMoveInDate(resultSet.getTimestamp("moveInDate").toLocalDateTime());
                    home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                    home.setNumOfBath(resultSet.getInt("numOfBath"));
                    home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                    home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                    home.setHomeDescription(resultSet.getString("homeDescription"));
                    home.setTenantDescription(resultSet.getString("tenantDescription"));
                    home.setWardId(resultSet.getInt("wardId"));
                    home.setHomeType(resultSet.getInt("homeTypeId"));
                    home.setCreatedBy(resultSet.getInt("createdBy"));
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Handle any exceptions
            throw new RuntimeException("Error retrieving home details: " + e.getMessage(), e);
        }

        return home;  // Return the home object or null if not found
    }
}
