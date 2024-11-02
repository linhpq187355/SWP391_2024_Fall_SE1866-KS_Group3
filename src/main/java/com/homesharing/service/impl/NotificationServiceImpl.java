package com.homesharing.service.impl;

import com.homesharing.dao.NotificationDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Notification;
import com.homesharing.service.NotificationService;

import java.sql.SQLException;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {

    private final NotificationDAO notificationDAO;

    public NotificationServiceImpl(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @Override
    public boolean markNotification(int id) throws SQLException, GeneralException {
        return notificationDAO.markAsRead(id);
    }

    @Override
    public boolean deleteNotification(int id) throws SQLException, GeneralException {
        return notificationDAO.deleteNotification(id);
    }

    @Override
    public boolean updateNotification(int id, String content, String status, String type, String url) throws SQLException, GeneralException {
        return notificationDAO.updateNotification(id, content, status, type, url);
    }

    @Override
    public boolean addNotification(int receiverId, String content, String type, String url) throws SQLException, GeneralException {
        return notificationDAO.addNotification(receiverId, content, type, url);
    }

    @Override
    public List<Notification> getNotifications(int receiverId) throws SQLException, GeneralException {
        return notificationDAO.getNotificationsByReceiverId(receiverId);
    }

    @Override
    public List<Notification> getUnReadNotifications(int receiverId) throws SQLException, GeneralException {
        return notificationDAO.getUnreadNotificationsByReceiverId(receiverId);
    }


}
