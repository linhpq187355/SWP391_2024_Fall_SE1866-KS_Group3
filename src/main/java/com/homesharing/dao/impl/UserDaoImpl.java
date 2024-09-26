package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.UserDao;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implementation of UserDao interface, handling database operations related to the User entity.
 * This class interacts with the database to save a user and check if an email already exists.
 */
public class UserDaoImpl implements UserDao {

    /**
     * Saves a user to the database by executing an INSERT SQL query.
     * All database-related exceptions are caught and thrown as a GeneralException
     * to be handled in the service layer.
     *
     * @param user The user object to be saved into the database.
     * @return The generated user ID.
     * @throws GeneralException If any error occurs during the database operation.
     */
    @Override
    public int saveUser(User user) {
        String sql = "INSERT INTO [HSS_User] ([firstName], [lastName], [email], [Rolesid], [status], [hashedPassword], [createdAt]) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to ensure automatic resource management
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the prepared statement based on the user's information
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getRolesId());
            preparedStatement.setString(5, user.getStatus());
            preparedStatement.setString(6, user.getHashedPassword());
            preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    } else {
                        throw new GeneralException("Creating user failed, no ID obtained.");
                    }
                }
            } else {
                throw new GeneralException("Creating user failed, no rows affected.");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error saving user to the database: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if an email already exists in the database by executing a SELECT COUNT SQL query.
     * Returns true if the email exists, otherwise false.
     * Any database-related exceptions are caught and thrown as a GeneralException.
     *
     * @param email The email address to check.
     * @return True if the email exists in the database, false otherwise.
     * @throws GeneralException If any error occurs during the database operation.
     */
    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [HSS_User] WHERE [email] = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if email exists
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error checking email existence in the database: " + e.getMessage(), e);
        }

        return false;
    }
}
