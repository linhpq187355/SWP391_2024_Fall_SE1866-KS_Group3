/*
 * Copyright(C) 2024, Roomify Project.
 * Roomify: Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-11-01      1.0                 ThangLT          First Implement
 */

package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.PermissonDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Permisson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissonDAOImpl extends DBContext implements PermissonDAO {

    @Override
    public List<Permisson> getAll() {
        List<Permisson> permissons = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Permissons]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Permisson permisson = new Permisson();
                permisson.setId(resultSet.getInt("id"));
                permisson.setName(resultSet.getString("name"));
                permisson.setDescription(resultSet.getString("description"));
                permissons.add(permisson);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Throwing exception to the upper layer to handle it properly
            throw new GeneralException("Error retrieving permissons from the database: " + e.getMessage(), e);
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
        return permissons;
    }

    @Override
    public Permisson fetchByName(String name) {
        String sql = "SELECT * FROM [dbo].[Permissons] WHERE [name]=?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Permisson permisson = new Permisson();
                    permisson.setId(resultSet.getInt("id"));
                    permisson.setName(resultSet.getString("name"));
                    permisson.setDescription(resultSet.getString("description"));
                    return permisson;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error fetching home from the database: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Permisson fetchById(int id) {
        String sql = "SELECT * FROM [dbo].[Permissons] WHERE [id]=?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Permisson permisson = new Permisson();
                    permisson.setId(resultSet.getInt("id"));
                    permisson.setName(resultSet.getString("name"));
                    permisson.setDescription(resultSet.getString("description"));
                    return permisson;
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error fetching home from the database: " + e.getMessage(), e);
        }
        return null;
    }
}
