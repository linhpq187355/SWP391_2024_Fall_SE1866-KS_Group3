/*
 * Copyright(C) 2024, Home sharing Inc.
 * Home sharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.service.impl;

import com.homesharing.dao.NotificationDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Notification;
import com.homesharing.service.NotificationService;

import java.sql.SQLException;
import java.util.List;

/**
 * NotificationServiceImpl is the implementation of the NotificationService interface.
 * It provides the functionality to manage notifications such as adding a new notification
 * and retrieving unread notifications for a specific user.
 */
public class NotificationServiceImpl implements NotificationService {

    private final NotificationDAO notificationDAO;// Instance of NotificationDAO for data access

    /**
     * Constructor to initialize NotificationServiceImpl with a NotificationDAO instance.
     *
     * @param notificationDAO The DAO object responsible for notification data operations.
     */
    public NotificationServiceImpl(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO; // Assigning the passed DAO instance to the class member
    }

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
    @Override
    public boolean addNotification(int receiverId, String content, String type, String url, String title) throws SQLException, GeneralException {
        // Delegates the call to the DAO to add the notification
        return notificationDAO.addNotification(receiverId, content,title, type, url);
    }

    /**
     * Retrieves a list of unread notifications for a specified receiver.
     *
     * @param receiverId The ID of the user for whom to retrieve unread notifications.
     * @return A list of unread notifications associated with the receiver.
     * @throws SQLException if there is an issue with the database operation.
     * @throws GeneralException for handling other exceptions specific to the business logic.
     */
    @Override
    public List<Notification> getUnReadNotifications(int receiverId) throws SQLException, GeneralException {
        // Calls the DAO method to fetch unread notifications for the given receiver ID
        return notificationDAO.getUnreadNotificationsByReceiverId(receiverId);
    }


}
