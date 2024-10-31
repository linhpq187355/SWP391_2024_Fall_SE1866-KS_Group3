package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeImageDAO;
import com.homesharing.model.Home;
import com.homesharing.model.HomeImage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HomeImageDAOImpl implements HomeImageDAO {

    /**
     * get all images via home id
     *
     * @param homeId
     * @return image list
     */
    @Override
    public List<HomeImage> getImgByHomeId(int homeId) {
        if (homeId <= 0) {
            throw new IllegalArgumentException("Invalid homeId: " + homeId);
        }

        List<HomeImage> imgList = new ArrayList<>();

        String sql = "SELECT [id],[imgUrl],[imgType],[status] FROM [dbo].[HomeImages]\n" +
                "WHERE [Homesid] = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, homeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    HomeImage img = new HomeImage();
                    img.setImgUrl(resultSet.getString("imgUrl"));
                    img.setStatus(resultSet.getString("status"));
                    img.setType(resultSet.getString("imgType"));
                    img.setHomeId(homeId);
                    imgList.add(img);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error retrieving images for homeId: " + homeId, e);
        }
        return imgList;
    }


    /**
     * Get the images via the given homes
     *
     * @param homes
     * @return image list
     */
    @Override
    public List<HomeImage> getImgByHomes(List<Home> homes) {
        return List.of();
    }

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

    /**
     * Clear all the home image based on the home id
     *
     * @param homeId
     * @return int status indicate that the task is successful or not
     */
    @Override
    public int clear(int homeId) {
        String sql = "DELETE FROM [dbo].[HomeImages] WHERE [homesId] = ?";
        int affectedRows = 0; // To keep track of the number of affected rows
        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Setting the homeId parameter for the PreparedStatement
            preparedStatement.setInt(1, homeId);
            // Execute the delete statement and capture affected rows
            affectedRows = preparedStatement.executeUpdate();
            // Check if any rows were affected
            if (affectedRows == 0) {
                throw new SQLException("No amenities found for the specified home ID: " + homeId);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error clearing amenities from home ID " + homeId + ": " + e.getMessage(), e);
        }
        return affectedRows; // Return the number of affected rows
    }
}
