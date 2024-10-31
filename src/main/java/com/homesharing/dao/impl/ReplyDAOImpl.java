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
import com.homesharing.dao.ReplyDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Reply;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the ReplyDAO interface for managing replies-related database operations.
 * @author ManhNC
 */
public class ReplyDAOImpl extends DBContext implements ReplyDAO {

    /**
     * Retrieves a list of replies from the database based on the conversation ID.
     *
     * @param conversationId The ID of the conversation for which replies are to be fetched.
     * @return A list of Reply objects that belong to the specified conversation.
     * @throws SQLException If there is an error during the database query.
     */
    @Override
    public List<Reply> getReplies(int conversationId) throws SQLException {
        // SQL query to select replies based on conversation ID
        String sql = "SELECT * FROM Replies WHERE conversationId = ?";
        List<Reply> replies = new ArrayList<>();
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement
        ResultSet resultSet = null;  // Initialize result set

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // Set parameters for the prepared statement
            preparedStatement.setInt(1, conversationId); // Set conversationId

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                Reply reply = new Reply();
                reply.setId(resultSet.getInt("id"));
                reply.setText(resultSet.getString("reply"));
                reply.setStatus(resultSet.getString("status"));
                reply.setConversationId(resultSet.getInt("conversationId"));
                reply.setUserId(resultSet.getInt("userId"));
                reply.setContentType(resultSet.getString("contentType"));
                reply.setContentUrl(resultSet.getString("contentUrl"));
                if (resultSet.getTimestamp("time") != null) {
                    reply.setTime(resultSet.getTimestamp("time").toLocalDateTime());
                }

                replies.add(reply);
            }
        } catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while retrieving replies for conversation ID " + conversationId + ": " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while retrieving replies for conversation ID " + conversationId + ": " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while retrieving replies for conversation ID " + conversationId + ": " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed to prevent memory leaks
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);  // Close the database connection
        }
        return replies;
    }

    /**
     * Updates the status of the latest reply for a specific conversation for a given user.
     *
     * @param conversationId The ID of the conversation containing the latest reply.
     * @param userId        The ID of the user whose latest reply status will be updated.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public void updateStatusForLatestReply(int conversationId, int userId) throws SQLException {
        String sql = "UPDATE Replies SET status = ? WHERE id = ("
                + "SELECT TOP 1 id FROM Replies "
                + "WHERE conversationId = ? AND userId != ? AND status = ? "
                + "ORDER BY time DESC)";

        Connection connection = null;  // Declare Connection variable
        PreparedStatement preparedStatement = null;  // Declare PreparedStatement variable

        try {
            // Obtain connection from the getConnection() method
            connection = getConnection();

            // Create PreparedStatement from the connection
            preparedStatement = connection.prepareStatement(sql);

            // Set values for the PreparedStatement
            preparedStatement.setString(1, "seen"); // New status
            preparedStatement.setInt(2, conversationId); // Conversation ID
            preparedStatement.setInt(3, userId); // User ID
            preparedStatement.setString(4, "sent"); // Only update the latest "sent" message

            // Execute the update and store the number of updated rows
            int updatedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Throw specific error for SQLException
            throw new GeneralException("SQL error while updating status for latest reply: " + e.getMessage(), e);
        } catch (IOException e) {
            // Throw specific error for IOException
            throw new GeneralException("I/O error while updating status for latest reply: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Throw specific error for ClassNotFoundException
            throw new GeneralException("Class not found error while updating status for latest reply: " + e.getMessage(), e);
        } finally {
            // Close PreparedStatement if it is not null
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection(connection);
        }
    }


    /**
     * Adds a new reply to the database.
     *
     * @param reply The Reply object containing the information of the reply to be added.
     * @return The ID of the newly added reply.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public int addReply(Reply reply) throws SQLException {
        // SQL query to insert a new reply into the Replies table
        String sql = "INSERT INTO [dbo].[Replies] " +
                " ([reply], [time], [status], [conversationId], [userId], [contentType], [contentUrl])" +
                " VALUES(?,?,?,?,?,?,?)";

        Connection connection = null;  // Declare Connection variable
        PreparedStatement preparedStatement = null;  // Declare PreparedStatement variable
        ResultSet generatedKeys = null;  // Declare ResultSet to hold generated keys
        int replyId = -1;  // Variable to store the generated reply ID

        try {
            // Obtain a connection from the getConnection() method
            connection = getConnection();

            // Prepare the SQL statement to insert a reply, requesting generated keys
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // Set parameters for the PreparedStatement using the Reply object's properties
            preparedStatement.setString(1, reply.getText()); // Set the reply text
            preparedStatement.setTimestamp(2, Timestamp.valueOf(reply.getTime())); // Set the timestamp of the reply
            preparedStatement.setString(3, reply.getStatus()); // Set the status of the reply
            preparedStatement.setInt(4, reply.getConversationId()); // Set the associated conversation ID
            preparedStatement.setInt(5, reply.getUserId()); // Set the user ID of the reply sender
            preparedStatement.setString(6, reply.getContentType()); // Set the content type (e.g., text, image)
            preparedStatement.setString(7, reply.getContentUrl()); // Set the content URL, if applicable

            // Execute the update to insert the reply into the database
            preparedStatement.executeUpdate();

            // Retrieve the generated keys (i.e., the ID of the newly inserted reply)
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                replyId = generatedKeys.getInt(1); // Get the generated ID
            } else {
                // If no ID was generated, log an error or handle the case appropriately
                throw new GeneralException("No generated key returned for the new reply");
            }

        } catch (SQLException e) {
            // Handle SQL exceptions specifically, indicating issues with database operations
            throw new GeneralException("SQL error while inserting reply into the database: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException, which may occur in the context of I/O operations
            throw new GeneralException("I/O error while inserting reply into the database: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException, which can occur if the JDBC driver class is not found
            throw new GeneralException("Class not found error while inserting reply into the database: " + e.getMessage(), e);
        } finally {
            // Close ResultSet if it is not null
            if (generatedKeys != null) {
                generatedKeys.close();
            }
            // Close PreparedStatement if it is not null
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection(connection);
        }
        return replyId; // Return the generated reply ID
    }


    /**
     * Retrieves replies for a specific conversation filtered by content type.
     *
     * @param conversationId The ID of the conversation.
     * @param contentType    The type of content to filter replies (e.g., text, image).
     * @return A list of Reply objects that match the given conversation ID and content type.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public List<Reply> getRepliesByConversationIdAndContentType(int conversationId, String contentType) throws SQLException {
        // SQL query to select replies based on conversation ID and optionally by content type
        String sql = "SELECT [reply], [time], [status], [conversationId], [userId], [contentType], [contentUrl] " +
                "FROM [dbo].[Replies] " +
                "WHERE [conversationId] = ? " +
                (contentType != null ? "AND [contentType] = ?" : "AND [contentType] IS NULL");

        Connection connection = null;  // Declare Connection variable
        PreparedStatement preparedStatement = null;  // Declare PreparedStatement variable
        ResultSet resultSet = null;  // Declare ResultSet variable to hold query results
        List<Reply> replies = new ArrayList<>();  // List to store replies retrieved from the database

        try {
            // Obtain a connection from the getConnection() method
            connection = getConnection();

            // Prepare the SQL statement to retrieve replies
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, conversationId); // Set the conversation ID
            if (contentType != null) {
                preparedStatement.setString(2, contentType); // Set content type if it is not null
            }

            // Execute the query to retrieve replies
            resultSet = preparedStatement.executeQuery();

            // Process the result set to populate the replies list
            while (resultSet.next()) {
                Reply reply = new Reply(); // Create a new Reply object
                reply.setText(resultSet.getString("reply")); // Set the reply text
                reply.setTime(resultSet.getTimestamp("time").toLocalDateTime()); // Set the timestamp of the reply
                reply.setStatus(resultSet.getString("status")); // Set the status of the reply
                reply.setConversationId(resultSet.getInt("conversationId")); // Set the conversation ID
                reply.setUserId(resultSet.getInt("userId")); // Set the user ID of the reply sender
                reply.setContentType(resultSet.getString("contentType")); // Set the content type of the reply
                reply.setContentUrl(resultSet.getString("contentUrl")); // Set the content URL, if applicable

                replies.add(reply); // Add the reply to the list
            }

        } catch (SQLException e) {
            // Handle SQL exceptions specifically, indicating issues with database operations
            throw new GeneralException("SQL error while retrieving replies from the database: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException, which may occur in the context of I/O operations
            throw new GeneralException("I/O error while retrieving replies from the database: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException, which can occur if the JDBC driver class is not found
            throw new GeneralException("Class not found error while retrieving replies from the database: " + e.getMessage(), e);
        } finally {
            // Close ResultSet if it is not null
            if (resultSet != null) {
                resultSet.close();
            }
            // Close PreparedStatement if it is not null
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection(connection);
        }
        return replies; // Return the list of replies retrieved from the database
    }


    /**
     * Fetches the latest reply for a specific conversation.
     *
     * @param conversationId The ID of the conversation whose latest reply is to be fetched.
     * @return The latest Reply object for the specified conversation.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public Reply getLastestReply(int conversationId) throws SQLException {
        // SQL query to select the latest reply for a specific conversation
        String sql = "SELECT TOP 1 [reply], [time], [status], [userId], [contentType], [contentUrl] " +
                "FROM [dbo].[Replies] " +
                "WHERE [conversationId] = ? " + // Filter replies by conversationId
                "ORDER BY [time] DESC"; // Order by time in descending order to get the most recent reply

        Connection connection = null; // Declare Connection variable
        PreparedStatement preparedStatement = null; // Declare PreparedStatement variable
        ResultSet resultSet = null; // Declare ResultSet variable to hold query results

        try {
            // Obtain a connection from the getConnection() method
            connection = getConnection();

            // Prepare the SQL statement to retrieve the latest reply
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, conversationId); // Set the conversationId for the PreparedStatement

            // Execute the query to get the latest reply
            resultSet = preparedStatement.executeQuery();

            // If there is a result, return the Reply object from the first record
            if (resultSet.next()) {
                Reply latestReply = new Reply(); // Create a new Reply object
                latestReply.setText(resultSet.getString("reply")); // Set the reply text
                latestReply.setTime(resultSet.getTimestamp("time").toLocalDateTime()); // Set the timestamp of the reply
                latestReply.setStatus(resultSet.getString("status")); // Set the status of the reply
                latestReply.setConversationId(conversationId); // Set the conversationId used for filtering
                latestReply.setUserId(resultSet.getInt("userId")); // Set the user ID of the reply sender
                latestReply.setContentType(resultSet.getString("contentType")); // Set the content type of the reply
                latestReply.setContentUrl(resultSet.getString("contentUrl")); // Set the content URL, if applicable

                // Calculate and update the time elapsed since the reply was sent
                latestReply.calculateTimeAgo();
                return latestReply; // Return the latest reply object
            }
        } catch (SQLException e) {
            // Handle SQL exceptions specifically, indicating issues with database operations
            throw new GeneralException("SQL error while fetching the latest reply from the database: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException, which may occur in the context of I/O operations
            throw new GeneralException("I/O error while fetching the latest reply from the database: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException, which can occur if the JDBC driver class is not found
            throw new GeneralException("Class not found error while fetching the latest reply from the database: " + e.getMessage(), e);
        } finally {
            // Close PreparedStatement if it is not null
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection(connection);
        }
        return null; // Return null if no replies are found
    }


    /**
     * Counts the number of new messages for a specific user.
     * This method retrieves the count of messages that have been sent in conversations
     * involving the specified user but have not yet been seen by that user. A message
     * is considered new if it has been sent after the last seen message or the last
     * message sent by the user in the same conversation.
     *
     * @param userId the ID of the user for whom to count new messages
     * @return the count of new messages that the specified user has received
     * @throws SQLException if a database access error occurs
     * @throws GeneralException if there are issues retrieving the connection or executing the SQL query
     */
    @Override
    public int countNewMessages(int userId) throws SQLException {
        int newMessageCount = 0; // Initialize the count of new messages

        // SQL query to count new messages based on specific criteria
        String sql = "SELECT COUNT(*) AS NewMessageCount " +
                "FROM Replies r " +
                "JOIN Conversations c ON r.conversationId = c.id " +
                "WHERE (c.userOne = ? OR c.userTwo = ?) " + // Check if the user is part of the conversation
                "AND r.userId != ? " + // Exclude messages sent by the user
                "AND r.status = 'sent' " + // Only count messages that have been sent
                "AND r.time > COALESCE((SELECT MAX(time) " +
                "                       FROM Replies r2 " +
                "                       WHERE r2.conversationId = r.conversationId " +
                "                       AND r2.userId != ? " + // Last seen message by the other participant
                "                       AND r2.status = 'seen'), '1970-01-01') " + // Default to '1970-01-01' if no seen message exists
                "AND r.time > COALESCE((SELECT MAX(time) " +
                "                       FROM Replies r3 " +
                "                       WHERE r3.conversationId = r.conversationId " +
                "                       AND r3.userId = ?), '1970-01-01');"; // Last message sent by the user

        Connection connection = null; // Declare Connection variable
        PreparedStatement preparedStatement = null; // Declare PreparedStatement variable
        ResultSet resultSet = null; // Declare ResultSet variable to hold query results

        try {
            // Obtain a connection from the getConnection() method
            connection = getConnection();

            // Prepare the SQL statement for execution
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, userId); // Set userId for checking userOne
            preparedStatement.setInt(2, userId); // Set userId for checking userTwo
            preparedStatement.setInt(3, userId); // Exclude messages sent by this user
            preparedStatement.setInt(4, userId); // Set userId for checking last seen message
            preparedStatement.setInt(5, userId); // Set userId for checking last message sent by this user

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // If there is a result, get the count of new messages
            if (resultSet.next()) {
                newMessageCount = resultSet.getInt("NewMessageCount"); // Retrieve the count from the result set
            }
        } catch (SQLException e) {
            // Handle SQL exceptions specifically, indicating issues with database operations
            throw new GeneralException("SQL error counting new messages for user ID " + userId + ": " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException, which may occur in the context of I/O operations
            throw new GeneralException("I/O error while counting new messages for user ID " + userId + ": " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException, which can occur if the JDBC driver class is not found
            throw new GeneralException("Class not found error while counting new messages for user ID " + userId + ": " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed to avoid memory leaks
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection(connection);
        }

        return newMessageCount; // Return the total count of new messages
    }

}
