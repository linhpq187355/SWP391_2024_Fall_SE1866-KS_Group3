package com.homesharing.dao.impl;
import com.homesharing.conf.DBContext;
import com.homesharing.dao.AmentityDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Amentity;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class AmentityDAOImpl implements AmentityDAO {

    @Override
    public List<Amentity> getAllAmentities() {
        List<Amentity> amentities = new ArrayList<>();
        String sql = "SELECT [id],[name],[icon],[status] FROM [dbo].[Amenities]";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Amentity amentity = new Amentity();
                amentity.setId(resultSet.getInt("id"));
                amentity.setName(resultSet.getString("name"));
                amentity.setIcon(resultSet.getString("icon"));
                amentity.setStatus(resultSet.getString("status"));
                amentities.add(amentity);
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
        return amentities;
    }


}