/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.User;
import com.homesharing.util.PasswordUtil;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Implementation of the UserDAO interface, handling database operations for the User entity.
 * @author ManhNC
 */
public class UserDAOImpl extends DBContext implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAOImpl.class.getName());

    /**
     * Updates the Google ID for a user in the database.
     *
     * @param googleId The Google ID to be updated.
     * @param email    The email address of the user to update.
     * @return The number of rows updated (should be 1 if successful).
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public int updateGoogleId(String googleId, String email) throws SQLException {
        int rowUpdated = 0;
        String sql = "UPDATE [dbo].[HSS_Users]" +
                "   SET [googleID] = ?" +
                " WHERE email = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set the parameters for the update statement
            preparedStatement.setString(1, googleId);
            preparedStatement.setString(2, email);

            // Execute the update and get the number of affected rows
            rowUpdated = preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error updating user googleId", e);
            throw new GeneralException("Error updating user googleId: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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
        return rowUpdated;
    }

    /**
     * Retrieves the Google ID associated with a given email address from the database.
     *
     * @param email The email address of the user.
     * @return The Google ID if found, or {@code null} if not found.
     * @throws SQLException if a database access error occurs or the result set is closed.
     */
    @Override
    public String getGoogleId(String email) throws SQLException {
        String sql = "SELECT [googleID] FROM [HSS_Users] WHERE [email] = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("googleID");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding googleID by email in the database: " + e.getMessage(), e);
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

        return null; // Return null if no user is found
    }

    /**
     * Saves a new user to the database.
     *
     * @param user The User object to be saved.
     * @return The generated user ID.
     * @throws GeneralException If an error occurs during the database operation.
     */
    @Override
    public int saveUser(User user) throws SQLException {
        String sql = "INSERT INTO [HSS_Users] ([firstName], [lastName], [email], [Rolesid], [status], [hashedPassword], [googleID], [createdAt], [gender], [dob], [phoneNumber]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            // Set parameters for the prepared statement based on the user's information
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getRolesId());
            preparedStatement.setString(5, user.getStatus());
            preparedStatement.setString(6, user.getHashedPassword());
            preparedStatement.setString(7, user.getGoogleId());
            preparedStatement.setTimestamp(8, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(9, user.getGender());
            // Handle null date of birth case
            if (user.getDob() != null) {
                preparedStatement.setDate(10, java.sql.Date.valueOf(user.getDob()));
            } else {
                preparedStatement.setNull(10, java.sql.Types.DATE);
            }
            preparedStatement.setString(11, user.getPhoneNumber());
            // Execute the update to insert the user into the database
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated keys (ID)
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating user failed, no rows affected.");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new GeneralException("Error saving user to the database: " + e.getMessage(), e);
        } finally {
            closeConnection();
        }
    }


    /**
     * Checks if an email already exists in the database by executing a SELECT COUNT SQL query.
     *
     * @param email The email address to check.
     * @return {@code true} if the email exists, {@code false} otherwise.
     * @throws GeneralException if there is an error checking email existence in the database.
     */
    @Override
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM [HSS_Users] WHERE [email] = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            // Execute the query to check for email existence
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if email exists
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new GeneralException("Error checking email existence in the database", e);
        } finally {
            closeConnection();
        }

        // Return false if no email match is found
        return false;
    }

    /**
     * Checks if a phone number already exists in the database.
     *
     * @param phone The phone number to check.
     * @return {@code true} if the phone number exists, {@code false} otherwise.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public boolean phoneExists(String phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM [HSS_Users] WHERE [phoneNumber] = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, phone);

            // Execute the query to check for email existence
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if email exists
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new GeneralException("Error checking email existence in the database", e);
        } finally {
            closeConnection();
        }

        // Return false if no email match is found
        return false;
    }

    /**
     * Updates the email address of a user in the database.
     *
     * @param email The new email address.
     * @param id    The ID of the user to update.
     * @return The number of rows updated.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public int updateEmail(String email, int id) throws SQLException {
        int rowsUpdated = 0;
        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                    " SET [email] = ?\n" +
                    " WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set the parameters for the update statement
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);

            // Execute the update and get the number of affected rows
            rowsUpdated = preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error updating user profile", e);
            throw new GeneralException("Error updating user profile: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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
        return rowsUpdated;
    }

    /**
     * Checks if a user has a password set in the database.
     *
     * @param userId The ID of the user.
     * @return 1 if the password is NULL, 0 if it's not NULL, -1 if an error occurs or user not found.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public int passWordExists(int userId) throws SQLException {
        String sql = "SELECT CASE\n" +
                "           WHEN hashedPassword IS NULL THEN 1\n" +
                "           ELSE 0\n" +
                "           END AS password_is_null\n" +
                " FROM HSS_Users" +
                " WHERE id = ?;\n";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            // Execute the query to check for email existence
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1); // Return true if email exists
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new GeneralException("Error checking email existence in the database", e);
        } finally {
            if (resultSet != null) {
                resultSet.close(); // Đảm bảo ResultSet được đóng
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        // Return false if no email match is found
        return -1;
    }

    /**
     * Retrieves a {@link User} by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} object if found, or {@code null} if no user is found.
     * @throws GeneralException if there is an error retrieving the user from the database.
     */
    @Override
    public User getUser(int id) {
        String sql = "SELECT u.id, u.address, u.gender, u.firstName, u.lastName, u.avatar, u.dob, u.isVerified, u.email, u.phoneNumber, u.rolesid "
                + "FROM [HSS_Users] u WHERE u.id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Set the email parameter for the prepared statement
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            // Execute the query to check for email existence

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setAddress(resultSet.getString("address"));
                user.setGender(resultSet.getString("gender"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setVerified(resultSet.getBoolean("isVerified"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setDob(resultSet.getDate("dob") != null ? resultSet.getDate("dob").toLocalDate() : null);
                user.setRolesId(resultSet.getInt("rolesid"));
                return user;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error getting user from database", e);
            throw new GeneralException("Error getting user from database", e);
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

        return null; // Return null if user is not found
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return The {@link User} object if found, or {@code null} if no user is found.
     * @throws GeneralException if there is an error finding the user by email in the database.
     */
    @Override
    public User findUserByEmail(String email) {
        String sql = "SELECT [id], [firstName], [lastName], [email], [Rolesid], [status], [hashedPassword], [createdAt] FROM [HSS_Users] WHERE [email] = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setRolesId(resultSet.getInt("Rolesid"));
                user.setStatus(resultSet.getString("status"));
                user.setHashedPassword(resultSet.getString("hashedPassword"));

                // Check null before call toLocalDateTime()
                Timestamp createdAtTimestamp = resultSet.getTimestamp("createdAt");
                if (createdAtTimestamp != null) {
                    user.setCreatedAt(createdAtTimestamp.toLocalDateTime());
                } else {
                    user.setCreatedAt(null);
                }

                return user;
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding user by email in the database: " + e.getMessage(), e);
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

        return null; // Return null if no user is found
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT [id]\n" +
                "      ,[email]\n" +
                "      ,[hashedPassword]\n" +
                "      ,[phoneNumber]\n" +
                "      ,[username]\n" +
                "      ,[firstName]\n" +
                "      ,[lastName]\n" +
                "      ,[avatar]\n" +
                "      ,[dob]\n" +
                "      ,[address]\n" +
                "      ,[gender]\n" +
                "      ,[citizenNumber]\n" +
                "      ,[createdAt]\n" +
                "      ,[status]\n" +
                "      ,[isVerified]\n" +
                "      ,[lastModified]\n" +
                "      ,[rolesid]\n" +
                "  FROM [dbo].[HSS_Users]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setHashedPassword(resultSet.getString("hashedPassword"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setUserName(resultSet.getString("username"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAvatar(resultSet.getString("avatar"));
                if (resultSet.getDate("dob") != null) {
                    user.setDob(resultSet.getDate("dob").toLocalDate());
                }
                user.setAddress(resultSet.getString("address"));
                user.setGender(resultSet.getString("gender"));
                user.setCitizenNumber(resultSet.getString("citizenNumber"));
                // Check null before call toLocalDateTime()
                Timestamp createdAtTimestamp = resultSet.getTimestamp("createdAt");
                if (createdAtTimestamp != null) {
                    user.setCreatedAt(createdAtTimestamp.toLocalDateTime());
                } else {
                    user.setCreatedAt(null);
                }
                user.setStatus(resultSet.getString("status"));
                user.setVerified(resultSet.getBoolean("isVerified"));
                // Check null before call toLocalDateTime()
                Timestamp lastModifiedTimestamp = resultSet.getTimestamp("lastModified");
                if (lastModifiedTimestamp != null) {
                    user.setLastModified(lastModifiedTimestamp.toLocalDateTime());
                } else {
                    user.setLastModified(null);
                }
                user.setRolesId(resultSet.getInt("rolesid"));

                userList.add(user);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new GeneralException("Error: ", e);
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
        return userList;
    }

    /**
     * Updates the profile information of an existing user in the database.
     * This method only updates specific fields like address, gender, name, avatar, and date of birth.
     *
     * @param user The {@link User} object containing the user's updated profile information.
     * @return The number of rows affected by the update operation (should be 1 if successful).
     * @throws GeneralException if there is an error updating the user profile in the database.
     */
    @Override
    public int updateUserProfile(User user) {
        int rowsUpdated = 0;
        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                "   SET [address] = ?\n" +
                "      ,[gender] = ?\n" +
                "      ,[firstName] = ?\n" +
                "      ,[lastName] = ?\n" +
                "      ,[avatar] = ?\n" +
                "      ,[dob] = ?\n" +
                "      ,[lastModified] = GETDATE()\n" +
                " WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set the parameters for the update statement
            preparedStatement.setString(1, user.getAddress());
            preparedStatement.setString(2, user.getGender());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getAvatar());

            // Handle null date of birth case
            if (user.getDob() != null) {
                preparedStatement.setDate(6, java.sql.Date.valueOf(user.getDob()));
            } else {
                preparedStatement.setNull(6, java.sql.Types.DATE);
            }

            // Set the user ID to identify the correct record
            preparedStatement.setInt(7, user.getId());

            // Execute the update and get the number of affected rows
            rowsUpdated = preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error updating user profile", e);
            throw new GeneralException("Error updating user profile: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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
        return rowsUpdated;
    }

    /**
     * Retrieves the avatar URL or path of the user with the specified ID.
     *
     * @param id The unique identifier of the user whose avatar is to be retrieved.
     * @return A {@link String} representing the user's avatar (URL or file path), or {@code null} if no avatar is found.
     * @throws RuntimeException if there is an error retrieving the avatar from the database.
     */
    @Override
    public String getUserAvatar(int id) {
        String sql = "SELECT u.avatar\n" +
                "  FROM [HSS_Users] u WHERE u.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Set the user ID parameter in the prepared statement
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            // If a result is found, return the avatar string
            if (resultSet.next()) {
                return resultSet.getString("avatar");
            }


        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error retrieving user avatar from the database", e);
            throw new RuntimeException("Error retrieving user avatar from the database", e);
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
        // Return null if no avatar was found for the given user ID
        return null;
    }

    @Override
    public void updateUserStatus(int id, String status) {
        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                "   SET [status] = ?\n" +
                " WHERE [id] = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error update user status: " + e.getMessage(), e);
        }
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT [id], [firstName], [lastName], [email], [Rolesid], [status], [hashedPassword], [createdAt] FROM [dbo].[HSS_Users] WHERE [id] = ?";


        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            //
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setRolesId(resultSet.getInt("rolesid"));
                user.setStatus(resultSet.getString("status"));
                user.setHashedPassword(resultSet.getString("hashedPassword"));
                user.setCreatedAt(resultSet.getTimestamp("createdAt").toLocalDateTime());
                return user;
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding user by id in the database: " + e.getMessage(), e);
        }

        return null; // Return null if no user is found
    }

    /**
     * Resets the password for a user by updating the hashed password in the database.
     *
     * @param password The new plaintext password to be hashed and stored.
     * @param id The unique identifier of the user whose password is to be reset.
     * @return An integer representing the number of rows affected (1 if successful, 0 if not).
     * @throws GeneralException if there is an error during the password update.
     */
    @Override
    public int resetPassword(String password, int id) {
        int rowsUpdated = 0;
        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                "   SET [hashedPassword] = ?\n" +
                " WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{

            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Hash the new password using a utility method before storing
            String hashedPassword = PasswordUtil.hashPassword(password);

            // Set the hashed password and the user ID parameters in the prepared statement
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setInt(2, id);

            // Execute the update and capture the number of rows affected
            rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error updating user password", e);
            throw new GeneralException("Error updating user password: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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
        return rowsUpdated;
    }

}
