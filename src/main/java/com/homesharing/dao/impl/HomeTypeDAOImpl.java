package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.HomeTypeDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.HomeType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeTypeDAOImpl implements HomeTypeDAO {
    @Override
    public List<HomeType> getAllHomeTypes() {
        List<HomeType> hometypes = new ArrayList<>();
        String sql = "SELECT [id]\n" +
                "      ,[name]\n" +
                "      ,[description]\n" +
                "      ,[status]\n" +
                "      ,[icon]\n" +
                "  FROM [dbo].[HomeTypes]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HomeType hometype = new HomeType();
                hometype.setId(resultSet.getInt("id"));
                hometype.setName(resultSet.getString("name"));
                hometype.setDescription(resultSet.getString("description"));
                hometype.setStatus(resultSet.getString("status"));
                hometype.setIcon(resultSet.getString("icon"));
                hometypes.add(hometype);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Throwing exception to the upper layer to handle it properly
            throw new GeneralException("Error retrieving homes from the database: " + e.getMessage(), e);
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
        return hometypes;
    }
}
