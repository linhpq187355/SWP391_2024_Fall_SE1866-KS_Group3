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

import static java.sql.Statement.RETURN_GENERATED_KEYS; // Static import for RETURN_GENERATED_KEYS


/**
 * Implementation of the UserDAO interface, handling database operations for the User entity.
 */
public class UserDAOImpl implements UserDAO {

    /**
     * Saves a user to the database by executing an INSERT SQL query.
     *
     * @param user The {@link User} object containing user details to be saved.
     * @return The generated ID of the newly created user.
     * @throws GeneralException if there is an error saving the user to the database.
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
            throw new GeneralException("Error saving user to the database: " + e.getMessage(), e);
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
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [HSS_Users] WHERE [email] = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);

            // Execute the query to check for email existence
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if email exists
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new GeneralException("Error checking email existence in the database", e);
        }

        // Return false if no email match is found
        return false;
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
        String sql = "SELECT u.id, u.email, u.phoneNumber, u.firstName, u.lastName, u.avatar, u.dob "
                + "FROM [HSS_Users] u WHERE u.id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the email parameter for the prepared statement
            preparedStatement.setInt(1, id);

            // Execute the query to check for email existence
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhoneNumber(resultSet.getString("phoneNumber"));
                    user.setFirstName(resultSet.getString("firstName"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAvatar(resultSet.getString("avatar"));
                    user.setDob(resultSet.getDate("dob") != null ? resultSet.getDate("dob").toLocalDate() : null);
                    return user;
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new GeneralException("Error checking email existence in the database", e);
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
            throw new GeneralException("Error: ",e);
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
            if (user.getDob() != null) {
                statement.setDate(6, java.sql.Date.valueOf(user.getDob()));
            } else {
                statement.setNull(6, java.sql.Types.DATE);
            }
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

    @Override
    public int resetPassword(String password, int id) {
        int rowsUpdated = 0;
        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                "   SET [hashedPassword] = ?\n"+
                " WHERE id = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){


            statement.setString(1, PasswordUtil.hashPassword(password));
            statement.setInt(2, id);

            rowsUpdated = statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error update user profile: " + e.getMessage(), e);
        }
        return rowsUpdated;
    }



}
