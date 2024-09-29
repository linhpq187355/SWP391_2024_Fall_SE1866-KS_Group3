package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeDetailDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class HomeDetailDAOImpl implements HomeDetailDAO {

@Override
public Home getHomeById(int id) {
    String sql = "SELECT [id], [name], [address], [longitude], [latitude], [orientation], " +
            "[area], [leaseDuration], [moveInDate], [numOfBedroom], [numOfBath], " +
            "[createdDate], [modifiedDate], [homeDescription], [tenantDescription], " +
            "[wardId], [homeTypeId], [createdBy] FROM [dbo].[Homes] WHERE [id] = ?";

    Home home = null;

    try (Connection connection = DBContext.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setInt(1, id);
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
        throw new GeneralException("Error retrieving home by ID from the database: " + e.getMessage(), e);
    }

    return home;
}

@Override
public List<Price> getHomePricesByHomeId(int homeId) {
    String sql = "SELECT [id], [price], [createdDate] FROM [Prices] WHERE [Homesid] = ?";
    List<Price> prices = new ArrayList<>();

    try (Connection connection = DBContext.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setInt(1, homeId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Price price = new Price();
            price.setId(resultSet.getInt("id"));
            price.setPrice(resultSet.getInt("price"));
            price.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
            prices.add(price);
        }

    } catch (SQLException | IOException | ClassNotFoundException e) {
        throw new GeneralException("Error retrieving home prices from the database: " + e.getMessage(), e);
    }

    return prices;
}

@Override
public User getCreatorByHomeId(int homeId) {
    String sql = "SELECT u.id, u.firstName, u.lastName, u.email, u.phoneNumber " +
            "FROM [HSS Users] u " +
            "JOIN Homes h ON u.id = h.createdBy " +
            "WHERE h.id = ?";
    User user = null;

    try (Connection connection = DBContext.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setInt(1, homeId);
        ResultSet resultSet = preparedStatement.executeQuery();

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
    }

    return user;
}
}
