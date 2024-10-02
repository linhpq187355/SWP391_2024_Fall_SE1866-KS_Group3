package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.PriceDAO;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PriceDAOImpl implements PriceDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceDAOImpl.class);

    @Override
    public List<Price> getPrice(List<Home> homes) {
        if (homes == null || homes.isEmpty()) {
            LOGGER.warn("Home list is null or empty. Returning an empty price list.");
            return new ArrayList<>();
        }

        StringBuilder sql = new StringBuilder("SELECT id, price, Homesid FROM prices WHERE id IN (");
        for (int i = 0; i < homes.size(); i++) {
            sql.append("?");
            if (i < homes.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        List<Price> prices = new ArrayList<>();

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < homes.size(); i++) {
                preparedStatement.setInt(i + 1, homes.get(i).getPriceId());
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Price price = new Price();
                    price.setHomesId(resultSet.getInt("Homesid"));
                    price.setId(resultSet.getInt("id"));
                    price.setPrice(resultSet.getInt("price"));
                    prices.add(price);
                }
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error retrieving prices from the database: " + e.getMessage(), e);
        }

        return prices;
    }

    @Override
    public int savePrice(Price price) {
        String sql = "INSERT INTO [dbo].[Prices] ([price],[createdDate],[Homesid])\n" +
                "     VALUES (?,?,?)";

        // Using try-with-resources to manage the database connection and resources
        try (Connection connection = DBContext.getConnection();
             // PreparedStatement with RETURN_GENERATED_KEYS to capture the inserted Home ID
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Setting parameters for the PreparedStatement using the Home object
            preparedStatement.setInt(1, price.getPrice());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(3, price.getHomesId());

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
