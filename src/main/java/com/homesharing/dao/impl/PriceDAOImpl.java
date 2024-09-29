package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.PriceDAO;
import com.homesharing.model.Home;
import com.homesharing.model.Price;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PriceDAOImpl implements PriceDAO {
    @Override
    public List<Price> getPrice(List<Home> homes) {
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

            // Đặt giá trị cho các homeId
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

    public List<Price> getPriced() {
        String sql = "SELECT * FROM prices  ";

        List<Price> prices = new ArrayList<>();

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

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

    public static void main(String[] args) {
        PriceDAOImpl price = new PriceDAOImpl();
        System.out.println(price.getPriced().size());

    }
}
