package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.PriceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PriceDAOImpl implements the PriceDAO interface to provide
 * database operations related to Price entities.
 */
public class PriceDAOImpl extends DBContext implements PriceDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriceDAOImpl.class);

    /**
     * Retrieves a list of Price objects for the given list of Home objects.
     *
     * @param homes a list of Home objects for which the prices are to be retrieved
     * @return a list of Price objects corresponding to the given homes
     */
    @Override
    public List<Price> getPrices(List<Home> homes) {
        // Validate input
        if (homes == null || homes.isEmpty()) {
            LOGGER.warn("Home list is null or empty. Returning an empty price list.");
            return new ArrayList<>();
        }

        // Construct SQL query with placeholders for prepared statement
        StringBuilder sql = new StringBuilder("SELECT id, price, Homesid FROM prices WHERE id IN (");

        // Append placeholders for each home price ID
        for (int i = 0; i < homes.size(); i++) {
            sql.append("?");
            if (i < homes.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");


        List<Price> prices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql.toString());


            // Set the price ID parameters in the prepared statement
            for (int i = 0; i < homes.size(); i++) {
                preparedStatement.setInt(i + 1, homes.get(i).getPriceId());
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Price price = new Price();
                price.setHomesId(resultSet.getInt("Homesid"));
                price.setId(resultSet.getInt("id"));
                price.setPrice(resultSet.getInt("price"));
                prices.add(price);
            }

        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while retrieving prices from the database: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving prices from the database: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving prices from the database: " + e.getMessage(), e);
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

        return prices;
    }
}