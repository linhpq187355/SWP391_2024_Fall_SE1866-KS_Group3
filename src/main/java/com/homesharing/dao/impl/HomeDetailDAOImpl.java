package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDetailDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * HomeDetailDAOImpl is a class that implements the HomeDetailDAO interface.
 * This class is responsible for interacting with the database to retrieve
 * details related to homes, such as home information, prices, creator information, etc.
 */
public class HomeDetailDAOImpl implements HomeDetailDAO {

    /**
     * Retrieve a Home object by its unique ID.
     *
     * @param id the unique identifier of the home
     * @return Home object with the given ID or null if not found
     * @throws GeneralException if there is an error during the database query
     */
    @Override
    public Home getHomeById(int id) {
        String sql = "SELECT [id], [name], [address], [longitude], [latitude], [orientation], " +
                "[area], [leaseDuration], [moveInDate], [numOfBedroom], [numOfBath], " +
                "[createdDate], [modifiedDate], [homeDescription], [tenantDescription], " +
                "[wardsId], [homeTypeId], [createdBy] FROM [dbo].[Homes] WHERE [id] = ?";
        Home home = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

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
                home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
                home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
                home.setNumOfBath(resultSet.getInt("numOfBath"));
                home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null
                        ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
                home.setHomeDescription(resultSet.getString("homeDescription"));
                home.setTenantDescription(resultSet.getString("tenantDescription"));
                home.setWardId(resultSet.getInt("wardsId"));
                home.setHomeTypeId(resultSet.getInt("homeTypeId"));
                home.setCreatedBy(resultSet.getInt("createdBy"));
            }
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
            home.setMoveInDate(resultSet.getDate("moveInDate").toLocalDate());
            home.setNumOfBedroom(resultSet.getInt("numOfBedroom"));
            home.setNumOfBath(resultSet.getInt("numOfBath"));
            home.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
            home.setModifiedDate(resultSet.getTimestamp("modifiedDate") != null
                    ? resultSet.getTimestamp("modifiedDate").toLocalDateTime() : null);
            home.setHomeDescription(resultSet.getString("homeDescription"));
            home.setTenantDescription(resultSet.getString("tenantDescription"));
            home.setWardId(resultSet.getInt("wardsId"));
            home.setHomeTypeId(resultSet.getInt("homeTypeId"));
            home.setCreatedBy(resultSet.getInt("createdBy"));
        }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving home by ID from the database: " + e.getMessage(), e);
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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return home;
    }


    /**
     * Retrieve a list of Price objects for a specific home by its ID.
     *
     * @param homeId the unique identifier of the home
     * @return List of Price objects associated with the home
     * @throws GeneralException if there is an error during the database query
     */
    @Override
    public List<Price> getHomePricesByHomeId(int homeId) {
        String sql = "SELECT [id], [price], [createdDate] FROM [Prices] WHERE [Homesid] = ?";
        List<Price> prices = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, homeId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Price price = new Price();
                price.setId(resultSet.getInt("id"));
                price.setPrice(resultSet.getInt("price"));
                price.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                prices.add(price);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving home prices from the database: " + e.getMessage(), e);
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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return prices;
    }


    /**
     * Retrieve the User object who created a specific home by its ID.
     *
     * @param homeId the unique identifier of the home
     * @return User object who created the home
     * @throws GeneralException if there is an error during the database query
     */
@Override
public User getCreatorByHomeId(int homeId) {
    String sql = "SELECT u.id, u.firstName, u.lastName, u.email, u.phoneNumber " +
            "FROM [HSS_Users] u " +
            "JOIN Homes h ON u.id = h.createdBy " +
            "WHERE h.id = ?";
    User user = null;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        connection = DBContext.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, homeId);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
        }
    } catch (SQLException | IOException | ClassNotFoundException e) {
        throw new GeneralException("Error retrieving creator information from the database: " + e.getMessage(), e);
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
            throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
        }
    }

    return user;
}

    /**
     * Retrieve a list of Price objects for a specific home by its ID.
     *
     * @param homeId the unique identifier of the home
     * @return List of Price objects associated with the home
     * @throws GeneralException if there is an error during the database query
     */
    @Override
    public List<HomeType> getHomeTypesByHomeId(int homeId) {
        String sql = "SELECT ht.id, ht.name, ht.description, ht.status " +
                "FROM HomeTypes ht " +
                "JOIN Homes h ON ht.id = h.homeTypeId " +
                "WHERE h.id = ?";
        List<HomeType> homeTypes = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, homeId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HomeType homeType = new HomeType();
                homeType.setId(resultSet.getInt("id"));
                homeType.setName(resultSet.getString("name"));
                homeType.setDescription(resultSet.getString("description"));
                homeType.setStatus(resultSet.getString("status"));
                homeTypes.add(homeType);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving home types from the database: " + e.getMessage(), e);
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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return homeTypes;
    }
    /**
     * Retrieve a list of Amenity objects for a specific home by its ID.
     *
     * @param homeId the unique identifier of the home
     * @return List of Amenity objects associated with the home
     * @throws GeneralException if there is an error during the database query
     */
    @Override
    public List<Amentity> getHomeAmenitiesByHomeId(int homeId) {
        String sql = "SELECT a.id, a.name, a.icon, a.status " +
                "FROM Amenities a " +
                "JOIN Amenities_Homes ah ON a.id = ah.amenitiesId " +
                "WHERE ah.homesId = ?";
        List<Amentity> amenities = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, homeId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Amentity amentity = new Amentity();
                amentity.setId(resultSet.getInt("id"));
                amentity.setName(resultSet.getString("name"));
                amentity.setIcon(resultSet.getString("icon"));
                amentity.setStatus(resultSet.getString("status"));
                amenities.add(amentity);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving home amenities from the database: " + e.getMessage(), e);
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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return amenities;
    }


    /**
     * Retrieve a list of FireEquipment objects for a specific home by its ID.
     *
     * @param homeId the unique identifier of the home
     * @return List of FireEquipment objects associated with the home
     * @throws GeneralException if there is an error during the database query
     */

    @Override
    public List<FireEquipment> getHomeFireEquipmentsByHomeId(int homeId) {
        String sql = "SELECT fe.id, fe.name, fe.icon, fe.status " +
                "FROM FireEquipments fe " +
                "JOIN FireEquipments_Homes feh ON fe.id = feh.fireEquipmentsId " +
                "WHERE feh.homesId = ?";
        List<FireEquipment> fireEquipments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, homeId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FireEquipment fireEquipment = new FireEquipment();
                fireEquipment.setId(resultSet.getInt("id"));
                fireEquipment.setName(resultSet.getString("name"));
                fireEquipment.setIcon(resultSet.getString("icon"));
                fireEquipment.setStatus(resultSet.getString("status"));
                fireEquipments.add(fireEquipment);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error retrieving home fire equipment from the database: " + e.getMessage(), e);
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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }

        return fireEquipments;
    }


}
