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
import com.homesharing.dao.PermissionDAO;
import com.homesharing.model.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAOImpl extends DBContext implements PermissionDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceDAOImpl.class);

    @Override
    public List<Permission> getAll() {
        List<Permission> permissions = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Permissons]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Permission permission = new Permission();
                permission.setId(resultSet.getInt("id"));
                permission.setName(resultSet.getString("name"));
                permission.setDescription(resultSet.getString("description"));
                permissions.add(permission);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Throwing exception to the upper layer to handle it properly
            LOGGER.error("Error retrieving permissons from the database: {}", e.getMessage());
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
                LOGGER.error("Error closing database resources: {}", e.getMessage());
            }
        }
        return permissions;
    }

    @Override
    public Permission fetchByName(String name) {
        String sql = "SELECT * FROM [dbo].[Permissons] WHERE [name]=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Permission permission = new Permission();
                permission.setId(resultSet.getInt("id"));
                permission.setName(resultSet.getString("name"));
                permission.setDescription(resultSet.getString("description"));
                return permission;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            LOGGER.error("Error retrieving permissons from the database: {}", e.getMessage());
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
                LOGGER.error("Error closing database resources: {}", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Permission fetchById(int id) {
        String sql = "SELECT * FROM [dbo].[Permissons] WHERE [id]=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Permission permission = new Permission();
                permission.setId(resultSet.getInt("id"));
                permission.setName(resultSet.getString("name"));
                permission.setDescription(resultSet.getString("description"));
                return permission;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            LOGGER.error("Error retrieving permissons from the database: {}", e.getMessage());
            throw new IllegalArgumentException("Error fetching permissisons from the database: " + e.getMessage(), e);
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
                LOGGER.error("Error closing database resources: {}", e.getMessage());
            }
        }
        return null;
    }
}
