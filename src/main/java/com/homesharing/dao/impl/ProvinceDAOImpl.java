package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.ProvinceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Province;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDAOImpl implements ProvinceDAO {
    private List<Province> provinces = new ArrayList<>();

    @Override
    public List<Province> getAllProvinces() {
        String sql = "SELECT [id],[name],[status] FROM [dbo].[Provinces]";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Province province = new Province();
                province.setId(resultSet.getInt("id"));
                province.setName(resultSet.getString("name"));
                province.setStatus(resultSet.getString("status"));
                provinces.add(province);
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
        return provinces;
    }
}
