package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.WishListDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.WishList;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


    public class WishListDAOImpl implements WishListDAO {
        @Override
        public int saveWishList(WishList wishlist) {
            String sql = "INSERT INTO [dbo].[Wishlists] ([userId], [homeId], [createdDate], [status]) VALUES (?, ?, ?, ?)";

            try (Connection connection = DBContext.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                // Thiết lập các giá trị cho PreparedStatement từ đối tượng wishlist
                preparedStatement.setInt(1, wishlist.getUserId());
                preparedStatement.setInt(2, wishlist.getHomeId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(wishlist.getCreatedDate())); // Convert LocalDateTime to Timestamp
                preparedStatement.setString(4, wishlist.getStatus());

                // Thực thi câu lệnh và lấy số lượng hàng bị ảnh hưởng
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    // Lấy ID được sinh ra tự động nếu có
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1);  // Trả về ID được sinh ra
                        } else {
                            throw new SQLException("Creating wishlist failed, no ID obtained.");
                        }
                    }
                } else {
                    throw new SQLException("Creating wishlist failed, no rows affected.");
                }
            } catch (SQLException | IOException | ClassNotFoundException e) {
                // Ném lại exception dưới dạng runtime exception để service layer xử lý
                throw new IllegalArgumentException("Error saving wishlist to the database: " + e.getMessage(), e);
            }
        }

        @Override
        public List<Home> getWishlistByUserId(int userId) {
            List<Home> wishlist = new ArrayList<>();
            String sql = "SELECT h.ID, h.NAME, h.ADDRESS " +
                    "FROM Homes h " +
                    "JOIN Wishlists w ON h.ID = w.homeId " +
                    "WHERE w.userId = ? AND w.status = 'active'";

            try (Connection connection = DBContext.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Home home = new Home();
                    home.setId(resultSet.getInt("ID"));
                    home.setName(resultSet.getString("NAME"));
                    home.setAddress(resultSet.getString("ADDRESS"));
                    // Set other properties of Home based on your Home class
                    wishlist.add(home);
                }
            } catch (SQLException | IOException | ClassNotFoundException e) {
                throw new IllegalArgumentException("Error retrieving wishlist: " + e.getMessage(), e);
            }
            return wishlist;
        }

        @Override
        public List<Price> getHomePricesByHomeId(int homeId) {
            String sql = "SELECT [id], [price], [createdDate] FROM [Prices] WHERE [Homesid] = ?";
            List<Price> prices = new ArrayList<>();

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = DBContext.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, homeId);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Price price = new Price();
                    price.setId(resultSet.getInt("id"));
                    price.setPrice(resultSet.getInt("price"));
                    price.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
                    prices.add(price);
                }

            } catch (SQLException | IOException | ClassNotFoundException e) {
                throw new GeneralException("Error retrieving home prices from the database: " + e.getMessage(), e);
            } finally {
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

            return prices;
        }


        @Override
        public void removeWishList(int userId, int homeId, String status) {
            String sql = "UPDATE [dbo].[Wishlists] SET status = ? WHERE userId = ? AND homeId = ?";
            try (Connection connection = DBContext.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, status);
                preparedStatement.setInt(2, userId);
                preparedStatement.setInt(3, homeId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error updating wishlist status", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

            @Override
        public void addWishList(WishList wishlist) {
            String sql = "INSERT INTO [dbo].[Wishlists] ([userId], [homeId], [createdDate], [status]) VALUES (?, ?, ?, ?)";

            try (Connection connection = DBContext.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // Thiết lập các giá trị cho PreparedStatement từ đối tượng wishlist
                preparedStatement.setInt(1, wishlist.getUserId());
                preparedStatement.setInt(2, wishlist.getHomeId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(wishlist.getCreatedDate())); // Chuyển đổi LocalDateTime thành Timestamp
                preparedStatement.setString(4, wishlist.getStatus());

                // Thực thi câu lệnh
                preparedStatement.executeUpdate();
            } catch (SQLException | IOException | ClassNotFoundException e) {
                // Ném lại exception dưới dạng runtime exception để service layer xử lý
                throw new IllegalArgumentException("Error adding wishlist to the database: " + e.getMessage(), e);
            }
        }

        @Override
        public boolean isAlreadyInWishlist(int userId, int homeId, String status) {
            String sql = "SELECT COUNT(*) FROM [dbo].[Wishlists] WHERE userId = ? AND homeId = ? AND status = ?";
            try (Connection connection = DBContext.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, homeId);
                preparedStatement.setString(3, status);

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException | IOException | ClassNotFoundException e) {
                throw new RuntimeException("Error checking wishlist in the database", e);
            }
            return false;
        }
        @Override
        public void addOrUpdateWishList(WishList wishlist) {
            if (isAlreadyInWishlist(wishlist.getUserId(), wishlist.getHomeId(), "inactive")) {
                // Nếu đã tồn tại với trạng thái "inactive", chỉ cần cập nhật lại
                removeWishList(wishlist.getUserId(), wishlist.getHomeId(), "active");
            } else {
                // Nếu chưa tồn tại, thêm mới
                addWishList(wishlist);
            }
        }

}


