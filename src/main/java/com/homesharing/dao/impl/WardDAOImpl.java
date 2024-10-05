package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.WardDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Ward;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WardDAOImpl implements WardDAO {


    @Override
    public List<Ward> getAllWards() {
        String sql = "SELECT [id],[name],[status] FROM [dbo].[Wards]";

        List<Ward> wards = new ArrayList<>();
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

    @Override
    public List<Ward> getWardsByDistrictId(int districtId) {
        String sql = "SELECT [id],[name] FROM [dbo].[Wards] \n" +
                "WHERE Districtsid = ? AND status='active'";

        List<Ward> wards = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, districtId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                    Ward ward = new Ward();
                    ward.setId(resultSet.getInt("id"));
                    ward.setName(resultSet.getString("name"));
                    wards.add(ward);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving to the database: " + e.getMessage(), e);
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

    @Override
    public Ward getWardById(int id) {
        String sql = "SELECT [id],[name],[status] FROM [dbo].[Wards] WHERE id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Ward ward = new Ward();
                ward.setId(resultSet.getInt("id"));
                ward.setName(resultSet.getString("name"));
                ward.setStatus(resultSet.getString("status"));
                return ward;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving to the database: " + e.getMessage(), e);
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
        return null;
    }
}
