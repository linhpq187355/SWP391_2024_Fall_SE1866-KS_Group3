/*
 * Copyright(C) 2024, Home sharing Inc.
 * Home sharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.service;

import com.homesharing.exception.GeneralException;
import com.homesharing.model.Notification;

import java.sql.SQLException;
import java.util.List;

/**
 * NotificationService interface defines the contract for notification-related operations
 * in the Home Sharing application.
 */
public interface NotificationService {

    /**
     * Adds a new notification for a specified receiver.
     *
     * @param receiverId The ID of the user receiving the notification.
     * @param content The content of the notification message.
     * @param type The type/category of the notification (e.g., alert, message, etc.).
     * @param url The URL associated with the notification for additional context or action.
     * @return true if the notification was added successfully, false otherwise.
     * @throws SQLException if there is an issue with the database operation.
     * @throws GeneralException for handling other exceptions specific to the business logic.
     */
    boolean addNotification(int receiverId, String content, String type, String url) throws SQLException, GeneralException;

    /**
     * Retrieves a list of unread notifications for a specified receiver.
     *
     * @param receiverId The ID of the user for whom to retrieve unread notifications.
     * @return A list of unread notifications associated with the receiver.
     * @throws SQLException if there is an issue with the database operation.
     * @throws GeneralException for handling other exceptions specific to the business logic.
     */
    List<Notification> getUnReadNotifications(int receiverId) throws SQLException, GeneralException;
}


