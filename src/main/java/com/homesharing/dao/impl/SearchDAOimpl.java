package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.SearchDAO;
import com.homesharing.model.Home;
import com.homesharing.model.Price; // Import the Price model

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchDAOimpl implements SearchDAO {

    @Override
    public List<Home> searchHomes(String name, String location, Integer minPrice, Integer maxPrice) {
        String sql = "SELECT h.*, p.id AS price_id, p.price AS price_value " +
                "FROM Homes h " +
                "JOIN Prices p ON h.id = p.Homesid " +
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

            // Set the parameters
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, "%" + location + "%");
            preparedStatement.setString(5, location);
            preparedStatement.setString(6, location);
            preparedStatement.setInt(7, minPrice != null ? minPrice : 0); // Set default minPrice if null
            preparedStatement.setInt(8, maxPrice != null ? maxPrice : Integer.MAX_VALUE); // Set default maxPrice if null
            preparedStatement.setInt(9, minPrice);
            preparedStatement.setInt(10, maxPrice);

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
                    home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                    home.setHomeDescription(resultSet.getString("homeDescription"));
                    home.setTenantDescription(resultSet.getString("tenantDescription"));
                    home.setWardId(resultSet.getInt("wardId"));
                    home.setHomeType(resultSet.getInt("homeTypeId"));
                    home.setCreatedBy(resultSet.getInt("createdBy"));

                    // Create a Price object
                    Price price = new Price();
                    price.setId(resultSet.getInt("price_id")); // Get the price ID
                    price.setPrice(resultSet.getBigDecimal("price_value")); // Get the price value
                    price.setHomeId(home.getId()); // Set the corresponding home ID

                    // You can now store the price object or manage it according to your needs
                    // For example, if you want to keep it in the home, you may need to add a list of prices in Home model

                    homes.add(home);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error searching homes: " + e.getMessage(), e);
        }

        return homes;
    }
}
