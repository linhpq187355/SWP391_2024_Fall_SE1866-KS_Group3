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
    /**
     * Searches for a list of homes based on the address.
     *
     * @param address The address of the home to search for (supports partial matching).
     * @return A list of Home objects that match the address criteria.
     */
    @Override
    public List<Home> searchHomesByAdress(String address) {
        String sql = "SELECT id, name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, createdDate, modifiedDate, homeDescription, tenantDescription, wardsId, homeTypeId, createdBy " +
                "FROM Homes WHERE address LIKE ?";

        List<Home> homes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + address + "%");
            resultSet = preparedStatement.executeQuery();

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
                home.setWardId(resultSet.getInt("wardsId"));
                home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                home.setCreatedBy(resultSet.getInt("createdBy"));
                homes.add(home);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error searching homes by address: " + e.getMessage(), e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return homes;
    }

    /**
     * Searches for homes within a specified price range.
     *
     * @param minPrice The minimum price of the home.
     * @param maxPrice The maximum price of the home.
     * @return A list of Home objects that fall within the specified price range.
     */
    @Override
    public List<Home> searchByPriceRange(int minPrice, int maxPrice) {
        String sql = "SELECT h.id, h.name, h.address, h.longitude, h.latitude, h.orientation, h.area, " +
                "h.leaseDuration, h.moveInDate, h.numOfBedroom, h.numOfBath, h.createdDate, " +
                "h.modifiedDate, h.homeDescription, h.tenantDescription, h.wardsId, h.homeTypeId, " +
                "h.createdBy " +
                "FROM Homes h " +
                "JOIN Prices p ON h.id = p.Homesid " +
                "WHERE p.price BETWEEN ? AND ?";

        List<Home> homes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);
            resultSet = preparedStatement.executeQuery();

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
                home.setWardId(resultSet.getInt("wardsId"));
                home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                home.setCreatedBy(resultSet.getInt("createdBy"));
                homes.add(home);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error searching homes by price range: " + e.getMessage(), e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return homes;
    }



    /**
     * Retrieves the price of a home by its unique home ID.
     *
     * @param homeId The unique identifier of the home.
     * @return The Price object associated with the specified home ID.
     * @throws SQLException if there is an error executing the SQL query.
     * @throws ClassNotFoundException if the JDBC driver class is not found.
     */
    @Override
    public Price getPriceByHomeId(int homeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, price, Homesid, createdDate FROM Prices WHERE Homesid = ?";
        Price price = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, homeId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                price = new Price();
                price.setId(resultSet.getInt("id"));
                price.setPrice(resultSet.getInt("price"));
                price.setHomesId(resultSet.getInt("Homesid"));
                price.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException("Error getting price by home ID: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return price;
    }

    /**
     * Retrieves the price of a home by its unique home ID.
     *
     * @return The Price object associated with the specified home ID.
     * @throws SQLException if there is an error executing the SQL query.
     * @throws ClassNotFoundException if the JDBC driver class is not found.
     */
    @Override
    public int getMaxPrice() throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT TOP 1 price FROM prices p ORDER BY p.price DESC";
        int maxPrice = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                maxPrice = resultSet.getInt("price");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error retrieving maximum price: " + e.getMessage(), e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return maxPrice;
    }




}
