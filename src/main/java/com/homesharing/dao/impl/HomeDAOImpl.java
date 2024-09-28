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
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class HomeDAOImpl implements HomeDAO {
    private List<Home> homes = new ArrayList<>();

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

    @Override
    public List<Home> getNewHomes() {
        String sql = """
                select top 12 h.id, h.address, h.area, h.createdDate, tb1.id [priceId]
                \t   from Homes h
                \t   left join (
                \t   select Homesid, price, createdDate, id
                \t   from Prices\s
                \t   where createdDate in (
                \t\t\tselect max(p.createdDate) createdDate
                \t\t\tfrom Prices p
                \t\t\tgroup by p.Homesid
                \t   )) tb1 on tb1.Homesid = h.id
                \t   order by h.createdDate desc""";
        List<Home> homes = new ArrayList<>();
        // Use try-with-resources to manage database resources (Connection, PreparedStatement, and ResultSet)
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Home home = new Home();
                home.setId(resultSet.getInt("id"));
                home.setAddress(resultSet.getString("address"));
                home.setArea(resultSet.getBigDecimal("area"));
                home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                home.setPriceId(resultSet.getInt("priceId"));

                homes.add(home);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Ném ngoại lệ dưới dạng runtime exception để xử lý tại tầng service
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        }

        return homes;
    }

}
