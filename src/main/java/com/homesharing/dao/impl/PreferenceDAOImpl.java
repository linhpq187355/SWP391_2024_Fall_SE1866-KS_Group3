package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.PreferenceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the PreferenceDAO interface for managing user preferences.
 */
public class PreferenceDAOImpl implements PreferenceDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceDAOImpl.class);

    /**
     * Retrieves the preferences for a specific user based on their user ID.
     *
     * @param userId the ID of the user whose preferences are to be retrieved
     * @return the Preference object containing the user's preferences, or null if not found
     */
    @Override
    public Preference getPreference(int userId) {
        String sql = "SELECT * FROM Preferences WHERE usersId = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the user ID parameter for the prepared statement
            preparedStatement.setInt(1, userId);

            // Execute the query to retrieve user preferences
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Preference preference = new Preference();
                    preference.setCleanliness(resultSet.getObject("cleanliness", Integer.class) != null
                            ? resultSet.getInt("cleanliness") : 100);
                    preference.setDrinking(resultSet.getObject("drinking", Integer.class) != null
                            ? resultSet.getInt("drinking") : 100);
                    preference.setSmoking(resultSet.getObject("smoking", Integer.class) != null
                            ? resultSet.getInt("smoking") : 100);
                    preference.setInteraction(resultSet.getObject("interaction", Integer.class) != null
                            ? resultSet.getInt("interaction") : 100);
                    preference.setCooking(resultSet.getObject("cooking", Integer.class) != null
                            ? resultSet.getInt("cooking") : 100);
                    preference.setPet(resultSet.getObject("pet", Integer.class) != null
                            ? resultSet.getInt("pet") : 100);
                    return preference;
                }
            }

        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while retrieving user preferences for userId {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error retrieving user preferences from the database", e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving user preferences from the database", e);
        }
        return null;
    }

    /**
     * Updates the preferences for a user in the database.
     *
     * @param preferences a map of preference names and their corresponding values
     * @return the number of rows updated in the database
     */
    @Override
    public int updatePreference(Map<String, Integer> preferences) {
        if (preferences == null || !preferences.containsKey("usersId")) {
            LOGGER.warn("Preferences map is null or does not contain user_id. No updates will be made.");
            return 0;
        }

        int rowsUpdated = 0;
        StringBuilder sql = new StringBuilder("UPDATE [dbo].[Preferences] SET ");
        List<Integer> values = new ArrayList<>();

        // Build SQL query and collect values
        for (Map.Entry<String, Integer> entry : preferences.entrySet()) {
            sql.append(entry.getKey()).append(" = ?, ");
            values.add(entry.getValue());
        }

        // Remove the last comma and space
        if (sql.length() > 0) {
            sql.setLength(sql.length() - 2);
        }

        sql.append(" WHERE usersId = ?");
        values.add(preferences.get("usersId")); // Add the user_id at the end

        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            // Set the parameters in the PreparedStatement
            for (int i = 0; i < values.size(); i++) {
                statement.setInt(i + 1, values.get(i));
            }

            // Execute the update and get the number of rows updated
            rowsUpdated = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while updating user preferences: {}", e.getMessage());
            throw new GeneralException("Error updating user preferences: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new GeneralException("Error updating user preferences: " + e.getMessage(), e);
        }
        return rowsUpdated;
    }

    /**
     * Inserts a new preference record for a user in the database.
     *
     * @param userId the ID of the user for whom preferences are being created
     * @return the number of rows affected by the insert operation
     */
    @Override
    public int insertPreference(int userId) {
        String sql = "INSERT INTO [dbo].[Preferences] ([usersId]) VALUES (?)";
        int affectedRows = 0;

        // Using try-with-resources to ensure automatic resource management
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, userId);
            // Execute the update to insert the user into the database
            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while inserting preferences for userId {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error saving preferences to the database: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("Error saving preferences to the database: " + e.getMessage(), e);
        }
        return affectedRows;
    }


}