package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.WardDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Province;
import com.homesharing.model.Ward;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WardDAOImpl implements WardDAO {
    private List<Ward> wards = new ArrayList<>();

    @Override
    public List<Ward> getAllWards() {
        String sql = "SELECT [id],[name],[status] FROM [dbo].[Wards]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ward ward = new Ward();
                ward.setId(resultSet.getInt("id"));
                ward.setName(resultSet.getString("name"));
                ward.setStatus(resultSet.getString("status"));
                wards.add(ward);
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
        return wards;
    }
}
