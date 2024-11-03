/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.util;

import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.service.NotificationService;
import com.homesharing.service.impl.NotificationServiceImpl;

import java.sql.SQLException;

/**
 * Utility class for adding notifications in the Home Sharing System.
 * This class follows the Singleton pattern to ensure that only one
 * instance of AddNotificationUtil is created throughout the application.
 */
public class AddNotificationUtil {

    // Singleton instance of AddNotificationUtil
    private static AddNotificationUtil instance;
    private NotificationDAO notificationDAO; // DAO for notification operations
    private NotificationService notificationService; // Service for handling notifications

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the notificationDAO and notificationService.
     */
    private AddNotificationUtil() {
        this.notificationDAO = new NotificationDAOImpl(); // Initialize the Notification DAO
        this.notificationService = new NotificationServiceImpl(notificationDAO); // Initialize the Notification Service
    }

    /**
     * Returns the singleton instance of AddNotificationUtil.
     * If the instance is null, it creates a new one.
     *
     * @return the singleton instance of AddNotificationUtil
     */
    public static AddNotificationUtil getInstance() {
        if (instance == null) {
            instance = new AddNotificationUtil(); // Create a new instance if none exists
        }
        return instance; // Return the singleton instance
    }

    /**
     * Adds a notification with the specified details.
     *
     * @param id      the ID of the user to whom the notification is directed
     * @param message the message content of the notification
     * @param type    the type of notification (e.g., alert, info)
     * @param url     the URL related to the notification, if applicable
     * @throws SQLException if a database access error occurs while adding the notification
     */
    public void addNotification(int id, String message, String title, String type, String url) throws SQLException {
        notificationService.addNotification(id,message,type,url, title);
    }
}

