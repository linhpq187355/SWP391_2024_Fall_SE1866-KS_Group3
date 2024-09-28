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

public class DistrictDAOImpl implements DistrictDAO {
    private List<District> districts = new ArrayList<District>();

    @Override
    public List<District> getAllDistricts() {
        String sql = "SELECT [id],[name],[status] FROM [dbo].[Districts]";

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
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return districts;
    }

    @Override
    public List<District> getDistrictByProvinceId(int provinceId) {
        String sql = "SELECT d.id, d.name, d.status\nFROM Districts d\nINNER JOIN Provinces p ON d.provincesId = p.id\nWHERE p.id = ?;";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, provinceId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    District district = new District();
                    district.setProvinceId(provinceId);
                    district.setName(resultSet.getString("name"));
                    district.setStatus(resultSet.getString("status"));
                    districts.add(district);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Re-throw as runtime exception to be handled by the service layer
            throw new IllegalArgumentException("Error saving home to the database: " + e.getMessage(), e);
        }
        return null;
    }
}
