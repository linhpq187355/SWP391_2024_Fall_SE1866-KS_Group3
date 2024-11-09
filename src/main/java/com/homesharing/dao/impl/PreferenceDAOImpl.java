/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second Implement
 */

package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.PreferenceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
public class PreferenceDAOImpl extends DBContext implements PreferenceDAO {
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{

            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Set the user ID parameter for the prepared statement
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
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
                preference.setGuest(resultSet.getObject("guests", Integer.class) != null
                        ? resultSet.getInt("guests") : 100);
                preference.setPet(resultSet.getObject("pet", Integer.class) != null
                        ? resultSet.getInt("pet") : 100);
                return preference;
            }


        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while retrieving user preferences for userId {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error retrieving user preferences from the database", e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving user preferences from the database", e);
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());


            // Set the parameters in the PreparedStatement
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setInt(i + 1, values.get(i));
            }

            // Execute the update and get the number of rows updated
            rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while updating user preferences: {}", e.getMessage());
            throw new GeneralException("Error updating user preferences: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new GeneralException("Error updating user preferences: " + e.getMessage(), e);
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
     * Updates the preferences for a specific user based on the Preference object.
     *
     * @param preference the Preference object containing updated preference values
     * @return the number of rows updated in the database
     */
    @Override
    public int updatePreference(Preference preference) {
        if (preference == null) {
            LOGGER.warn("Preference is null. No updates will be made.");
            return 0;
        }

        // Validate preference values
        validatePreference(preference);

        int rowsUpdated = 0;
        String sql = "UPDATE [dbo].[Preferences]\n" +
                "   SET [cleanliness] = ?\n" +
                "      ,[smoking] = ?\n" +
                "      ,[drinking] = ?\n" +
                "      ,[interaction] = ?\n" +
                "      ,[guests] = ?\n" +
                "      ,[cooking] = ?\n" +
                "      ,[pet] = ?\n" +
                " WHERE usersId = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, preference.getCleanliness());
            preparedStatement.setInt(2, preference.getSmoking());
            preparedStatement.setInt(3, preference.getDrinking());
            preparedStatement.setInt(4, preference.getInteraction());
            preparedStatement.setInt(5, preference.getGuest());
            preparedStatement.setInt(6, preference.getCooking());
            preparedStatement.setInt(7, preference.getPet());
            preparedStatement.setInt(8, preference.getUserId());

            // Execute the update and get the number of rows updated
            rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while updating user preferences: {}", e.getMessage());
            throw new GeneralException("Error updating user preferences: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new GeneralException("Error updating user preferences: " + e.getMessage(), e);
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

    private void validatePreference(Preference preference) {
        if (preference.getCleanliness() < 1 || preference.getCleanliness() > 5 ||
                preference.getSmoking() < 1 || preference.getSmoking() > 5 ||
                preference.getDrinking() < 1 || preference.getDrinking() > 5 ||
                preference.getInteraction() < 1 || preference.getInteraction() > 5 ||
                preference.getGuest() < 1 || preference.getGuest() > 5 ||
                preference.getCooking() < 1 || preference.getCooking() > 5 ||
                preference.getPet() < 1 || preference.getPet() > 5) {
            throw new IllegalArgumentException("Invalid preference values provided.");
        }
    }

    /**
     * Lists all matching preferences for users, excluding the specified user ID.
     *
     * @param userId the ID of the user to exclude from the results
     * @return a list of Preference objects that match the criteria
     */
    @Override
    public List<Preference> listMatchingPreference(int userId) {
        List<Preference> list = new ArrayList<>();
        String sql = "select *\n" +
                "from Preferences\n" +
                "where usersId in (\n" +
                "\tselect id\n" +
                "\tfrom HSS_Users\n" +
                "\twhere rolesId = 4 and id != ?\n" +
                ")";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Preference preference = new Preference();
                preference.setId(resultSet.getInt("id"));
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
                preference.setGuest(resultSet.getObject("guests", Integer.class) != null
                        ? resultSet.getInt("guests") : 100);
                preference.setPet(resultSet.getObject("pet", Integer.class) != null
                        ? resultSet.getInt("pet") : 100);
                preference.setUserId(resultSet.getInt("usersId"));
                list.add(preference);
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
        return list;
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
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
        return affectedRows;
    }


    public static void main(String[] args) {
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl();
        System.out.println(preferenceDAO.listMatchingPreference(23).get(3).getCleanliness());
    }
}