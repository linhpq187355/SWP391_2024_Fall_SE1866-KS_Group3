/*
 * Copyright(C) 2024, Roomify Project.
 * Roomify: Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-11-02      1.0                 ThangLT          First Implement
 */

package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.UserPermissionDAO;
import com.homesharing.model.Permission;
import com.homesharing.model.UserPermission;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.homesharing.conf.DBContext.getConnection;

public class UserPermDAOImpl implements UserPermissionDAO {
    /**
     * Fetch the list of permission given by user id
     *
     * @param userId
     * @return list of permission
     */
    @Override
    public List<Permission> getPermissionsByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid userId: " + userId);
        }

        List<Permission> permList = new ArrayList<>();

        String sql = "SELECT p.id, p.name, p.description\n" +
                "FROM Permissons p\n" +
                "JOIN Users_Permissons up ON p.id = up.Permissonsid\n" +
                "JOIN HSS_Users u ON up.[HSS Usersid] = u.id\n" +
                "WHERE u.id = ?;";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Permission perm = new Permission();
                    perm.setId(resultSet.getInt("id"));
                    perm.setName(resultSet.getString("name"));
                    perm.setDescription(resultSet.getString("description"));
                    permList.add(perm);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error retrieving permissions for userId: " + userId, e);
        }
        return permList;
    }

    /**
     * Fetch permissions via email
     *
     * @param email
     * @return the permission list
     */
    @Override
    public List<Permission> getPermissionByEmail(String email) {
        List<Permission> permList = new ArrayList<>();

        String sql = "SELECT p.id, p.name, p.description\n" +
                "FROM Permissons p\n" +
                "JOIN Users_Permissons up ON p.id = up.Permissonsid\n" +
                "JOIN HSS_Users u ON up.[HSS Usersid] = u.id\n" +
                "WHERE u.email = ?;";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Permission perm = new Permission();
                    perm.setId(resultSet.getInt("id"));
                    perm.setName(resultSet.getString("name"));
                    perm.setDescription(resultSet.getString("description"));
                    permList.add(perm);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error retrieving permissions for user email: " + email, e);
        }
        return permList;
    }

    /**
     * Save the user permission info
     *
     * @param permission
     * @return operation status is successful or not
     */
    @Override
    public int save(UserPermission permission) {
        // First, check if the record already exists
        if (exists(permission)) {
            // If it exists, skip the save operation
            return -1; // or return a specific value indicating that the record already exists
        }

        String sql = "INSERT INTO [dbo].[Users_Permissons] ([HSS Usersid],[Permissonsid]) VALUES (?,?)";
        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Setting parameters for the PreparedStatement using the Permission object
            preparedStatement.setInt(1, permission.getUserId());
            preparedStatement.setInt(2, permission.getPermissonId());

            // Execute the insert statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // Retrieve the generated Permission ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  // Return the generated Permission ID
                    } else {
                        throw new SQLException("Creating permission failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating permission failed, no rows affected.");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving permission to the database: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean exists(UserPermission permission) {
        String sql = "SELECT COUNT(*) FROM [dbo].[Users_Permissons] WHERE [HSS Usersid] = ? AND [Permissonsid] = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, permission.getUserId());
            preparedStatement.setInt(2, permission.getPermissonId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Return true if count is greater than 0
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error checking existence of permission: " + e.getMessage(), e);
        }
        return false; // Return false if no record exists
    }

    /**
     * Clear all the permission via user id
     *
     * @param userId
     * @return operation status is successful or not
     */
    @Override
    public int clear(int userId) {
        String sql = "DELETE FROM [dbo].[Users_Permissons] WHERE [HSS Usersid] = ?";
        int affectedRows = 0; // To keep track of the number of affected rows
        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Setting the userId parameter for the PreparedStatement
            preparedStatement.setInt(1, userId);
            // Execute the delete statement and capture affected rows
            affectedRows = preparedStatement.executeUpdate();
            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new SQLException("No permissions found for the specified user ID: " + userId);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error clearing permissions for user ID " + userId + ": " + e.getMessage(), e);
        }
        return affectedRows; // Return the number of affected rows
    }
}
