package com.homesharing.service;

import com.homesharing.exception.GeneralException;
import com.homesharing.model.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationService {
    boolean markNotification(int id) throws SQLException, GeneralException;

    boolean deleteNotification(int id) throws SQLException, GeneralException;

    boolean updateNotification(int id, String content, String status, String type, String url) throws SQLException, GeneralException;

    boolean addNotification(int receiverId, String content, String type, String url) throws SQLException, GeneralException;

    List<Notification> getNotifications(int receiverId) throws SQLException, GeneralException;

    List<Notification> getUnReadNotifications(int receiverId) throws SQLException, GeneralException;
}


