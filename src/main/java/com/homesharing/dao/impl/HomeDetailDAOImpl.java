package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDetailDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeDetailDAOImpl implements HomeDetailDAO {
    public Home getHomeById(int id) {
        String sql = "SELECT [id], [name], [address], [longitude], [latitude], [orientation], " +
                "[area], [leaseDuration], [moveInDate], [numOfBedroom], [numOfBath], " +
                "[createdDate], [modifiedDate], [homeDescription], [tenantDescription], " +
                "[wardId], [homeTypeId], [createdBy] FROM [dbo].[Homes] WHERE [id] = ?";

        Home home = null; // Initialize the Home object

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id); // Set the parameter for the query
            ResultSet resultSet = preparedStatement.executeQuery();

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
                home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null
                        ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                home.setHomeDescription(resultSet.getString("homeDescription"));
                home.setTenantDescription(resultSet.getString("tenantDescription"));
                home.setWardId(resultSet.getInt("wardId"));
                home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                home.setCreatedBy(resultSet.getInt("createdBy"));
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Throwing exception to the upper layer to handle it properly
            throw new GeneralException("Error retrieving home by ID from the database: " + e.getMessage(), e);
        }

        return home; // Return the Home object or null if not found
    }
}
