package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeImageDAO;
import com.homesharing.model.HomeImage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class HomeImageDAOImp implements HomeImageDAO {

    @Override
    public int save(HomeImage image) {
        String sql = "INSERT INTO [dbo].[HomeImages]\n" +
                "           ([imgUrl]\n" +
                "           ,[Homesid])\n" +
                "     VALUES (?,?)";

        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, image.getImgUrl());
            preparedStatement.setInt(2, image.getHomeId());

            // Execute the insert statement and capture affected rows
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated Home ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);  // Return the generated Home ID
                    } else {
                        throw new SQLException("Creating model failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating model failed, no rows affected.");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving model to the database: " + e.getMessage(), e);
        }
    }
}
