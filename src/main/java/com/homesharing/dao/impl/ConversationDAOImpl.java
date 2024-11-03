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
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the ConversationDAO interface for managing conversation-related database operations.
 * This class provides concrete implementations for methods defined in the ConversationDAO interface.
 * It handles the database connection and SQL queries for conversations.
 *
 * @version 1.0
 * @since 2024-10-20
 * @author ManhNC
 */
public class ConversationDAOImpl extends DBContext implements ConversationDAO {

    /**
     * Retrieves all active conversations associated with a specific user ID.
     *
     * @param userId the ID of the user for whom to retrieve conversations
     * @return a list of Conversation objects associated with the user
     * @throws SQLException if a database access error occurs while retrieving conversations
     */
    @Override
    public List<Conversation> getAllConversationsByUserId(int userId) throws SQLException {
        // Validate input parameters
        if (userId <= 0) {
            throw new GeneralException("Invalid user ID: must be greater than zero.");
        }

        String sql = "select * from Conversations " +
                "where userOne = ? or userTwo = ? ";
        List<Conversation> conversations = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, userId); // Set userId
            preparedStatement.setInt(2, userId); // Set userId
            // Execute the query
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Conversation conversation = new Conversation();
                conversation.setId(resultSet.getInt("id"));
                conversation.setUserOne(resultSet.getInt("userOne"));
                conversation.setUserTwo(resultSet.getInt("userTwo"));
                conversation.setTime(resultSet.getTimestamp("time").toLocalDateTime());
                conversation.setStatus(resultSet.getString("status"));
                conversations.add(conversation);
            }
            return conversations;
        } catch (SQLException e) {
            String errorMessage = String.format("SQL error while retrieving conversations for user ID: %d. Reason: %s", userId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (IOException e) {
            String errorMessage = String.format("I/O error while retrieving conversations for user ID: %d. Reason: %s", userId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (ClassNotFoundException e) {
            String errorMessage = String.format("Class not found error while retrieving conversations for user ID: %d. Reason: %s", userId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } finally {
            // Ensure resources are closed
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            closeConnection(connection);
        }
    }

    /**
     * Retrieves the conversation ID for a conversation between two users.
     *
     * @param userOne the ID of the first user
     * @param userTwo the ID of the second user
     * @return the ID of the conversation, or 0 if no conversation exists
     * @throws SQLException if a database access error occurs while retrieving the conversation ID
     */
    @Override
    public int getConversationId(int userOne, int userTwo) throws SQLException {
        String sql = "select id from Conversations " +
                "where userOne = ? and userTwo = ? ";
        Connection connection = null; // Initialize connection
        PreparedStatement preparedStatement = null; // Initialize prepared statement
        ResultSet resultSet = null; // Initialize result set
        try {
            connection = getConnection(); // Get a database connection
            preparedStatement = connection.prepareStatement(sql); // Prepare SQL statement

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, userOne); // Set the first user ID
            preparedStatement.setInt(2, userTwo); // Set the second user ID

            // Execute the query and retrieve results
            resultSet = preparedStatement.executeQuery();

            // If a conversation exists, return the ID
            if (resultSet.next()) {
                return resultSet.getInt("id"); // Get conversation ID from the result set
            }

        } catch (SQLException e) {
            // Handle SQL exceptions and provide specific error message
            String errorMessage = String.format("SQL error while retrieving conversation ID for users: %d and %d. Reason: %s", userOne, userTwo, e.getMessage());
            throw new GeneralException(errorMessage, e); // Wrap and rethrow as GeneralException
        } catch (IOException e) {
            // Handle IOException and provide specific error message
            String errorMessage = String.format("I/O error while retrieving conversation ID for users: %d and %d. Reason: %s", userOne, userTwo, e.getMessage());
            throw new GeneralException(errorMessage, e); // Wrap and rethrow as GeneralException
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException and provide specific error message
            String errorMessage = String.format("Class not found error while retrieving conversation ID for users: %d and %d. Reason: %s", userOne, userTwo, e.getMessage());
            throw new GeneralException(errorMessage, e); // Wrap and rethrow as GeneralException
        } finally {
            // Close the ResultSet, PreparedStatement, and connection to release resources
            if (preparedStatement != null) {
                preparedStatement.close(); // Close the prepared statement
            }
            if (resultSet != null) {
                resultSet.close(); // Close the result set
            }
            closeConnection(connection);
        }
        return 0;
    }

    /**
     * Adds a new conversation to the database.
     *
     * @param conversation the Conversation object to be added
     * @return the ID of the newly created conversation
     * @throws SQLException if a database access error occurs while adding the conversation
     */
    @Override
    public int addConversation(Conversation conversation) throws SQLException {
        String sql = "INSERT INTO [dbo].[Conversations] ([userOne] ,[userTwo],[time],[status])" +
                " VALUES(?,?,?,?)";
        int generatedId = 0; // Variable to store the ID of the newly created conversation
        Connection connection = null; // Initialize connection
        PreparedStatement preparedStatement = null; // Initialize prepared statement

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, conversation.getUserOne());
            preparedStatement.setInt(2, conversation.getUserTwo());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(conversation.getTime()));
            preparedStatement.setString(4, conversation.getStatus());

            // Execute the update and get the generated ID
            int affectedRows = preparedStatement.executeUpdate();

            // Verify if the insert was successful
            if (affectedRows > 0) {
                // Retrieve the generated ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1); // Lấy ID mới tạo
                    }
                }
            } else {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }
        }catch (SQLException e) {
            // Handle SQL exceptions with specific message
            String errorMessage = String.format("SQL error while adding conversation between users: %d and %d. Reason: %s",
                    conversation.getUserOne(), conversation.getUserTwo(), e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (IOException e) {
            // Handle IOException with specific message
            String errorMessage = String.format("I/O error while adding conversation for users: %d and %d. Reason: %s",
                    conversation.getUserOne(), conversation.getUserTwo(), e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException with specific message
            String errorMessage = String.format("Class not found error while adding conversation for users: %d and %d. Reason: %s",
                    conversation.getUserOne(), conversation.getUserTwo(), e.getMessage());
            throw new GeneralException(errorMessage, e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close(); // Close the prepared statement
            }
            closeConnection(connection);
        }
        return generatedId;
    }

    /**
     * Retrieves a map of contact users associated with their respective conversation IDs.
     *
     * @param userId the ID of the user for whom to retrieve contact users and conversation IDs
     * @return a map where keys are user IDs of contacts and values are their respective conversation IDs
     * @throws SQLException if a database access error occurs while retrieving contact users
     */
    @Override
    public Map<Integer, Integer> contactUsersWithConversationId(int userId) throws SQLException {
        String sql = "SELECT userOne AS userId, id FROM Conversations WHERE userTwo = ? " +
                "UNION " +
                "SELECT userTwo AS userId, id FROM Conversations WHERE userOne = ? ";
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

            // Process the result set and populate the map with contact user IDs and conversation IDs
            while (resultSet.next()) {
                int contactUserId = resultSet.getInt("userId"); // Get the contact user ID
                int conversationId = resultSet.getInt("id"); // Get the conversation ID
                contactUserIds.put(contactUserId, conversationId); // Map contact user ID to conversation ID
            }
        } catch (SQLException e) {
            // SQL-specific error handling with detailed message
            String errorMessage = String.format("SQL error while fetching contact users for user ID %d. Reason: %s",
                    userId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (IOException e) {
            // Handle IOException with a specific message
            String errorMessage = String.format("I/O error while accessing contact users for user ID %d. Reason: %s",
                    userId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException with a specific message
            String errorMessage = String.format("Class not found error while retrieving contact users for user ID %d. Reason: %s",
                    userId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } finally {
            // Ensure all resources are closed to prevent memory leaks
            if (preparedStatement != null) {
                preparedStatement.close(); // Close the PreparedStatement
            }
            if (resultSet != null) {
                resultSet.close(); // Close the ResultSet
            }
            closeConnection(connection);
        }

        return contactUserIds;
    }

    /**
     * Updates the conversation status to block or unblock it based on the action parameter.
     *
     * @param conversationId the ID of the conversation to update
     * @param blockerId the ID of the user who is blocking/unblocking the conversation
     * @param action the action to perform: "block" or "unblock"
     * @return true if the conversation was successfully updated, false otherwise
     * @throws SQLException if a database access error occurs while updating the conversation
     */
    @Override
    public boolean updateConversationStatus(int conversationId, int blockerId, String action) throws SQLException {
        String sql;
        String status;

        // Determine status based on the action
        if ("block".equalsIgnoreCase(action)) {
            status = "block_by_" + blockerId; // Set status to blocked by the user
        } else if ("unblock".equalsIgnoreCase(action)) {
            status = "active"; // Reset status to active
        } else {
            throw new IllegalArgumentException("Invalid action: " + action); // Throw an error if action is invalid
        }

        sql = "UPDATE [dbo].[Conversations] SET [status] = ? WHERE [id] = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isUpdated = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters for the query
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, conversationId);

            // Execute the update and check if it affected any row
            int affectedRows = preparedStatement.executeUpdate();
            isUpdated = affectedRows > 0; // Nếu có hàng bị ảnh hưởng, set isUpdated = true

        } catch (SQLException e) {
            String errorMessage = String.format("SQL error while updating conversation with ID %d by user %d. Reason: %s",
                    conversationId, blockerId, e.getMessage());
            throw new SQLException(errorMessage, e);
        } catch (IOException e) {
            String errorMessage = String.format("I/O error while updating conversation with ID %d by user %d. Reason: %s",
                    conversationId, blockerId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (ClassNotFoundException e) {
            String errorMessage = String.format("Class not found error while updating conversation with ID %d by user %d. Reason: %s",
                    conversationId, blockerId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection(connection);
        }

        return isUpdated;
    }


    /**
     * Retrieves the conversation details based on the conversation ID.
     *
     * @param conversationId the ID of the conversation to retrieve
     * @return the Conversation object containing the conversation details, or null if no conversation exists
     * @throws SQLException if a database access error occurs while retrieving the conversation
     */
    @Override
    public Conversation getConversation(int conversationId) throws SQLException {
        String sql = "SELECT id, userOne, userTwo, time, status FROM Conversations WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection(); // Get the database connection
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, conversationId);

            // Execute the query and retrieve results
            resultSet = preparedStatement.executeQuery();

            // If the conversation exists, create and return a Conversation object
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userOne = resultSet.getInt("userOne");
                int userTwo = resultSet.getInt("userTwo");
                Timestamp time = resultSet.getTimestamp("time");
                String status = resultSet.getString("status");

                // Create and return the Conversation object
                return new Conversation(id, userOne, userTwo, time.toLocalDateTime(), status);
            }

        } catch (SQLException e) {
            String errorMessage = String.format("SQL error while retrieving conversation with ID: %d. Reason: %s",
                    conversationId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (IOException e) {
            String errorMessage = String.format("I/O error while retrieving conversation with ID: %d. Reason: %s",
                    conversationId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } catch (ClassNotFoundException e) {
            String errorMessage = String.format("Class not found error while retrieving conversation with ID: %d. Reason: %s",
                    conversationId, e.getMessage());
            throw new GeneralException(errorMessage, e);
        } finally {
            // Close resources
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            closeConnection(connection);
        }
        return null; // Return null if no conversation is found
    }


}
