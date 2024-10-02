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
    public List<Home> searchHomesByAdress   (String address) {
        String sql = "SELECT id, name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, createdDate, modifiedDate, homeDescription, tenantDescription, wardId, homeTypeId, createdBy " +
                "FROM Homes WHERE address LIKE ?";

        List<Home> homes = new ArrayList<>();

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the wildcard for partial matching
            preparedStatement.setString(1, "%" + address + "%");

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
            throw new RuntimeException("Error searching homes by address: " + e.getMessage(), e);
        }

        return homes;
    }

    @Override
    public List<Home> searchByPriceRange(int minPrice, int maxPrice) {
        String sql = "SELECT h.id, h.name, h.address, h.longitude, h.latitude, h.orientation, h.area, " +
                "h.leaseDuration, h.moveInDate, h.numOfBedroom, h.numOfBath, h.createdDate, " +
                "h.modifiedDate, h.homeDescription, h.tenantDescription, h.wardId, h.homeTypeId, " +
                "h.createdBy " +
                "FROM Homes h " +
                "JOIN Prices p ON h.id = p.Homesid " +
                "WHERE p.price BETWEEN ? AND ?";

        List<Home> homes = new ArrayList<>();

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the minimum and maximum price
            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);

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
            throw new RuntimeException("Error searching homes by price range: " + e.getMessage(), e);
        }

        return homes;
    }

    @Override
    public int getMaxPrice() throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT TOP 1 price FROM prices p ORDER BY p.price DESC";
        int maxPrice = 0;

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                maxPrice = resultSet.getInt("price");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error retrieving maximum price: " + e.getMessage(), e);
        }

        return maxPrice;
    }

    @Override
    public Price getPriceByHomeId(int homeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, price, Homesid, createdDate FROM Prices WHERE Homesid = ?";
        Price price = null;

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, homeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    price = new Price();
                    price.setId(resultSet.getInt("id"));
                    price.setPrice(resultSet.getInt("price"));
                    price.setHomesId(resultSet.getInt("Homesid"));
                    price.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException("Error getting price by home ID: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return price;
    }


}
