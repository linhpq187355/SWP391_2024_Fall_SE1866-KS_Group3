package com.homesharing.dao.impl;

import com.homesharing.dao.LoadHomeDAO;

import com.homesharing.conf.DBContext;
import com.homesharing.model.Home;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class LoadHomeDAOImpl implements LoadHomeDAO {

    @Override
    public List<Home> getAllHomes() {
        String sql = "SELECT id, name, address, longitude, latitude, orientation, area, leaseDuration, moveInDate, numOfBedroom, numOfBath, homeDescription FROM Homes";
        List<Home> homes = new ArrayList<>();

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

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
                home.setHomeDescription(resultSet.getString("homeDescription"));

                homes.add(home);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error retrieving homes: " + e.getMessage(), e);
        }
        return homes;
    }
}
