package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.ReplyDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Reply;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReplyDAOImplTest {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ReplyDAOImpl replyDAO;

    private MockedStatic<DBContext> mockedDBContext;

    @BeforeEach
    public void setUp() {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        mockedDBContext = Mockito.mockStatic(DBContext.class);
        mockedDBContext.when(DBContext::getConnection).thenReturn(connection);
        replyDAO = new ReplyDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    void testGetReplies_Success() throws Exception {
        // Arrange
        int conversationId = 1;
        List<Reply> expectedReplies = new ArrayList<>();

        // Create a mock reply
        Reply reply = new Reply();
        reply.setId(1);
        reply.setText("Hello");
        reply.setStatus("sent");
        reply.setConversationId(conversationId);
        reply.setUserId(1);
        reply.setContentType("text");
        reply.setContentUrl(null);
        expectedReplies.add(reply);

        // Mocking the behavior of the connection, prepared statement, and result set
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false); // One reply found
        when(resultSet.getInt("id")).thenReturn(reply.getId());
        when(resultSet.getString("reply")).thenReturn(reply.getText());
        when(resultSet.getString("status")).thenReturn(reply.getStatus());
        when(resultSet.getInt("conversationId")).thenReturn(reply.getConversationId());
        when(resultSet.getInt("userId")).thenReturn(reply.getUserId());
        when(resultSet.getString("contentType")).thenReturn(reply.getContentType());
        when(resultSet.getString("contentUrl")).thenReturn(reply.getContentUrl());
        when(resultSet.getTimestamp("time")).thenReturn(null); // No timestamp

        // Act
        List<Reply> replies = replyDAO.getReplies(conversationId);

        // Assert
        assertEquals(expectedReplies.size(), replies.size(), "Expected size of replies list to be equal");
        assertEquals(expectedReplies.get(0).getText(), replies.get(0).getText(), "Expected reply text to match");
        assertEquals(expectedReplies.get(0).getStatus(), replies.get(0).getStatus(), "Expected reply status to match");
        assertEquals(expectedReplies.get(0).getConversationId(), replies.get(0).getConversationId(), "Expected reply conversation ID to match");
        assertEquals(expectedReplies.get(0).getUserId(), replies.get(0).getUserId(), "Expected reply user ID to match");
        assertEquals(expectedReplies.get(0).getContentType(), replies.get(0).getContentType(), "Expected reply content type to match");
        assertNull(replies.get(0).getContentUrl(), "Expected reply content URL to be null");
    }

    @Test
    void testGetReplies_NoReplies() throws Exception {
        // Arrange
        int conversationId = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // No replies found

        // Act
        List<Reply> replies = replyDAO.getReplies(conversationId);

        // Assert
        assertTrue(replies.isEmpty(), "Expected replies list to be empty");
    }

    @Test
    void testGetReplies_SQLException() throws Exception {
        // Arrange
        int conversationId = 1;

        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException("SQL error")); // Simulate SQLException

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            replyDAO.getReplies(conversationId);
        });

        assertEquals("SQL error while retrieving replies for conversation ID 1: SQL error", thrown.getMessage());
    }

    @Test
    void testUpdateStatusForLatestReply_Success() throws SQLException {
        int conversationId = 1; // Test data
        int userId = 2; // Test data

        // Mock the behavior of prepareStatement and executeUpdate
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate one row updated

        // Call the method to test
        replyDAO.updateStatusForLatestReply(conversationId, userId);

        // Verify that the PreparedStatement was set up correctly
        verify(preparedStatement).setString(1, "seen");
        verify(preparedStatement).setInt(2, conversationId);
        verify(preparedStatement).setInt(3, userId);
        verify(preparedStatement).setString(4, "sent");

        // Verify that executeUpdate was called once
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testUpdateStatusForLatestReply_NoRepliesFound() throws SQLException {
        int conversationId = 1;
        int userId = 2;

        // Mock the behavior of prepareStatement and executeUpdate
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Simulate no rows updated

        // Expect a GeneralException to be thrown
        Exception exception = assertThrows(GeneralException.class, () -> {
            replyDAO.updateStatusForLatestReply(conversationId, userId);
        });

        assertTrue(exception.getMessage().contains("No replies found to update for conversation ID " + conversationId));
    }

    @Test
    void testUpdateStatusForLatestReply_SQLException() throws SQLException {
        int conversationId = 1;
        int userId = 2;

        // Mock the behavior of prepareStatement and throw an SQLException
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

        // Expect a GeneralException to be thrown
        Exception exception = assertThrows(GeneralException.class, () -> {
            replyDAO.updateStatusForLatestReply(conversationId, userId);
        });

        assertTrue(exception.getMessage().contains("SQL error while updating status for latest reply: SQL error"));
    }

    @Test
    void testAddReply_Success() throws SQLException {
        // Prepare test data
        Reply reply = new Reply();
        reply.setText("Test reply");
        reply.setTime(Timestamp.valueOf("2024-10-30 10:00:00").toLocalDateTime());
        reply.setStatus("sent");
        reply.setConversationId(1);
        reply.setUserId(2);
        reply.setContentType("text");
        reply.setContentUrl(null);

        // Mock the behavior of prepareStatement and generated keys
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful update
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate that a key is generated
        when(resultSet.getInt(1)).thenReturn(100); // Simulate the generated key

        // Call the method to test
        int replyId = replyDAO.addReply(reply);

        // Verify that the correct parameters were set on the PreparedStatement
        verify(preparedStatement).setString(1, reply.getText());
        verify(preparedStatement).setTimestamp(2, Timestamp.valueOf(reply.getTime()));
        verify(preparedStatement).setString(3, reply.getStatus());
        verify(preparedStatement).setInt(4, reply.getConversationId());
        verify(preparedStatement).setInt(5, reply.getUserId());
        verify(preparedStatement).setString(6, reply.getContentType());
        verify(preparedStatement).setString(7, reply.getContentUrl());

        // Verify that executeUpdate was called
        verify(preparedStatement).executeUpdate();

        // Assert that the returned reply ID is correct
        assertEquals(100, replyId);
    }

    @Test
    void testAddReply_NoGeneratedKey() throws SQLException {
        // Prepare test data
        Reply reply = new Reply();
        reply.setText("Test reply");
        reply.setTime(Timestamp.valueOf("2024-10-30 10:00:00").toLocalDateTime());
        reply.setStatus("sent");
        reply.setConversationId(1);
        reply.setUserId(2);
        reply.setContentType("text");
        reply.setContentUrl(null);

        // Mock the behavior of prepareStatement and generated keys
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful update
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate that no key is generated

        // Expect a GeneralException to be thrown
        Exception exception = assertThrows(GeneralException.class, () -> {
            replyDAO.addReply(reply);
        });

        assertTrue(exception.getMessage().contains("No generated key returned for the new reply"));
    }

    @Test
    void testAddReply_SQLException() throws SQLException {
        // Prepare test data
        Reply reply = new Reply();
        reply.setText("Test reply");
        reply.setTime(Timestamp.valueOf("2024-10-30 10:00:00").toLocalDateTime());
        reply.setStatus("sent");
        reply.setConversationId(1);
        reply.setUserId(2);
        reply.setContentType("text");
        reply.setContentUrl(null);

        // Mock the behavior of prepareStatement and throw an SQLException
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("SQL error"));

        // Expect a GeneralException to be thrown
        Exception exception = assertThrows(GeneralException.class, () -> {
            replyDAO.addReply(reply);
        });

        assertTrue(exception.getMessage().contains("SQL error while inserting reply into the database: SQL error"));
    }

    @Test
    void testGetRepliesByConversationIdAndContentType_NonNullContentType() throws SQLException {
        int conversationId = 1;
        String contentType = "text";
        LocalDateTime replyTime = LocalDateTime.now();

        // Prepare test data
        List<Reply> expectedReplies = new ArrayList<>();
        Reply reply = new Reply();
        reply.setText("Hello");
        reply.setTime(replyTime);
        reply.setStatus("sent");
        reply.setConversationId(conversationId);
        reply.setUserId(2);
        reply.setContentType("text");
        reply.setContentUrl(null);

        expectedReplies.add(reply);

        // Mocking the database interactions
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the behavior of ResultSet
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Mocking the ResultSet to return value
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Chỉ một kết quả
        when(resultSet.getString("reply")).thenReturn("Hello");
        when(resultSet.getTimestamp("time")).thenReturn(Timestamp.valueOf(replyTime)); // Thay thế thành thời gian đã lưu
        when(resultSet.getString("status")).thenReturn("sent");
        when(resultSet.getInt("conversationId")).thenReturn(conversationId);
        when(resultSet.getInt("userId")).thenReturn(2);
        when(resultSet.getString("contentType")).thenReturn(contentType);
        when(resultSet.getString("contentUrl")).thenReturn(null);

        // call method
        List<Reply> actualReplies = replyDAO.getRepliesByConversationIdAndContentType(conversationId, contentType);

        // Verify result
        assertEquals(expectedReplies.size(), actualReplies.size());
        assertEquals(expectedReplies.get(0).getText(), actualReplies.get(0).getText());
        assertEquals(expectedReplies.get(0).getTime(), actualReplies.get(0).getTime());
        assertEquals(expectedReplies.get(0).getStatus(), actualReplies.get(0).getStatus());
        assertEquals(expectedReplies.get(0).getConversationId(), actualReplies.get(0).getConversationId());
        assertEquals(expectedReplies.get(0).getUserId(), actualReplies.get(0).getUserId());
        assertEquals(expectedReplies.get(0).getContentType(), actualReplies.get(0).getContentType());
        assertEquals(expectedReplies.get(0).getContentUrl(), actualReplies.get(0).getContentUrl());
    }

    @Test
    void testGetRepliesByConversationIdAndContentType_NullContentType() throws SQLException {
        int conversationId = 1;
        LocalDateTime replyTime = LocalDateTime.now();
        List<Reply> expectedReplies = new ArrayList<>();
        Reply reply = new Reply();
        reply.setText("Hello");
        reply.setTime(replyTime);
        reply.setStatus("sent");
        reply.setConversationId(conversationId);
        reply.setUserId(1);
        reply.setContentType(null);
        reply.setContentUrl(null);

        expectedReplies.add(reply);

        // Mocking the database interactions
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Mocking the ResultSet to return values
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Return one result
        when(resultSet.getString("reply")).thenReturn("Hello");
        when(resultSet.getTimestamp("time")).thenReturn(Timestamp.valueOf(replyTime));
        when(resultSet.getString("status")).thenReturn("sent");
        when(resultSet.getInt("conversationId")).thenReturn(conversationId);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getString("contentType")).thenReturn(null);
        when(resultSet.getString("contentUrl")).thenReturn(null);

        // Call the method under test
        List<Reply> actualReplies = replyDAO.getRepliesByConversationIdAndContentType(conversationId, null);

        // Verify the results
        assertEquals(expectedReplies.size(), actualReplies.size());
        assertEquals(expectedReplies.get(0).getText(), actualReplies.get(0).getText());
        assertEquals(expectedReplies.get(0).getTime(), actualReplies.get(0).getTime());
        assertEquals(expectedReplies.get(0).getStatus(), actualReplies.get(0).getStatus());
        assertEquals(expectedReplies.get(0).getConversationId(), actualReplies.get(0).getConversationId());
        assertEquals(expectedReplies.get(0).getUserId(), actualReplies.get(0).getUserId());
        assertEquals(expectedReplies.get(0).getContentType(), actualReplies.get(0).getContentType());
        assertEquals(expectedReplies.get(0).getContentUrl(), actualReplies.get(0).getContentUrl());
    }

    @Test
    void testGetRepliesByConversationIdAndContentType_SQLException() throws SQLException {
        // Mock the behavior of prepareStatement and throw an SQLException
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException("Database error"));

        // Expect a GeneralException to be thrown
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            replyDAO.getRepliesByConversationIdAndContentType(1, "text");
        });

        // Verify the message
        assertEquals("SQL error while retrieving replies from the database: Database error", exception.getMessage());
    }

    @Test
    void testGetLastestReply_Found() throws Exception {
        int conversationId = 1;
        String expectedReplyText = "This is the latest reply";
        LocalDateTime expectedTime = LocalDateTime.now().minusMinutes(5);
        String expectedStatus = "sent";
        int expectedUserId = 100;
        String expectedContentType = "text";
        String expectedContentUrl = null;

        // Set up the mock behavior for the PreparedStatement and ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("reply")).thenReturn(expectedReplyText);
        when(resultSet.getTimestamp("time")).thenReturn(Timestamp.valueOf(expectedTime));
        when(resultSet.getString("status")).thenReturn(expectedStatus);
        when(resultSet.getInt("userId")).thenReturn(expectedUserId);
        when(resultSet.getString("contentType")).thenReturn(expectedContentType);
        when(resultSet.getString("contentUrl")).thenReturn(expectedContentUrl);

        // Call the method under test
        Reply result = replyDAO.getLastestReply(conversationId);

        // Verify the expected behavior and values
        assertNotNull(result);
        assertEquals(expectedReplyText, result.getText());
        assertEquals(expectedTime, result.getTime());
        assertEquals(expectedStatus, result.getStatus());
        assertEquals(conversationId, result.getConversationId());
        assertEquals(expectedUserId, result.getUserId());
        assertEquals(expectedContentType, result.getContentType());
        assertEquals(expectedContentUrl, result.getContentUrl());
        verify(preparedStatement).setInt(1, conversationId);
        verify(preparedStatement).close();
    }

    @Test
    void testGetLastestReply_NotFound() throws Exception {
        int conversationId = 1;

        // Mock the PreparedStatement and ResultSet to simulate no data found
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // Call the method under test
        Reply result = replyDAO.getLastestReply(conversationId);

        // Verify that result is null since no data is found
        assertNull(result);
        verify(preparedStatement).setInt(1, conversationId);
        verify(preparedStatement).close();
    }

    @Test
    void testGetLastestReply_SQLException() throws Exception {
        int conversationId = 1;

        // Mock the behavior to throw an SQLException
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Assert that the GeneralException is thrown with the expected message
        GeneralException exception = assertThrows(GeneralException.class, () -> replyDAO.getLastestReply(conversationId));
        assertTrue(exception.getMessage().contains("SQL error while fetching the latest reply from the database"));

        // Verify that no further interactions occur
        verify(preparedStatement, never()).setInt(anyInt(), anyInt());
    }

    @Test
    void testGetLastestReply_IOException() throws Exception {
        int conversationId = 1;

        // Mock DBContext.getConnection() to throw IOException
        mockedDBContext.when(DBContext::getConnection).thenThrow(new IOException("Connection error"));

        // Assert that the GeneralException is thrown with the expected message
        GeneralException exception = assertThrows(GeneralException.class, () -> replyDAO.getLastestReply(conversationId));
        assertTrue(exception.getMessage().contains("I/O error while fetching the latest reply from the database"));

        // Verify that no interaction with the database occurs
        verify(connection, never()).prepareStatement(anyString());
    }

    @Test
    void testGetLastestReply_ClassNotFoundException() throws Exception {
        int conversationId = 1;

        // Mock DBContext.getConnection() to throw ClassNotFoundException
        mockedDBContext.when(DBContext::getConnection).thenThrow(new ClassNotFoundException("JDBC driver not found"));

        // Assert that the GeneralException is thrown with the expected message
        GeneralException exception = assertThrows(GeneralException.class, () -> replyDAO.getLastestReply(conversationId));
        assertTrue(exception.getMessage().contains("Class not found error while fetching the latest reply from the database"));

        // Verify that no interaction with the database occurs
        verify(connection, never()).prepareStatement(anyString());
    }

    @Test
    void testCountNewMessages() throws SQLException {
        int userId = 1; // Example user ID
        int expectedNewMessageCount = 5; // Expected number of new messages

        // Mock the behavior of connection, prepared statement, and result set
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("NewMessageCount")).thenReturn(expectedNewMessageCount);

        // Call the method under test
        int actualNewMessageCount = replyDAO.countNewMessages(userId);

        // Verify the expected results
        assertEquals(expectedNewMessageCount, actualNewMessageCount, "The count of new messages should match the expected value.");

        // Verify that methods were called with correct parameters
        verify(preparedStatement, times(1)).setInt(1, userId);
        verify(preparedStatement, times(1)).setInt(2, userId);
        verify(preparedStatement, times(1)).setInt(3, userId);
        verify(preparedStatement, times(1)).setInt(4, userId);
        verify(preparedStatement, times(1)).setInt(5, userId);

        // Verify resources were closed
        verify(resultSet, times(1)).close();
        verify(preparedStatement, times(1)).close();
    }

    @Test
    void testCountNewMessagesSQLException() throws SQLException {
        int userId = 1;

        // Mock behavior for SQLException
        when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException("Database error"));

        // Assert that GeneralException is thrown
        GeneralException exception = assertThrows(GeneralException.class, () -> replyDAO.countNewMessages(userId));
        assertTrue(exception.getMessage().contains("SQL error counting new messages for user ID"));

        // Verify resources were closed
        verify(preparedStatement, never()).close();
        verify(resultSet, never()).close();
    }

}
