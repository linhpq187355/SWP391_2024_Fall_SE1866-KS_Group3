/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.ConversationDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Conversation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the ConversationDAO interface for managing conversation-related database operations.
 * @author ManhNC
 */
public class ConversationDAOImpl extends DBContext implements ConversationDAO {
    @Override
    public List<Conversation> getAllConversationsByUserId(int userId) throws SQLException {
        String sql = "select * from Conversations " +
                "where userOne = ? or userTwo = ?  " +
                "and status = 'active' ";
        List<Conversation> conversations = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, userId); // Set userId
            preparedStatement.setInt(2, userId); // Set userId
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Conversation conversation = new Conversation();
                conversation.setId(resultSet.getInt("id"));
                conversation.setUserOne(resultSet.getInt("userOne"));
                conversation.setUserTwo(resultSet.getInt("userTwo"));
                conversation.setTime(resultSet.getTimestamp("time"));
                conversation.setStatus(resultSet.getString("status"));
                conversations.add(conversation);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding token in the database", e);
        } finally {
            closeConnection(connection);
        }
        return conversations;
    }

    @Override
    public int getConversationId(int userOne, int userTwo) throws SQLException {
        String sql = "select id from Conversations " +
                "where userOne = ? and userTwo = ?  " +
                "and status = 'active' ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, userOne); // Set userId
            preparedStatement.setInt(2, userTwo); // Set userId
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error finding conversation in the database", e);
        } finally {
            closeConnection(connection);
        }
        return 0;
    }

    @Override
    public Map<Integer, Integer> contactUsersWithConversationId(int userId) throws SQLException {
        String sql = "SELECT userOne AS userId, id FROM Conversations WHERE userTwo = ? AND status = 'active' " +
                "UNION " +
                "SELECT userTwo AS userId, id FROM Conversations WHERE userOne = ? AND status = 'active'";
        Map<Integer, Integer> contactUserIds = new HashMap<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int contactUserId = resultSet.getInt("userId");
                int conversationId = resultSet.getInt("id");
                contactUserIds.put(contactUserId, conversationId);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error fetching contact users from the database", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // Xử lý ngoại lệ hoặc log lỗi nếu cần
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // Xử lý ngoại lệ hoặc log lỗi nếu cần
                }
            }
            closeConnection(connection);
        }

        return contactUserIds;
    }

}
