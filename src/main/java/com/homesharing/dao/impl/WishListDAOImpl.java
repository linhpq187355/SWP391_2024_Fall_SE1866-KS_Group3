package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.WishListDAO;
import com.homesharing.exception.GeneralException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WishListDAOImpl implements WishListDAO {


    @Override
    public boolean addToWishlist(int homeId, int userId) {
        String sql = "INSERT INTO Wishlists (homeId, userId) VALUES (?, ?)";


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, homeId);
            preparedStatement.setInt(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có hàng được thêm vào

        } catch (SQLException | IOException | ClassNotFoundException e) {
            // Ném ngoại lệ để lớp trên xử lý đúng cách
            throw new GeneralException("Error adding to wishlist: " + e.getMessage(), e);
        } finally {
            // Đóng tài nguyên theo thứ tự ngược lại
            try {
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
    }
}
