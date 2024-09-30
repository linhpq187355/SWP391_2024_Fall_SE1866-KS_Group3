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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of UserDao interface, handling database operations related to the User entity.
 * This class interacts with the database to save a user and check if an email already exists.
 */
public class UserDAOImpl implements UserDao {

    // Logger for logging test execution
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class.getName());
    private static final List<User> userList = new ArrayList<>();


    /**
     * Saves a user to the database by executing an INSERT SQL query.
     * All database-related exceptions are caught and thrown as a runtime exception
     * to be handled in the service layer.
     *
     * @param user The user object to be saved into the database.
     * @return
     */
    @Override
    public int saveUser(User user) {
        String sql = "INSERT INTO [HSS_Users] ([firstName], [lastName], [email], [Rolesid], [status], [hashedPassword], [createdAt]) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to ensure automatic resource management
        try (Connection connection = DBContext.getConnection();
             // Create PreparedStatement with RETURN_GENERATED_KEYS to get the inserted ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the prepared statement based on the user's information
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getRolesId());
            preparedStatement.setString(5, user.getStatus());
            preparedStatement.setString(6, user.getHashedPassword());
            preparedStatement.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));
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
            throw new RuntimeException("Error saving user to the database: " + e.getMessage(), e);
        }
    }


    /**
     * Checks if an email already exists in the database by executing a SELECT COUNT SQL query.
     * Returns true if the email exists, otherwise false.
     * Any database-related exceptions are caught and thrown as a runtime exception to be handled by the service layer.
     *
     * @param email The email address to check.
     * @return True if the email exists in the database, false otherwise.
     */
    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [HSS_Users] WHERE [email] = ?";

        // Using try-with-resources to ensure automatic resource management
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the email parameter for the prepared statement
            preparedStatement.setString(1, email);

            // Execute the query to check for email existence
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if email exists
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new RuntimeException("Error checking email existence in the database", e);
        }

        // Return false if no email match is found
        return false;
    }

    @Override
    public User getUser(int id) {
        String sql = "select u.id, u.email, u.phoneNumber, u.firstName, u.lastName, u.avatar, u.dob\n" +
                "\tfrom [HSS_Users] u where u.id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the email parameter for the prepared statement
            preparedStatement.setInt(1, id);

            // Execute the query to check for email existence
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhoneNumber(resultSet.getString("phoneNumber"));
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAvatar(resultSet.getString("avatar"));
                    if (resultSet.getDate("dob") != null) {
                        user.setDob(resultSet.getDate("dob").toLocalDate());
                    } else {
                        user.setDob(null);
                    }
                    return user;
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new RuntimeException("Error checking email existence in the database", e);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        String sql = "SELECT [id], [firstName], [lastName], [email], [Rolesid], [status], [hashedPassword], [createdAt] FROM [HSS_Users] WHERE [email] = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setRolesId(resultSet.getInt("Rolesid"));
                user.setStatus(resultSet.getString("status"));
                user.setHashedPassword(resultSet.getString("hashedPassword"));
                user.setCreatedAt(resultSet.getTimestamp("createdAt").toLocalDateTime());
                return user;
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding user by email in the database: " + e.getMessage(), e);
        }

        return null; // Return null if no user is found
    }

    @Override
    public List<User> getAllUsers() {
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
                "      ,[wardsId]\n" +
                "      ,[rolesid]\n" +
                "  FROM [dbo].[HSS Users]";

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
                user.setCreatedAt(resultSet.getTimestamp("createdAt").toLocalDateTime());
                user.setStatus(resultSet.getString("status"));
                user.setVerified(resultSet.getBoolean("isVerified"));
                user.setLastModified(resultSet.getTimestamp("lastModified").toLocalDateTime());
                user.setWardsId(resultSet.getString("wardsId"));
                user.setRolesId(resultSet.getInt("Rolesid"));

                userList.add(user);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public int updateUserProfile(User user) {
        int rowsUpdated = 0;
        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                "   SET [email] = ?\n" +
                "      ,[phoneNumber] = ?\n" +
                "      ,[firstName] = ?\n" +
                "      ,[lastName] = ?\n" +
                "      ,[avatar] = ?\n" +
                "      ,[dob] = ?\n" +
                "      ,[lastModified] = GETDATE()\n" +
                " WHERE id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPhoneNumber());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getAvatar());
            statement.setDate(6, java.sql.Date.valueOf(user.getDob()));
            statement.setInt(7, user.getId());

            rowsUpdated = statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error update user profile: " + e.getMessage(), e);
        }
        return rowsUpdated;
    }

    @Override
    public String getUserAvatar(int id) {
        String sql = "select u.avatar\n" +
                "\tfrom [HSS_Users] u where u.id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the email parameter for the prepared statement
            preparedStatement.setInt(1, id);

            // Execute the query to check for email existence
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    return resultSet.getString("avatar");
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new RuntimeException("Error checking email existence in the database", e);
        }
        return null;
    }

    @Override
    public void updateUserStatus(int id, String status) {
        String sql = "UPDATE [dbo].[HSS Users]\n" +
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
        String sql = "SELECT [id], [firstName], [lastName], [email], [Rolesid], [status], [hashedPassword], [createdAt] FROM [dbo].[HSS Users] WHERE [id] = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setRolesId(resultSet.getInt("Rolesid"));
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

}
