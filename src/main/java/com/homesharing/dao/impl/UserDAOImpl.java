/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 *  * 2024-9-18      1.0                 ManhNC         First Implement
 * 2024-10-01        1.0              Pham Quang Linh    First Implement
 * 2024-10-10        2.0              Pham Quang Linh    Second Implement
 * 2024-10-10        2.0                 ManhNC          Second Implement
 * 2024-10-30        2.0              Pham Quang Linh    Add functions
 */

package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Appointment;
import com.homesharing.model.Home;
import com.homesharing.model.User;
import com.homesharing.util.PasswordUtil;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;

/**
 * Implementation of the UserDAO interface, handling database operations for the User entity.
 */
public class UserDAOImpl extends DBContext implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAOImpl.class.getName());
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

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
     * Saves a user to the database by executing an INSERT SQL query.
     *
     * @param user The {@link User} object containing user details to be saved.
     * @return The generated ID of the newly created user.
     * @throws GeneralException if there is an error saving the user to the database.
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
            closeConnection(connection);
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
            closeConnection(connection);
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
            closeConnection(connection);
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
        String sql = "SELECT u.id, u.address, u.gender, u.firstName, u.lastName, u.avatar, u.dob, u.isVerified, u.email, u.phoneNumber, u.duration, u.earliestMoveIn, u.latestMoveIn, u.minBudget, u.maxBudget,u.rolesid,u.preferredCity \n"
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
                user.setMaxBudget(resultSet.getInt("maxBudget"));
                user.setMinBudget(resultSet.getInt("minBudget"));
                user.setEarliestMoveIn(resultSet.getDate("earliestMoveIn") != null ? resultSet.getDate("earliestMoveIn").toLocalDate() : null);
                user.setLatestMoveIn(resultSet.getDate("latestMoveIn") != null ? resultSet.getDate("latestMoveIn").toLocalDate() : null);
                user.setDuration(resultSet.getString("duration"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setPrefProv(resultSet.getInt("preferredCity"));

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
    public User findUserByEmail(String email) throws GeneralException, SQLException {
        String sql = "SELECT [id], [firstName], [lastName], [email], [Rolesid], [status], [hashedPassword], [createdAt] FROM [HSS_Users] WHERE [email] = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            // Establish connection to the database
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email); // Set the email parameter in the query
            resultSet = preparedStatement.executeQuery(); // Execute the query

            // Check if a user was found
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setRolesId(resultSet.getInt("Rolesid"));
                user.setStatus(resultSet.getString("status"));
                user.setHashedPassword(resultSet.getString("hashedPassword"));

                // Convert timestamp to LocalDateTime, checking for null
                Timestamp createdAtTimestamp = resultSet.getTimestamp("createdAt");
                if (createdAtTimestamp != null) {
                    user.setCreatedAt(createdAtTimestamp.toLocalDateTime());
                } else {
                    user.setCreatedAt(null);
                }

                return user; // Return the found user
            }

        } catch (SQLException e) {
            throw new GeneralException("SQL error while finding user with email " + email + ": " + e.getMessage(), e);
        } catch (IOException e) {
            throw new GeneralException("I/O error while finding user with email " + email + ": " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new GeneralException("Class not found error while finding user with email " + email + ": " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    closeConnection(connection);
                }
        }
        return null; // Return null if no user is found
    }

    /**
     *  Get all user from database
     * @return the list of user if database has data or null if database empty
     */
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT u.[id],\n" +
                "       u.[email],\n" +
                "       u.[hashedPassword],\n" +
                "       u.[phoneNumber],\n" +
                "       u.[username],\n" +
                "       u.[firstName],\n" +
                "       u.[lastName],\n" +
                "       u.[avatar],\n" +
                "       u.[dob],\n" +
                "       u.[address],\n" +
                "       u.[gender],\n" +
                "       u.[citizenNumber],\n" +
                "       u.[createdAt],\n" +
                "       u.[status],\n" +
                "       u.[isVerified],\n" +
                "       u.[modifiedDate],\n" +
                "       u.[rolesid],\n" +
                "       r.[name] AS roleName\n" +
                "FROM [dbo].[HSS_Users] u\n" +
                "LEFT JOIN [dbo].[Roles] r ON u.[rolesid] = r.[id]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
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
                Timestamp lastModifiedTimestamp = resultSet.getTimestamp("modifiedDate");
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
                "      ,[modifiedDate] = GETDATE()\n" +
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
                Connection connection = DBContext.getConnection();
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

    /**
     * Retrieves the total number of users with specific roles (rolesid in 3 or 4).
     *
     * @return The total number of users with roles 3 or 4.
     * @throws RuntimeException If there is an error accessing the database.
     */
    @Override
    public int getNumberUsers() {
        String sql = "select count(id) total\n" +
                "from HSS_Users\n" +
                "where rolesid in (3,4)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // If a result is found, return the total number
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error retrieving total user from the database", e);
            throw new RuntimeException("Error retrieving total user from the database", e);
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

        return 0;
    }

    /**
     * Updates the matching profile of a user in the database.
     *
     * @param user the User object containing updated information
     * @return the number of rows updated in the database
     * @throws GeneralException if there is an error while updating the user profile
     */
    @Override
    public int updateMatchingProfile(User user) {
        int rowsUpdated = 0;
        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                "   SET [dob] = ?" +
                "      ,[gender] = ?" +
                "      ,[duration] = ?" +
                "      ,[minBudget] = ?" +
                "      ,[maxBudget] = ?" +
                "      ,[earliestMoveIn] = ?" +
                "      ,[preferredCity] = ?" +
                "      ,[latestMoveIn] = ?" +
                " WHERE id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set the parameters for the update statement
            preparedStatement.setDate(1, java.sql.Date.valueOf(user.getDob()));
            preparedStatement.setString(2, user.getGender());
            preparedStatement.setString(3, user.getDuration());
            preparedStatement.setInt(4, user.getMinBudget());
            preparedStatement.setInt(5, user.getMaxBudget());
            preparedStatement.setDate(6, java.sql.Date.valueOf(user.getEarliestMoveIn()));
            preparedStatement.setInt(7, user.getPrefProv());
            preparedStatement.setDate(8, java.sql.Date.valueOf(user.getLatestMoveIn()));
            preparedStatement.setInt(9, user.getId());

            // Execute the update and get the number of affected rows
            rowsUpdated = preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error updating user matching profile", e);
            throw new GeneralException("Error updating user matching profile: " + e.getMessage(), e);
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
     * Retrieves a user's matching profile from the database based on their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the User object containing the matching profile information, or null if not found
     * @throws GeneralException if there is an error while retrieving the user profile
     */
    @Override
    public User getMatchingUserProfile(int id) {
        String sql = "SELECT [id], [minBudget], [maxBudget], [earliestMoveIn], [latestMoveIn], [duration], [preferredCity] FROM [dbo].[HSS_Users] WHERE [id] = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setMinBudget(resultSet.getInt("minBudget"));
                user.setMaxBudget(resultSet.getInt("maxBudget"));
                user.setDuration(resultSet.getString("duration"));
                if(resultSet.getDate("earliestMoveIn") != null){
                    user.setEarliestMoveIn(resultSet.getDate("earliestMoveIn").toLocalDate());
                } else {
                    user.setEarliestMoveIn(null);
                }
                if(resultSet.getDate("latestMoveIn") != null){
                    user.setLatestMoveIn(resultSet.getDate("latestMoveIn").toLocalDate());
                } else {
                    user.setLatestMoveIn(null);
                }
                user.setPrefProv(resultSet.getInt("preferredCity"));
                return user;
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error getting user matching profile", e);
            throw new GeneralException("Error gettinh user matching profile: " + e.getMessage(), e);
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

        return null; // Return null if no user is found
    }

    /**
     * Retrieves a list of hosts associated with the provided appointments.
     *
     * @param appointments List of appointments containing host IDs to fetch hosts.
     * @return A list of User objects representing hosts or null if appointments are null/empty.
     * @throws RuntimeException If there is an error accessing the database.
     */
    @Override
    public List<User> getHostByAppointment(List<Appointment> appointments) {
        if(appointments == null || appointments.isEmpty()){
            LOGGER.warn("Appointment is null. No updates will be made.");
            return null;
        }
        List<User> hostList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * \n" +
                "from HSS_Users\n" +
                "where id in (");
        for (int i = 0; i < appointments.size(); i++) {
            sql.append("?");
            if (i < appointments.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());


            // Set the price ID parameters in the prepared statement
            for (int i = 0; i < appointments.size(); i++) {
                preparedStatement.setInt(i + 1, appointments.get(i).getHostId());
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                hostList.add(user);
            }
        } catch (SQLException e) {
            logger.warning("SQL error occurred while retrieving home from the database: {}"+ e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.warning("Unexpected error occurred: {}"+ e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
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
        return hostList;
    }

    /**
     * Retrieves a list of tenants associated with the provided appointments.
     *
     * @param appointments List of appointments containing tenant IDs to fetch tenants.
     * @return A list of User objects representing tenants or null if appointments are null/empty.
     * @throws RuntimeException If there is an error accessing the database.
     */
    @Override
    public List<User> getTenantByAppointment(List<Appointment> appointments) {
        if(appointments == null || appointments.isEmpty()){
            LOGGER.warn("Appointment is null. No updates will be made.");
            return null;
        }
        List<User> tenantList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select * \n" +
                "from HSS_Users\n" +
                "where id in (");
        for (int i = 0; i < appointments.size(); i++) {
            sql.append("?");
            if (i < appointments.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());


            // Set the price ID parameters in the prepared statement
            for (int i = 0; i < appointments.size(); i++) {
                preparedStatement.setInt(i + 1, appointments.get(i).getTenantId());
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                tenantList.add(user);
            }
        } catch (SQLException e) {
            logger.warning("SQL error occurred while retrieving home from the database: {}"+ e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.warning("Unexpected error occurred: {}"+ e.getMessage());
            throw new RuntimeException("Error retrieving homes from the database: " + e.getMessage(), e);
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
        return tenantList;
    }


    @Override
    public int getMaxPet() {
        String sql = "SELECT TOP 1 pet FROM Preferences ORDER BY pet DESC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("pet");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max pet in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMinPet() {
        String sql = "SELECT TOP 1 pet FROM Preferences ORDER BY pet ASC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("pet");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min pet in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMaxCooking() {
        String sql = "SELECT TOP 1 cooking FROM Preferences ORDER BY cooking DESC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("cooking");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max cooking in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMinCooking() {
        String sql = "SELECT TOP 1 cooking FROM Preferences ORDER BY cooking ASC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("cooking");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min cooking in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMaxGuests() {
        String sql = "SELECT TOP 1 guests FROM Preferences ORDER BY guests DESC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("guests");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max guests in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMinGuests() {
        String sql = "SELECT TOP 1 guests FROM Preferences ORDER BY guests ASC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("guests");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min guests in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMaxInteraction() {
        String sql = "SELECT TOP 1 interaction FROM Preferences ORDER BY interaction DESC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("interaction");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max interaction in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMinInteraction() {
        String sql = "SELECT TOP 1 interaction FROM Preferences ORDER BY interaction ASC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("interaction");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min interaction in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMaxDrinking() {
        String sql = "SELECT TOP 1 drinking FROM Preferences ORDER BY drinking DESC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("drinking");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max drinking in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMinDrinking() {
        String sql = "SELECT TOP 1 drinking FROM Preferences ORDER BY drinking ASC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("drinking");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min drinking in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMaxSmoking() {
        String sql = "SELECT TOP 1 smoking FROM Preferences ORDER BY smoking DESC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("smoking");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get max smoking in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMinSmoking() {
        String sql = "SELECT TOP 1 smoking FROM Preferences ORDER BY smoking ASC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("smoking");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min smoking in the database: " + e.getMessage(), e);
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
        return 0;
    }
    @Override
    public int getMinCleanliness() {
        String sql = "SELECT TOP 1 cleanliness FROM Preferences ORDER BY cleanliness ASC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("cleanliness");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min cleanliness in the database: " + e.getMessage(), e);
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
        return 0; // Trả về 0 nếu không tìm thấy giá trị
    }
    @Override
    public int getMaxCleanliness() {
        String sql = "SELECT TOP 1 cleanliness FROM Preferences ORDER BY cleanliness DESC;";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("cleanliness");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error get min cleanliness in the database: " + e.getMessage(), e);
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
        return 0; // Trả về 0 nếu không tìm thấy giá trị
    }
    @Override
    public List<User> getFilteredUsers(Map<String, Object> searchParams) throws SQLException, IOException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT u.id,u.firstName,u.lastName,u.avatar,u.gender,u.rolesId, \n" +
                "       p.cleanliness, p.workSchedule, p.smoking, p.drinking, \n" +
                "       p.interaction, p.guests, p.cooking, p.pet \n" +
                "FROM HSS_Users u \n" +
                "JOIN Preferences p ON u.id = p.usersId \n" +
                "JOIN Homes h ON u.id = h.createdBy \n" +
                "JOIN Wards w ON h.wardsId = w.id \n" +
                "JOIN Districts d ON w.Districtsid = d.id \n" + // Kết nối bảng Districts
                "JOIN Provinces pr ON d.provincesId = pr.id \n" + // Kết nối bảng Provinces
                "WHERE 1=1");

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        if (searchParams.containsKey("gender")) {
            sql.append(" AND u.gender = ?");
        }
        if (searchParams.containsKey("province")) {
            sql.append(" AND pr.id = ?");
        }
        if (searchParams.containsKey("district")) {
            sql.append(" AND d.id = ?");
        }
        if (searchParams.containsKey("ward")) {
            sql.append(" AND w.id = ?");
        }

        // Các điều kiện lọc khác
        if (searchParams.containsKey("minCleanliness") && searchParams.containsKey("maxCleanliness")) {
            sql.append(" AND p.cleanliness BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minSmoking") && searchParams.containsKey("maxSmoking")) {
            sql.append(" AND p.smoking BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minDrinking") && searchParams.containsKey("maxDrinking")) {
            sql.append(" AND p.drinking BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minInteraction") && searchParams.containsKey("maxInteraction")) {
            sql.append(" AND p.interaction BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minGuests") && searchParams.containsKey("maxGuests")) {
            sql.append(" AND p.guests BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minCooking") && searchParams.containsKey("maxCooking")) {
            sql.append(" AND p.cooking BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minPet") && searchParams.containsKey("maxPet")) {
            sql.append(" AND p.pet BETWEEN ? AND ?");
        }
        sql.append(" ORDER BY u.id DESC");

        if (searchParams.containsKey("per_page")) {
            sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");
        }

        try {
            connection = DBContext.getConnection();
            ps = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (searchParams.containsKey("gender")) {
                ps.setString(paramIndex++, (String) searchParams.get("gender"));
            }
            if (searchParams.containsKey("province")) {
                int provinceId = Integer.parseInt(searchParams.get("province").toString());
                ps.setInt(paramIndex++, provinceId);
            }
            if (searchParams.containsKey("district")) {
                int districtId = Integer.parseInt(searchParams.get("district").toString());
                ps.setInt(paramIndex++, districtId);
            }
            if (searchParams.containsKey("ward")) {
                int wardId = Integer.parseInt(searchParams.get("ward").toString());
                ps.setInt(paramIndex++, wardId);
            }

            if (searchParams.containsKey("minCleanliness") && searchParams.containsKey("maxCleanliness")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minCleanliness"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxCleanliness"));
            }
            if (searchParams.containsKey("minSmoking") && searchParams.containsKey("maxSmoking")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minSmoking"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxSmoking"));
            }
            if (searchParams.containsKey("minDrinking") && searchParams.containsKey("maxDrinking")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minDrinking"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxDrinking"));
            }
            if (searchParams.containsKey("minInteraction") && searchParams.containsKey("maxInteraction")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minInteraction"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxInteraction"));
            }
            if (searchParams.containsKey("minGuests") && searchParams.containsKey("maxGuests")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minGuests"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxGuests"));
            }
            if (searchParams.containsKey("minCooking") && searchParams.containsKey("maxCooking")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minCooking"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxCooking"));
            }
            if (searchParams.containsKey("minPet") && searchParams.containsKey("maxPet")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minPet"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxPet"));
            }
            if (searchParams.containsKey("per_page")) {
                int perPage = Integer.parseInt(searchParams.get("per_page").toString());
                int targetPage = searchParams.containsKey("targetPage")
                        ? Integer.parseInt(searchParams.get("targetPage").toString())
                        : 1;

                int offset = (targetPage - 1) * perPage;
                ps.setInt(paramIndex++, offset);
                ps.setInt(paramIndex, perPage);
            }

            logger.info(sql.toString());
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setGender(resultSet.getString("gender"));
                user.setRolesId(resultSet.getInt("rolesId"));
                userList.add(user);
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                logger.warning("Failed to close resources: " + e.getMessage());
            }
        }
        return userList;
    }
    @Override
    public int numOfUser(Map<String, Object> searchParams) throws SQLException, IOException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT u.id) \n" +
                "FROM HSS_Users u \n" +
                "JOIN Preferences p ON u.id = p.usersId \n" +
                "JOIN Homes h ON u.id = h.createdBy \n" +
                "JOIN Wards w ON h.wardsId = w.id \n" +
                "JOIN Districts d ON w.Districtsid = d.id \n" +
                "JOIN Provinces pr ON d.provincesId = pr.id \n" +
                "WHERE 1=1");

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int totalCount = 0; // Đặt giá trị mặc định

        if (searchParams.containsKey("gender")) {
            sql.append(" AND u.gender = ?");
        }
        if (searchParams.containsKey("province")) {
            sql.append(" AND pr.id = ?");
        }
        if (searchParams.containsKey("district")) {
            sql.append(" AND d.id = ?");
        }
        if (searchParams.containsKey("ward")) {
            sql.append(" AND w.id = ?");
        }

        if (searchParams.containsKey("minCleanliness") && searchParams.containsKey("maxCleanliness")) {
            sql.append(" AND p.cleanliness BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minSmoking") && searchParams.containsKey("maxSmoking")) {
            sql.append(" AND p.smoking BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minDrinking") && searchParams.containsKey("maxDrinking")) {
            sql.append(" AND p.drinking BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minInteraction") && searchParams.containsKey("maxInteraction")) {
            sql.append(" AND p.interaction BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minGuests") && searchParams.containsKey("maxGuests")) {
            sql.append(" AND p.guests BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minCooking") && searchParams.containsKey("maxCooking")) {
            sql.append(" AND p.cooking BETWEEN ? AND ?");
        }
        if (searchParams.containsKey("minPet") && searchParams.containsKey("maxPet")) {
            sql.append(" AND p.pet BETWEEN ? AND ?");
        }

        try {
            connection = DBContext.getConnection();
            ps = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (searchParams.containsKey("gender")) {
                ps.setString(paramIndex++, (String) searchParams.get("gender"));
            }
            if (searchParams.containsKey("province")) {
                int provinceId = Integer.parseInt(searchParams.get("province").toString());
                ps.setInt(paramIndex++, provinceId);
            }
            if (searchParams.containsKey("district")) {
                int districtId = Integer.parseInt(searchParams.get("district").toString());
                ps.setInt(paramIndex++, districtId);
            }
            if (searchParams.containsKey("ward")) {
                int wardId = Integer.parseInt(searchParams.get("ward").toString());
                ps.setInt(paramIndex++, wardId);
            }

            if (searchParams.containsKey("minCleanliness") && searchParams.containsKey("maxCleanliness")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minCleanliness"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxCleanliness"));
            }
            if (searchParams.containsKey("minSmoking") && searchParams.containsKey("maxSmoking")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minSmoking"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxSmoking"));
            }
            if (searchParams.containsKey("minDrinking") && searchParams.containsKey("maxDrinking")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minDrinking"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxDrinking"));
            }
            if (searchParams.containsKey("minInteraction") && searchParams.containsKey("maxInteraction")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minInteraction"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxInteraction"));
            }
            if (searchParams.containsKey("minGuests") && searchParams.containsKey("maxGuests")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minGuests"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxGuests"));
            }
            if (searchParams.containsKey("minCooking") && searchParams.containsKey("maxCooking")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minCooking"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxCooking"));
            }
            if (searchParams.containsKey("minPet") && searchParams.containsKey("maxPet")) {
                ps.setInt(paramIndex++, (Integer) searchParams.get("minPet"));
                ps.setInt(paramIndex++, (Integer) searchParams.get("maxPet"));
            }

            logger.info(sql.toString());
            resultSet = ps.executeQuery();
            if (resultSet.next()) { // Thêm kiểm tra này
                totalCount = resultSet.getInt(1);
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                logger.warning("Failed to close resources: " + e.getMessage());
            }
        }
        return totalCount;
    }


    public static void main(String[] args) {
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = userDAO.getUser(1);
        System.out.println(user.getFirstName());
    }

}
