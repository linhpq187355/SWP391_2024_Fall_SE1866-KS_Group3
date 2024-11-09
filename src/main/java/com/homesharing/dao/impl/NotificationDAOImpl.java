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
import com.homesharing.dao.NotificationDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Notification;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * NotificationDAOImpl implements the NotificationDAO interface.
 * This class handles data access operations for notifications in
 * the Home Sharing System, including creating, updating, deleting,
 * and retrieving notifications.
 *
 * @see NotificationDAO
 * @see DBContext
 * @author ManhNC
 * @version 1.0
 * @since 2024-10-20
 */
public class NotificationDAOImpl extends DBContext implements NotificationDAO {

    /**
     * Adds a new notification for a specified receiver.
     *
     * @param receiverId the ID of the user receiving the notification
     * @param content the content of the notification
     * @param type the type of notification (e.g., message, alert)
     * @param url an optional URL associated with the notification
     * @return true if the notification was added successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public boolean addNotification(int receiverId, String content,String title, String type, String url) throws SQLException {
        String sql = "INSERT INTO Notifications (recieverId, content, createdDate, status, type, url, title) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, receiverId);
            preparedStatement.setString(2, content);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(4, "sent");
            preparedStatement.setString(5, type);
            preparedStatement.setString(6, url);
            preparedStatement.setString(7, title);

            // Execute the update and return true if a row was affected
            return preparedStatement.executeUpdate() > 0;
        }  catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while adding notification: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while adding notification: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while adding notification: " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed to prevent memory leaks
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);
        }
    }

    /**
     * Updates an existing notification identified by its ID.
     *
     * @param id the ID of the notification to update
     * @param content the new content for the notification
     * @param status the new status of the notification (e.g., read, unread)
     * @param type the type of notification
     * @param url an optional new URL associated with the notification
     * @return true if the notification was updated successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public boolean updateNotification(int id, String content, String status, String type, String url) throws SQLException {
        String sql = "UPDATE Notifications SET content = ?, status = ?, type = ?, url = ? WHERE id = ?";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters for the prepared statement
            preparedStatement.setString(1, content);
            preparedStatement.setString(2, status);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, url);
            preparedStatement.setInt(5, id);

            // Execute the update and return true if a row was affected
            return preparedStatement.executeUpdate() > 0;
        }catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while updating notification: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while updating notification: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while updating notification: " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed to prevent memory leaks
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);
        }
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param id the ID of the notification to delete
     * @return true if the notification was deleted successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public boolean deleteNotification(int id) throws SQLException {
        String sql = "DELETE FROM Notifications WHERE id = ?";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            // Execute the delete operation and return true if a row was affected
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while deleting notification with ID " + id + ": " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while deleting notification with ID " + id + ": " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while deleting notification with ID " + id + ": " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed to prevent memory leaks
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);
        }
    }

    /**
     * Retrieves a list of notifications for a specific receiver.
     *
     * @param receiverId the ID of the user whose notifications to retrieve
     * @return a list of notifications associated with the receiver
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<Notification> getNotificationsByReceiverId(int receiverId) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE recieverId = ? ORDER BY createdDate DESC";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement
        ResultSet resultSet = null;  // Initialize result set

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receiverId);

            // Process the result set to create Notification objects and add them to the list
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Notification notification = new Notification(
                        resultSet.getInt("id"),
                        resultSet.getInt("recieverId"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("createdDate").toLocalDateTime(),
                        resultSet.getString("status"),
                        resultSet.getString("type"),
                        resultSet.getString("url"),
                        resultSet.getString("title")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while retrieving notifications for receiver ID " + receiverId + ": " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while retrieving notifications for receiver ID " + receiverId + ": " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while retrieving notifications for receiver ID " + receiverId + ": " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed to prevent memory leaks
            if (resultSet != null) {
                resultSet.close();  // Close the result set
            }
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);
        }
        return notifications;
    }

    /**
     * Marks a notification as read by its ID.
     *
     * @param id the ID of the notification to mark as read
     * @return true if the notification was marked as read successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public boolean markAsRead(int id) throws SQLException {
        String sql = "UPDATE Notifications SET status = 'seen' WHERE id = ?";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while marking notification ID " + id + " as read: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while marking notification ID " + id + " as read: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while marking notification ID " + id + " as read: " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed to prevent memory leaks
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);
        }
    }

    /**
     * Retrieves a list of unread notifications for a specific receiver.
     *
     * @param receiverId the ID of the user whose unread notifications to retrieve
     * @return a list of unread notifications associated with the receiver
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<Notification> getUnreadNotificationsByReceiverId(int receiverId) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE recieverId = ? AND status = 'sent' ORDER BY createdDate DESC";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement
        ResultSet resultSet = null;  // Initialize result set

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receiverId);
            resultSet = preparedStatement.executeQuery();

            // Process the result set to build the list of notifications
            while (resultSet.next()) {
                Notification notification = new Notification(
                        resultSet.getInt("id"),
                        resultSet.getInt("recieverId"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("createdDate").toLocalDateTime(),
                        resultSet.getString("status"),
                        resultSet.getString("type"),
                        resultSet.getString("url"),
                        resultSet.getString("title")
                );
                notifications.add(notification);
            }
        }catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while retrieving unread notifications for receiver ID " + receiverId + ": " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while retrieving unread notifications for receiver ID " + receiverId + ": " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while retrieving unread notifications for receiver ID " + receiverId + ": " + e.getMessage(), e);
        } finally {
            // Ensure resources are closed to prevent memory leaks
            if (resultSet != null) {
                resultSet.close();  // Close the result set
            }
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);  // Close the database connection
        }

        return notifications;
    }

    /**
     * Retrieves a list of sent notifications filtered by type and receiver ID.
     *
     * @param receiverId the ID of the receiver to filter notifications
     * @param type the type of notification to filter
     * @return a list of sent notifications matching the criteria
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<Notification> getSentNotificationsByTypeAndReceiverId(int receiverId, String type) throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE recieverId = ? AND type = ? ORDER BY createdDate DESC";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement
        ResultSet resultSet = null;  // Initialize result set

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receiverId);
            preparedStatement.setString(2, type);
            resultSet = preparedStatement.executeQuery();

            // Process the result set to build the list of notifications
            while (resultSet.next()) {
                Notification notification = new Notification(
                        resultSet.getInt("id"),
                        resultSet.getInt("recieverId"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("createdDate").toLocalDateTime(),
                        resultSet.getString("status"),
                        resultSet.getString("type"),
                        resultSet.getString("url"),
                        resultSet.getString("title")
                );
                notifications.add(notification);
            }
        }  catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while retrieving sent notifications of type '" + type + "' for receiver ID " + receiverId + ": " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while retrieving sent notifications of type '" + type + "' for receiver ID " + receiverId + ": " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while retrieving sent notifications of type '" + type + "' for receiver ID " + receiverId + ": " + e.getMessage(), e);
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
        return notifications;
    }

    /**
     * Marks notifications as seen for a specific receiver and type.
     *
     * @param receiverId the ID of the user to mark notifications as seen
     * @param type the type of notifications to mark as seen
     * @return true if the notifications were marked as seen successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public boolean markAsSeenByTypeAndReceiverId(int receiverId, String type) throws SQLException {
        String sql = "UPDATE Notifications SET status = 'seen' WHERE recieverId = ? AND type = ? AND status = 'sent'";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receiverId);
            preparedStatement.setString(2, type);

            // Execute the update and return whether any rows were affected
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while marking notifications as seen for receiver ID " + receiverId + " and type '" + type + "': " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while marking notifications as seen for receiver ID " + receiverId + " and type '" + type + "': " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while marking notifications as seen for receiver ID " + receiverId + " and type '" + type + "': " + e.getMessage(), e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);
        }
    }

    /**
     * Deletes notifications filtered by type and receiver ID.
     *
     * @param receiverId the ID of the user whose notifications to delete
     * @param type the type of notifications to delete
     * @return true if the notifications were deleted successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    @Override
    public boolean deleteNotificationsByTypeAndReceiverId(int receiverId, String type) throws SQLException {
        String sql = "DELETE FROM Notifications WHERE recieverId = ? AND type = ?";
        Connection connection = null;  // Initialize connection
        PreparedStatement preparedStatement = null;  // Initialize prepared statement

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, receiverId);
            preparedStatement.setString(2, type);

            // Execute the delete and return whether any rows were affected
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Handle SQL exception specifically
            throw new GeneralException("SQL error while deleting notifications for receiver ID " + receiverId + " and type '" + type + "': " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new GeneralException("I/O error while deleting notifications for receiver ID " + receiverId + " and type '" + type + "': " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException specifically
            throw new GeneralException("Class not found error while deleting notifications for receiver ID " + receiverId + " and type '" + type + "': " + e.getMessage(), e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();  // Close the prepared statement
            }
            closeConnection(connection);
        }
    }
}
