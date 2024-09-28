package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.SearchDAO;
import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchDAOimpl implements SearchDAO {

    @Override
    public List<Home> searchHomes(String name, String location, Integer minPrice, Integer maxPrice) {
        String sql = "SELECT h.*, p.id AS priceId, p.price " +
                "FROM Homes h " +
                "JOIN Prices p ON h.id = p.homesId " +
                "JOIN Wards w ON h.wardId = w.id " +
                "JOIN Districts d ON w.Districtsid = d.id " +
                "JOIN Provinces pr ON d.provincesId = pr.id " +
                "WHERE (h.name LIKE ? OR ? IS NULL OR ? = '') " +
                "AND (h.address LIKE ? OR ? IS NULL OR ? = '') " +
                "AND (p.price BETWEEN ? AND ? OR (? IS NULL AND ? IS NULL)) " +
                "ORDER BY h.createdDate DESC";

        List<Home> homes = new ArrayList<>();

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, "%" + location + "%");
            preparedStatement.setString(5, location);
            preparedStatement.setString(6, location);
            preparedStatement.setInt(7, minPrice != null ? minPrice : 0);
            preparedStatement.setInt(8, maxPrice != null ? maxPrice : Integer.MAX_VALUE);
            preparedStatement.setObject(9, minPrice); // This allows for null
            preparedStatement.setObject(10, maxPrice); // This allows for null

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
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
                    home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ?
                            resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                    home.setHomeDescription(resultSet.getString("homeDescription"));
                    home.setTenantDescription(resultSet.getString("tenantDescription"));
                    home.setWardId(resultSet.getInt("wardId"));
                    home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                    home.setCreatedBy(resultSet.getInt("createdBy"));

                    // Create and set the Price object
                    Price price = new Price();
                    price.setId(resultSet.getInt("priceId"));
                    price.setPrice(resultSet.getInt("price"));

                    // If you want to associate price with home, you can do it like this
                    home.setPriceId(price.getId());
                    // Optionally, store price as a field in the Home model if needed

                    homes.add(home);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error searching homes: " + e.getMessage(), e);
        }

        return homes;
    }
}
