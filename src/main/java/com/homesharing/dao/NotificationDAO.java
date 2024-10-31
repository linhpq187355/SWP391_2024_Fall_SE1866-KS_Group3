/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.dao;

import com.homesharing.model.Notification;

import java.sql.SQLException;
import java.util.List;

/**
 * NotificationDAO interface defines the data access methods for handling notifications
 * in the Home Sharing System.
 * It provides CRUD operations and additional methods to manage user notifications.
 */
public interface NotificationDAO {

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
    boolean addNotification(int receiverId, String content, String type, String url) throws SQLException;

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
    boolean updateNotification(int id, String content, String status, String type, String url) throws SQLException;

    /**
     * Deletes a notification by its ID.
     *
     * @param id the ID of the notification to delete
     * @return true if the notification was deleted successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean deleteNotification(int id) throws SQLException;

    /**
     * Retrieves a list of notifications for a specific receiver.
     *
     * @param receiverId the ID of the user whose notifications to retrieve
     * @return a list of notifications associated with the receiver
     * @throws SQLException if a database access error occurs
     */
    List<Notification> getNotificationsByReceiverId(int receiverId) throws SQLException;

    /**
     * Marks a notification as read by its ID.
     *
     * @param id the ID of the notification to mark as read
     * @return true if the notification was marked as read successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean markAsRead(int id) throws SQLException;

    /**
     * Retrieves a list of unread notifications for a specific receiver.
     *
     * @param receiverId the ID of the user whose unread notifications to retrieve
     * @return a list of unread notifications associated with the receiver
     * @throws SQLException if a database access error occurs
     */
    List<Notification> getUnreadNotificationsByReceiverId(int receiverId) throws SQLException;

    /**
     * Retrieves a list of sent notifications filtered by type and receiver ID.
     *
     * @param receiverId the ID of the receiver to filter notifications
     * @param type the type of notification to filter
     * @return a list of sent notifications matching the criteria
     * @throws SQLException if a database access error occurs
     */
    List<Notification> getSentNotificationsByTypeAndReceiverId(int receiverId, String type) throws SQLException;

    /**
     * Marks notifications as seen for a specific receiver and type.
     *
     * @param receiverId the ID of the user to mark notifications as seen
     * @param type the type of notifications to mark as seen
     * @return true if the notifications were marked as seen successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean markAsSeenByTypeAndReceiverId(int receiverId, String type) throws SQLException;

    /**
     * Deletes notifications filtered by type and receiver ID.
     *
     * @param receiverId the ID of the user whose notifications to delete
     * @param type the type of notifications to delete
     * @return true if the notifications were deleted successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean deleteNotificationsByTypeAndReceiverId(int receiverId, String type) throws SQLException;
}