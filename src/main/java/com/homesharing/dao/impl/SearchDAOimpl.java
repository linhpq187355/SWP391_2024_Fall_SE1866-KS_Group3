package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.SearchDAO;
import com.homesharing.model.Home;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchDAOimpl implements SearchDAO {
    @Override
    public List<Home> searchHomesByName(String name) {
        String sql = "SELECT id, name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, createdDate, modifiedDate, homeDescription, tenantDescription, wardId, homeTypeId, createdBy " +
                "FROM Homes WHERE name LIKE ?";

        List<Home> homes = new ArrayList<>();

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the wildcard for partial matching
            preparedStatement.setString(1, "%" + name + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Create a Home object for each row in the ResultSet
                    Home home = new Home();
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
                    home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                    home.setCreatedBy(resultSet.getInt("createdBy"));

                    // Add each home to the list
                    homes.add(home);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error searching homes by name: " + e.getMessage(), e);
        }

        return homes;
    }

}
