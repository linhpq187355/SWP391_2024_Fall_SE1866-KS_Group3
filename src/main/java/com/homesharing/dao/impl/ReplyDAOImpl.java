package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Reply;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReplyDAOImpl extends DBContext implements ReplyDAO {

    @Override
    public List<Reply> getReplies(int conversationId) throws SQLException {
        String sql = "select * from Replies where conversationId = ? ";
        List<Reply> replies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, conversationId); // Set userId
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Reply reply = new Reply();
                reply.setId(resultSet.getInt("id"));
                reply.setText(resultSet.getString("reply"));
                reply.setStatus(resultSet.getString("status"));
                reply.setConversationId(resultSet.getInt("conversationId"));
                reply.setUserId(resultSet.getInt("userId"));
                reply.setContentType(resultSet.getString("contentType"));
                reply.setContentUrl(resultSet.getString("contentUrl"));
                if(resultSet.getTimestamp("time") != null) {
                    reply.setTime(resultSet.getTimestamp("time").toLocalDateTime());
                }

                replies.add(reply);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding token in the database", e);
        } finally {
            closeConnection(connection);
        }
        return replies;
    }

    public void updateStatusForLatestReply(int conversationId, int userId) throws SQLException {
        String sql = "UPDATE Replies SET status = ? WHERE id = ("
                + "SELECT TOP 1 id FROM Replies "
                + "WHERE conversationId = ? AND userId != ? AND status = ? "
                + "ORDER BY time DESC)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Thiết lập các giá trị cho PreparedStatement
            preparedStatement.setString(1, "seen"); // Trạng thái mới
            preparedStatement.setInt(2, conversationId); // conversationId
            preparedStatement.setInt(3, userId); // userId
            preparedStatement.setString(4, "sent"); // Chỉ cập nhật tin nhắn "sent" cuối cùng

            // Thực thi cập nhật
            int updatedRows = preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("Error updating status for latest reply" + e.getMessage());
            throw new GeneralException("Error updating latest reply status in the database", e);
        } finally {
            closeConnection(connection);
        }
    }


    @Override
    public void addReply(Reply reply) throws SQLException {
        String sql = "INSERT INTO [dbo].[Replies] " +
                " ([reply], [time], [status], [conversationId], [userId], [contentType], [contentUrl])" +
                " VALUES(?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters for the prepared statement
            preparedStatement.setString(1, reply.getText());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(reply.getTime()));
            preparedStatement.setString(3, reply.getStatus());
            preparedStatement.setInt(4, reply.getConversationId());
            preparedStatement.setInt(5, reply.getUserId());
            preparedStatement.setString(6, reply.getContentType());
            preparedStatement.setString(7, reply.getContentUrl());
            // Execute the update to insert the token into the database
            preparedStatement.executeUpdate();

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error inserting token into the database", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Reply getLastestReply(int conversationId) throws SQLException {
        String sql = "SELECT TOP 1 [reply], [time], [status], [userId] ,[contentType], [contentUrl]" +
                "FROM [dbo].[Replies] " +
                "WHERE [conversationId] = ? " + // Lọc theo conversationId
                "ORDER BY [time] DESC";  // Sắp xếp thời gian theo thứ tự giảm dần để lấy tin nhắn mới nhất
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, conversationId); // Thiết lập conversationId cho PreparedStatement
            resultSet = preparedStatement.executeQuery();

            // Nếu có kết quả, trả về đối tượng Reply từ bản ghi đầu tiên
            if (resultSet.next()) {
                Reply latestReply = new Reply();
                latestReply.setText(resultSet.getString("reply"));
                latestReply.setTime(resultSet.getTimestamp("time").toLocalDateTime());
                latestReply.setStatus(resultSet.getString("status"));
                latestReply.setConversationId(conversationId); // Sử dụng conversationId đã lọc
                latestReply.setUserId(resultSet.getInt("userId"));
                latestReply.setContentType(resultSet.getString("contentType"));
                latestReply.setContentUrl(resultSet.getString("contentUrl"));
                // Tính toán và cập nhật thời gian đã trôi qua
                latestReply.calculateTimeAgo();
                return latestReply;
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error fetching the latest reply from the database", e);
        } finally {
            closeConnection(connection);
        }
        return null; // Trả về null nếu không có tin nhắn nào
    }
}
