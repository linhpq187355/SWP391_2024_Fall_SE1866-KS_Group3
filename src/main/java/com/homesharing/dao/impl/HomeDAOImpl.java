package com.homesharing.dao.impl;
import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Implementation of HomeDAO to interact with the Homes database.
 */
public class HomeDAOImpl extends DBContext implements HomeDAO {
    private List<Home> homes = new ArrayList<>();
    // Logger for logging errors or information
    private static final Logger logger = Logger.getLogger(HomeDAOImpl.class.getName());

    /**
     * @return the list of the home
     */
    @Override
    public List<Home> getAllHomes() {
        List<Home> homeList = new ArrayList<>();
        String sql = "SELECT [id], [name], [address], [longitude], [latitude], [orientation], [area], [leaseDuration], [moveInDate], [numOfBedroom], [numOfBath], [createdDate], [modifiedDate], [homeDescription], [tenantDescription], [wardsId], [homeTypeId], [createdBy] FROM [dbo].[Homes]";

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

                homeList.add(home);
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
        return homeList;
    }

    /**
     * Get home object via id
     * @param id
     * @return the home that need to be found
     */
    @Override
    public Home getHomeById(int id) {
        String sql = "SELECT [id], [name], [address], [longitude], [latitude], [orientation], [area], [leaseDuration], [moveInDate], [numOfBedroom], [numOfBath], [createdDate], [modifiedDate], [homeDescription], [tenantDescription], [wardsId], [homeTypeId], [createdBy] FROM [dbo].[Homes] WHERE [id]=?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
                    return home;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving home to the database: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Get home object via author id
     * @param userId
     * @return
     */
    @Override
    public List<Home> getHomesByUserId(int userId) {
        String sql = "SELECT * FROM [dbo].[Homes] WHERE [createdBy]=?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
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
                    home.setWardId(resultSet.getInt("wardsId"));
                    home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                    home.setCreatedBy(resultSet.getInt("createdBy"));
                    homes.add(home);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving home to the database: " + e.getMessage(), e);
        }
        return homes;
    }

    /**
     * Save home's info to the database
     * @param home Home object that need to be saved
     * @return home id
     */
    @Override
    public int saveHome(Home home) {
        String sql = "INSERT INTO Homes (name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, createdDate, modifiedDate, homeDescription, tenantDescription, wardsId, homeTypeId, createdBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
            throw new IllegalArgumentException("Error saving home to the database: " + e.getMessage(), e);
        }
    }

    /**
     * Update home info
     *
     * @param home
     * @return home id indicate the task is success or not
     */
    @Override
    public int updateHome(Home home) {
        List<String> updateColumns = new ArrayList<>();
        List<Object> updateValues = new ArrayList<>();

        if (home.getName() != null) {
            updateColumns.add("name = ?");
            updateValues.add(home.getName());
        }
        if (home.getAddress() != null) {
            updateColumns.add("address = ?");
            updateValues.add(home.getAddress());
        }
        if (home.getLongitude() != null && home.getLatitude() != null) {
            updateColumns.add("latitude = ?");
            updateColumns.add("longitude = ?");
            updateValues.add(home.getLongitude());
            updateValues.add(home.getLatitude());
        }
        if (home.getOrientation() != null) {
            updateColumns.add("orientation = ?");
            updateValues.add(home.getOrientation());
        }
        if (home.getArea() != null) {
            updateColumns.add("area = ?");
            updateValues.add(home.getArea());
        }
        if (home.getLeaseDuration() != -1) {
            updateColumns.add("leaseDuration = ?");
            updateValues.add(home.getLeaseDuration());
        }
        if (home.getMoveInDate() != null) {
            updateColumns.add("moveInDate = ?");
            updateValues.add(java.sql.Timestamp.valueOf(home.getMoveInDate()));
        }
        if (home.getNumOfBedroom() != -1) {
            updateColumns.add("numOfBedroom = ?");
            updateValues.add(home.getNumOfBedroom());
        }
        if (home.getNumOfBath() != -1) {
            updateColumns.add("numOfBath = ?");
            updateValues.add(home.getNumOfBath());
        }
        if (home.getHomeDescription() != null) {
            updateColumns.add("homeDescription = ?");
            updateValues.add(home.getHomeDescription());
        }
        if (home.getTenantDescription() != null) {
            updateColumns.add("tenantDescription = ?");
            updateValues.add(home.getTenantDescription());
        }
        if (home.getModifiedDate() != null) {
            updateColumns.add("modifiedDate = ?");
            updateValues.add(java.sql.Timestamp.valueOf(home.getModifiedDate()));
        }

        String updateColumnsStr = String.join(", ", updateColumns);
        String sql = "UPDATE Homes SET " + updateColumnsStr + " WHERE id = ?";

        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            for (Object value : updateValues) {
                preparedStatement.setObject(paramIndex++, value);
            }
            preparedStatement.setInt(paramIndex, home.getId());

            // Execute the update statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows;

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error updating home to the database: " + e.getMessage(), e);
        }
    }

    /**
     * Change home status
     *
     * @param homeId
     * @param status
     * @return home id indicate the success of the task or not
     */
    @Override
    public int changeStatus(int homeId, String status) {
        String[] allowedStatuses = {"active", "inactive", "pending", "sold"};
        if (!Arrays.asList(allowedStatuses).contains(status)) {
            throw new IllegalArgumentException("Invalid status. Status must be one of: " + Arrays.toString(allowedStatuses));
        }
        String sql = "UPDATE [dbo].[Homes] SET [status] = ? WHERE id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int paramIndex = 1;
            preparedStatement.setString(paramIndex, status);
            preparedStatement.setInt(++paramIndex, homeId);

            // Execute the update statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows;

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error updating home to the database: " + e.getMessage(), e);
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
        try {
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
