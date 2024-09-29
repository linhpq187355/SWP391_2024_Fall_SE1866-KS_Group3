package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.PreferenceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PreferenceDAOImpl implements PreferenceDAO {
    @Override
    public Preference getPreference(int userId) {
        String sql = "select * from Preferences where user_id = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the email parameter for the prepared statement
            preparedStatement.setInt(1, userId);

            // Execute the query to check for email existence
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Preference preference = new Preference();
                    preference.setCleanliness(resultSet.getInt("cleanliness"));
                    preference.setDrinking(resultSet.getInt("drinking"));
                    preference.setSmoking(resultSet.getInt("smoking"));
                    preference.setInteraction(resultSet.getInt("interaction"));
                    preference.setCooking(resultSet.getInt("cooking"));
                    preference.setPet(resultSet.getInt("pet"));
                    return preference;
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new RuntimeException("Error user existence in the database", e);
        }
        return null;
    }

    @Override
    public int updatePreference(Preference preference) {
        int rowsUpdated = 0;
        String sql = "UPDATE [dbo].[Preferences]\n" +
                "   SET [cleanliness] = ?\n" +
                "      ,[smoking] = ?\n" +
                "      ,[drinking] = ?\n" +
                "      ,[interaction] = ?\n" +
                "      ,[cooking] = ?\n" +
                "      ,[pet] = ?\n" +
                " WHERE [user_id] = ?";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){


            statement.setInt(1, preference.getCleanliness());
            statement.setInt(2, preference.getSmoking());
            statement.setInt(3, preference.getDrinking());
            statement.setInt(4, preference.getInteraction());
            statement.setInt(5, preference.getCooking());
            statement.setInt(6, preference.getPet());
            statement.setInt(7, preference.getUserId());

            rowsUpdated = statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error update user profile: " + e.getMessage(), e);
        }
        return rowsUpdated;
    }

    @Override
    public int insertPreference(Preference preference) {
        String sql = "INSERT INTO [dbo].[Preferences]\n" +
                "           ([cleanliness]\n" +
                "           ,[smoking]\n" +
                "           ,[drinking]\n" +
                "           ,[interaction]\n" +
                "           ,[cooking]\n" +
                "           ,[pet]\n" +
                "           ,[user_id])\n" +
                "     VALUES\n" +
                "           (?,?,?,?,?,?,?)";
        int affectedRows = 0;

        // Using try-with-resources to ensure automatic resource management
        try (Connection connection = DBContext.getConnection();
             // Create PreparedStatement with RETURN_GENERATED_KEYS to get the inserted ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the prepared statement based on the user's information
            preparedStatement.setInt(1, preference.getCleanliness());
            preparedStatement.setInt(2, preference.getSmoking());
            preparedStatement.setInt(3, preference.getDrinking());
            preparedStatement.setInt(4, preference.getInteraction());
            preparedStatement.setInt(5, preference.getCooking());
            preparedStatement.setInt(6, preference.getPet());
            preparedStatement.setInt(7, preference.getUserId());
            // Execute the update to insert the user into the database
            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw exceptions as runtime to be handled by the service layer
            throw new RuntimeException("Error saving preferences to the database: " + e.getMessage(), e);
        }
        return affectedRows;
    }


    public static void main(String[] args) {
        PreferenceDAOImpl preferenceDAO = new PreferenceDAOImpl();
        System.out.println(preferenceDAO.getPreference(7));
    }


}
