package com.homesharing.dao.impl;
import com.homesharing.conf.DBContext;
import com.homesharing.dao.DistrictDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.District;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DistrictDAOImpl implements DistrictDAO {
    public final String DB_RESOURCE = "Error closing database resources:";
    Logger logger = Logger.getLogger(DistrictDAOImpl.class.getName());


    @Override
    public List<District> getAllDistricts() {
        String sql = "SELECT [id],[name],[status] FROM [dbo].[Districts]";
        List<District> districts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                District district = new District();
                district.setId(resultSet.getInt("id"));
                district.setName(resultSet.getString("name"));
                district.setStatus(resultSet.getString("status"));
                districts.add(district);
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
                logger.severe(DB_RESOURCE + e.getMessage());
            }
        }
        return districts;
    }

    /**
     * Fetch a district info based on the given id
     *
     * @param id
     * @return district that you want to find
     */
    @Override
    public District getDistrictById(int id) {
        String sql = "SELECT [id],[name],[status] FROM [dbo].[Districts] WHERE id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                District district = new District();
                district.setId(resultSet.getInt("id"));
                district.setName(resultSet.getString("name"));
                district.setStatus(resultSet.getString("status"));
                return district;
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
                logger.severe(DB_RESOURCE + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public List<District> getDistrictByProvinceId(int provinceId) {
        String sql = "SELECT [id],[name] FROM [dbo].[Districts] WHERE provincesId=?";

        List<District> districts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, provinceId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                District district = new District();
                district.setId(resultSet.getInt("id"));
                district.setName(resultSet.getString("name"));
                districts.add(district);
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
                logger.severe(DB_RESOURCE + e.getMessage());
            }
        }
        return districts;
    }
}


