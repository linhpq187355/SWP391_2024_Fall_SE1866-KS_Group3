/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second Implement
 */

package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDAO;
                                                                                                                                        import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the HomeDAO interface for interacting with the Homes database.
 * This class provides methods for CRUD operations and querying homes.
 */
public class HomeDAOImpl extends DBContext implements HomeDAO {
    private List<Home> homes = new ArrayList<>();
    // Logger for logging errors or information
    private static final Logger logger = Logger.getLogger(HomeDAOImpl.class.getName());
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PreferenceDAOImpl.class);


    @Override
    public List<Home> getAllHomes() {
        String sql = "SELECT [id], [name], [address], [longitude], [latitude], [orientation], [area], [leaseDuration], [moveInDate], [numOfBedroom], [numOfBath], [createdDate], [modifiedDate], [homeDescription], [tenantDescription], [wardId], [homeTypeId], [createdBy] FROM [dbo].[Homes]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
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
                home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
                home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                home.setNumOfBath(resultSet.getInt("numOfBath"));
                home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                home.setHomeDescription(resultSet.getString("homeDescription"));
                home.setTenantDescription(resultSet.getString("tenantDescription"));
                home.setWardId(resultSet.getInt("wardId"));
                home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                home.setCreatedBy(resultSet.getInt("createdBy"));

                homes.add(home);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Throwing exception to the upper layer to handle it properly
            throw new GeneralException("Error retrieving homes from the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return homes;
    }

    @Override
    public Home getHomeById(int id) {
        String sql = "SELECT [id], [name], [status] FROM [dbo].[Provinces] WHERE [id]=?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
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
                    home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
                    home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                    home.setNumOfBath(resultSet.getInt("numOfBath"));
                    home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                    home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                    home.setHomeDescription(resultSet.getString("homeDescription"));
                    home.setTenantDescription(resultSet.getString("tenantDescription"));
                    home.setWardId(resultSet.getInt("wardId"));
                    home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                    home.setCreatedBy(resultSet.getInt("createdBy"));
                    return home;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving home to the database: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public int getNumberHomes() {
        String sql = "select count(id) total\n" +
                "from Homes";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // If a result is found, return the total number
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error retrieving total homes from the database", e);
            throw new RuntimeException("Error retrieving total homes from the database", e);
        } finally {
            // Closing resources in reverse order of opening
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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return 0;
    }

    /**
     * Retrieves homes matching the specified host IDs.
     *
     * @param matchingHost an array of host IDs to match.
     * @return List of matching Home objects or null if the input is invalid.
     */
    @Override
    public List<Home> getMatchingHomes(int[] matchingHost) {
        if(matchingHost == null || matchingHost.length == 0){
            LOGGER.warn("Preference is null. No updates will be made.");
            return null;
        }

        StringBuilder sql = new StringBuilder("select * \n" +
                "from Homes\n" +
                "where createdBy in (");
        for (int i = 0; i < matchingHost.length; i++) {
            sql.append("?");
            if (i < matchingHost.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");
        List<Home> matchingHomes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());


            // Set the price ID parameters in the prepared statement
            for (int i = 0; i < matchingHost.length; i++) {
                preparedStatement.setInt(i + 1, matchingHost[i]);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Home home = new Home();
                home.setId(resultSet.getInt("id"));
                home.setName(resultSet.getString("name"));
                home.setAddress(resultSet.getString("address"));
                home.setArea(resultSet.getBigDecimal("area"));
                home.setLeaseDuration(resultSet.getInt("leaseDuration"));
                home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
                matchingHomes.add(home);
            }
        } catch (SQLException e) {
            logger.warning("SQL error occurred while retrieving prices from the database: {}"+ e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.warning("Unexpected error occurred: {}"+ e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return matchingHomes;
    }

    @Override
    public int saveHome(Home home) {
        String sql = "INSERT INTO Homes (name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, createdDate, modifiedDate, homeDescription, tenantDescription, wardId, homeTypeId, createdBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Setting parameters for the PreparedStatement using the Home object
            preparedStatement.setString(1, home.getName());
            preparedStatement.setString(2, home.getAddress());
            preparedStatement.setBigDecimal(3, home.getLongitude());
            preparedStatement.setBigDecimal(4, home.getLatitude());
            preparedStatement.setString(5, home.getOrientation());
            preparedStatement.setBigDecimal(6, home.getArea());
            preparedStatement.setInt(7, home.getLeaseDuration());
            preparedStatement.setDate(8, java.sql.Date.valueOf(home.getMoveInDate()));
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
            throw new IllegalArgumentException("Error saving home to the database: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieve a list of the 12 newest homes from the database.
     *
     * @return List of the 12 newest homes.
     */
    @Override
    public List<Home> getNewHomes() {
        // SQL query to fetch 12 newest homes with price information
        String sql = """
                SELECT TOP 12 h.id, h.address, h.area, h.createdDate, tb1.id AS priceId
                FROM Homes h
                LEFT JOIN (
                    SELECT Homesid, price, createdDate, id
                    FROM Prices
                    WHERE createdDate IN (
                        SELECT MAX(p.createdDate) 
                        FROM Prices p
                        GROUP BY p.Homesid
                    )
                ) tb1 ON tb1.Homesid = h.id
                ORDER BY h.createdDate DESC
                """;

        // List to store fetched Home objects
        List<Home> homeList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // Try-with-resources to automatically close resources
        try{
            connection = DBContext.getConnection(); // Get database connection
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // Process each result row and map it to a Home object
            while (resultSet.next()) {
                Home home = new Home();
                home.setId(resultSet.getInt("id"));
                home.setAddress(resultSet.getString("address"));
                home.setArea(resultSet.getBigDecimal("area"));
                home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                home.setPriceId(resultSet.getInt("priceId"));

                homeList.add(home); // Add the Home object to the list
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Log the exception and throw a custom exception
            logger.severe("Error retrieving homes from the database: " + e.getMessage());
            throw new GeneralException("Error retrieving homes from the database", e);
        } finally {
            // Ensure resources are closed in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) closeConnection(); // close the connection using DBContext's method
            } catch (SQLException e) {
                logger.warning("Failed to close resources: " + e.getMessage());
            }
        }

        return homeList; // Return the list of homes
    }
}
