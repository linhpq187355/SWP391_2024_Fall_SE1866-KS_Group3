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
        String sql = "SELECT * FROM [dbo].[Wards] WHERE id=?";

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
                ward.setDistrictId(resultSet.getInt("Districtsid"));
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

    @Override
    public int getProvinceIdByWardId(int wardId) {
        String sql = "select p.id\n" +
                "from Wards w\n" +
                "left join Districts d on d.id = w.Districtsid\n" +
                "left join Provinces p on p.id = d.provincesId\n" +
                "where w.id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, wardId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error retrieving from the database: " + e.getMessage(), e);
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
        return 0;
    }

    public static void main(String[] args) {
        WardDAOImpl dao = new WardDAOImpl();
        System.out.println(dao.getProvinceIdByWardId(5));
    }
}
