package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.FireEquipmentDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.FireEquipment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FireEquipmentDAOImpl implements FireEquipmentDAO {

    @Override
    public List<FireEquipment> getAllFireEquipments() {
        List<FireEquipment> fireEquipments = new ArrayList<>();
        String sql = "SELECT [id],[name],[icon],[status] FROM [dbo].[FireEquipments]";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FireEquipment fireEquipment = new FireEquipment();
                fireEquipment.setId(resultSet.getInt("id"));
                fireEquipment.setName(resultSet.getString("name"));
                fireEquipment.setIcon(resultSet.getString("icon"));
                fireEquipment.setStatus(resultSet.getString("status"));
                fireEquipments.add(fireEquipment);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Throwing exception to the upper layer to handle it properly
            throw new GeneralException("Error retrieving model from the database: " + e.getMessage(), e);
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
        return fireEquipments;
    }
}