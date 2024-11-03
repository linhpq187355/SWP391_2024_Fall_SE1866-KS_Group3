package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationDAOImplTest {
    private NotificationDAOImpl notificationDAO;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private MockedStatic<DBContext> mockedDBContext;

    @BeforeEach
    public void setUp() {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        mockedDBContext = Mockito.mockStatic(DBContext.class);
        mockedDBContext.when(DBContext::getConnection).thenReturn(connection);
        notificationDAO = new NotificationDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    void testAddNotification_Success() throws SQLException {
        // Arrange
        int receiverId = 1;
        String content = "Test notification";
        String type = "info";
        String url = "http://example.com";

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate a successful insert

        // Act
        boolean result = notificationDAO.addNotification(receiverId, content,"Hi", type, url);

        // Assert
        assertTrue(result);
        verify(preparedStatement).setInt(1, receiverId);
        verify(preparedStatement).setString(2, content);
        verify(preparedStatement).setString(5, type);
        verify(preparedStatement).setString(6, url);
    }

    @Test
    void testAddNotification_SqlException() throws SQLException {
        // Arrange
        int receiverId = 1;
        String content = "Test notification";
        String type = "info";
        String url = "http://example.com";

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> {
            notificationDAO.addNotification(receiverId, content,"Hi", type, url);
        });
        assertTrue(exception.getMessage().contains("SQL error while adding notification: Database error"));
    }

    @Test
    void testUpdateNotification_Success() throws SQLException {
        // Arrange
        int id = 1;
        String content = "Updated notification";
        String status = "updated";
        String type = "info";
        String url = "http://example.com";

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate a successful update

        // Act
        boolean result = notificationDAO.updateNotification(id, content, status, type, url);

        // Assert
        assertTrue(result);
        verify(preparedStatement).setString(1, content);
        verify(preparedStatement).setString(2, status);
        verify(preparedStatement).setString(3, type);
        verify(preparedStatement).setString(4, url);
        verify(preparedStatement).setInt(5, id);
    }

    @Test
    void testUpdateNotification_SqlException() throws SQLException {
        // Arrange
        int id = 1;
        String content = "Updated notification";
        String status = "updated";
        String type = "info";
        String url = "http://example.com";

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> {
            notificationDAO.updateNotification(id, content, status, type, url);
        });
        assertTrue(exception.getMessage().contains("SQL error while updating notification: Database error"));
    }

    @Test
    void testDeleteNotification_Success() throws SQLException {
        // Arrange
        int id = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful deletion

        // Act
        boolean result = notificationDAO.deleteNotification(id);

        // Assert
        assertTrue(result);
        verify(preparedStatement).setInt(1, id);
    }

    @Test
    void testDeleteNotification_NoRowsAffected() throws SQLException {
        // Arrange
        int id = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Simulate no rows deleted

        // Act
        boolean result = notificationDAO.deleteNotification(id);

        // Assert
        assertTrue(!result); // Should return false if no rows affected
        verify(preparedStatement).setInt(1, id);
    }

    @Test
    void testDeleteNotification_SqlException() throws SQLException {
        // Arrange
        int id = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> {
            notificationDAO.deleteNotification(id);
        });
        assertTrue(exception.getMessage().contains("SQL error while deleting notification with ID 1: Database error"));
    }

    @Test
    void testGetNotificationsByReceiverId_Success() throws SQLException {
        // Arrange
        int receiverId = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(101);
        when(resultSet.getInt("recieverId")).thenReturn(receiverId);
        when(resultSet.getString("content")).thenReturn("Content 1");
        when(resultSet.getTimestamp("createdDate")).thenReturn(Timestamp.valueOf("2024-10-30 12:00:00"));
        when(resultSet.getString("status")).thenReturn("status1");
        when(resultSet.getString("type")).thenReturn("type1");
        when(resultSet.getString("url")).thenReturn("url1");

        // Act
        List<Notification> result = notificationDAO.getNotificationsByReceiverId(receiverId);

        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getId());
    }

    @Test
    void testGetNotificationsByReceiverId_NoNotifications() throws SQLException {
        // Arrange
        int receiverId = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // No notifications

        // Act
        List<Notification> result = notificationDAO.getNotificationsByReceiverId(receiverId);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void testGetNotificationsByReceiverId_SqlException() throws SQLException {
        // Arrange
        int receiverId = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> {
            notificationDAO.getNotificationsByReceiverId(receiverId);
        });
        assertTrue(exception.getMessage().contains("SQL error while retrieving notifications for receiver ID " + receiverId + ": Database error"));
    }

    @Test
    void testMarkAsRead_Success() throws Exception {
        // Arrange
        int notificationId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful update

        // Act
        boolean result = notificationDAO.markAsRead(notificationId);

        // Assert
        assertTrue(result, "Expected markAsRead to return true on successful update");
    }

    @Test
    void testMarkAsRead_NoRowsUpdated() throws Exception {
        // Arrange
        int notificationId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Simulate no update

        // Act
        boolean result = notificationDAO.markAsRead(notificationId);

        // Assert
        assertFalse(result, "Expected markAsRead to return false when no rows are updated");
    }

    @Test
    void testMarkAsRead_SQLException() throws Exception {
        // Arrange
        int notificationId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("SQL error")); // Simulate SQLException

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            notificationDAO.markAsRead(notificationId);
        });

        assertEquals("SQL error while marking notification ID 1 as read: SQL error", thrown.getMessage());
    }

    @Test
    void testGetUnreadNotificationsByReceiverId_Success() throws Exception {
        // Arrange
        int receiverId = 1;

        // Configure the mock result set to return values directly
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false); // Simulate two unread notifications
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getInt("recieverId")).thenReturn(receiverId);
        when(resultSet.getString("content")).thenReturn("Content 1", "Content 2");
        when(resultSet.getTimestamp("createdDate")).thenReturn(Timestamp.valueOf("2024-10-30 12:00:00"), Timestamp.valueOf("2024-10-30 11:00:00"));
        when(resultSet.getString("status")).thenReturn("sent", "sent");
        when(resultSet.getString("type")).thenReturn("type1", "type2");
        when(resultSet.getString("url")).thenReturn("url1", "url2");

        // Act
        List<Notification> result = notificationDAO.getUnreadNotificationsByReceiverId(receiverId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    void testGetUnreadNotificationsByReceiverId_NoNotifications() throws Exception {
        // Arrange
        int receiverId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate no unread notifications

        // Act
        List<Notification> result = notificationDAO.getUnreadNotificationsByReceiverId(receiverId);

        // Assert
        assertTrue(result.isEmpty(), "Expected an empty list when there are no unread notifications");
    }

    @Test
    void testGetUnreadNotificationsByReceiverId_SQLException() throws Exception {
        // Arrange
        int receiverId = 1;
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("SQL error")); // Simulate SQLException

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            notificationDAO.getUnreadNotificationsByReceiverId(receiverId);
        });

        assertEquals("SQL error while retrieving unread notifications for receiver ID 1: SQL error", thrown.getMessage());
    }

    @Test
    void testGetSentNotificationsByTypeAndReceiverId_Success() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        // Configure the mock result set to return values directly
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false); // Simulate two sent notifications
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getInt("recieverId")).thenReturn(receiverId);
        when(resultSet.getString("content")).thenReturn("Content 1", "Content 2");
        when(resultSet.getTimestamp("createdDate")).thenReturn(Timestamp.valueOf("2024-10-30 12:00:00"), Timestamp.valueOf("2024-10-30 11:00:00"));
        when(resultSet.getString("status")).thenReturn("sent", "sent");
        when(resultSet.getString("type")).thenReturn(type, type);
        when(resultSet.getString("url")).thenReturn("url1", "url2");

        // Act
        List<Notification> result = notificationDAO.getSentNotificationsByTypeAndReceiverId(receiverId, type);

        // Assert
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    void testGetSentNotificationsByTypeAndReceiverId_NoNotifications() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate no sent notifications

        // Act
        List<Notification> result = notificationDAO.getSentNotificationsByTypeAndReceiverId(receiverId, type);

        // Assert
        assertTrue(result.isEmpty(), "Expected an empty list when there are no sent notifications");
    }

    @Test
    void testGetSentNotificationsByTypeAndReceiverId_SQLException() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("SQL error")); // Simulate SQLException

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            notificationDAO.getSentNotificationsByTypeAndReceiverId(receiverId, type);
        });

        assertEquals("SQL error while retrieving sent notifications of type 'info' for receiver ID 1: SQL error", thrown.getMessage());
    }

    @Test
    void testMarkAsSeenByTypeAndReceiverId_Success() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate one row affected

        // Act
        boolean result = notificationDAO.markAsSeenByTypeAndReceiverId(receiverId, type);

        // Assert
        assertTrue(result, "Expected the method to return true when one row is affected");
    }

    @Test
    void testMarkAsSeenByTypeAndReceiverId_NoRowsAffected() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Simulate no rows affected

        // Act
        boolean result = notificationDAO.markAsSeenByTypeAndReceiverId(receiverId, type);

        // Assert
        assertFalse(result, "Expected the method to return false when no rows are affected");
    }

    @Test
    void testMarkAsSeenByTypeAndReceiverId_SQLException() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("SQL error")); // Simulate SQLException

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            notificationDAO.markAsSeenByTypeAndReceiverId(receiverId, type);
        });

        assertEquals("SQL error while marking notifications as seen for receiver ID 1 and type 'info': SQL error", thrown.getMessage());
    }

    @Test
    void testDeleteNotificationsByTypeAndReceiverId_Success() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate one row affected

        // Act
        boolean result = notificationDAO.deleteNotificationsByTypeAndReceiverId(receiverId, type);

        // Assert
        assertTrue(result, "Expected the method to return true when one row is deleted");
    }

    @Test
    void testDeleteNotificationsByTypeAndReceiverId_NoRowsAffected() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Simulate no rows affected

        // Act
        boolean result = notificationDAO.deleteNotificationsByTypeAndReceiverId(receiverId, type);

        // Assert
        assertFalse(result, "Expected the method to return false when no rows are deleted");
    }

    @Test
    void testDeleteNotificationsByTypeAndReceiverId_SQLException() throws Exception {
        // Arrange
        int receiverId = 1;
        String type = "info";

        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("SQL error")); // Simulate SQLException

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            notificationDAO.deleteNotificationsByTypeAndReceiverId(receiverId, type);
        });

        assertEquals("SQL error while deleting notifications for receiver ID 1 and type 'info': SQL error", thrown.getMessage());
    }


}
