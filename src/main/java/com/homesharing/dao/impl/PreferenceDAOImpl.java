package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.PreferenceDAO;
import com.homesharing.model.Preference;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            throw new RuntimeException("Error checking email existence in the database", e);
        }
        return null;
    }
}
