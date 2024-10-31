package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.ConversationDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Conversation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ConversationDAOImplTest {

    private ConversationDAOImpl conversationDAO;
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
        conversationDAO = new ConversationDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    void testGetAllConversationsByUserId_validUserId_returnsConversations() throws Exception {
        int userId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getInt("id")).thenReturn(101);
        when(resultSet.getInt("userOne")).thenReturn(userId);
        when(resultSet.getInt("userTwo")).thenReturn(2);
        when(resultSet.getTimestamp("time")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getString("status")).thenReturn("active");

        List<Conversation> conversations = conversationDAO.getAllConversationsByUserId(userId);
        assertEquals(1, conversations.size());
        assertEquals(101, conversations.get(0).getId());
    }

    @Test
    void testGetAllConversationsByUserId_invalidUserId_throwsException() {
        int invalidUserId = -1;
        Exception exception = assertThrows(GeneralException.class, () -> {
            conversationDAO.getAllConversationsByUserId(invalidUserId);
        });
        assertTrue(exception.getMessage().contains("Invalid user ID"));
    }

    @Test
    void testGetAllConversationsByUserId_noConversations_returnsEmptyList() throws Exception {
        int userId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Conversation> conversations = conversationDAO.getAllConversationsByUserId(userId);
        assertTrue(conversations.isEmpty());
    }

    @Test
    void testGetAllConversationsByUserId_sqlException_throwsGeneralException() throws Exception {
        int userId = 1;
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(GeneralException.class, () -> {
            conversationDAO.getAllConversationsByUserId(userId);
        });
        assertTrue(exception.getMessage().contains("SQL error"));
    }

    @Test
    void testGetConversationId_ValidUsers() throws Exception {
        // Arrange
        int userOne = 1;
        int userTwo = 2;
        int expectedId = 10;

        // Mock the behavior of the database
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Simulate a result found
        when(resultSet.getInt("id")).thenReturn(expectedId);

        // Act
        int conversationId = conversationDAO.getConversationId(userOne, userTwo);

        // Assert
        assertEquals(expectedId, conversationId);
    }

    @Test
    void testGetConversationId_NoConversation() throws Exception {
        // Arrange
        int userOne = 1;
        int userTwo = 2;

        // Mock the behavior of the database
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate no result found

        // Act
        int conversationId = conversationDAO.getConversationId(userOne, userTwo);

        // Assert
        assertEquals(0, conversationId);
    }

    @Test
    void testGetConversationId_SQLException() throws Exception {
        // Arrange
        int userOne = 1;
        int userTwo = 2;

        // Mock the behavior of the database
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            conversationDAO.getConversationId(userOne, userTwo);
        });
        assertTrue(exception.getMessage().contains("SQL error while retrieving conversation ID for users: 1 and 2. Reason: Database error"));
    }

    @Test
    void testAddConversation_Success() throws Exception {
        // Arrange
        Conversation conversation = new Conversation();
        conversation.setUserOne(1);
        conversation.setUserTwo(2);
        conversation.setTime(LocalDateTime.now());
        conversation.setStatus("active");

        String sql = "INSERT INTO [dbo].[Conversations] ([userOne] ,[userTwo],[time],[status]) VALUES(?,?,?,?)";
        int expectedGeneratedId = 10;

        // Mock the behavior of the database
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful insert
        ResultSet generatedKeys = mock(ResultSet.class);
        when(preparedStatement.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true); // Simulate generated key available
        when(generatedKeys.getInt(1)).thenReturn(expectedGeneratedId);

        // Act
        int generatedId = conversationDAO.addConversation(conversation);

        // Assert
        assertEquals(expectedGeneratedId, generatedId);
    }

    @Test
    void testAddConversation_NoRowsAffected() throws Exception {
        // Arrange
        Conversation conversation = new Conversation();
        conversation.setUserOne(1);
        conversation.setUserTwo(2);
        conversation.setTime(LocalDateTime.now());
        conversation.setStatus("active");

        String sql = "INSERT INTO [dbo].[Conversations] ([userOne] ,[userTwo],[time],[status]) VALUES(?,?,?,?)";

        // Mock the behavior of the database
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Simulate no rows affected

        // Act & Assert
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            conversationDAO.addConversation(conversation);
        });
        assertTrue(exception.getMessage().contains("Creating conversation failed, no rows affected."));
    }

    @Test
    void testAddConversation_SQLException() throws Exception {
        // Arrange
        Conversation conversation = new Conversation();
        conversation.setUserOne(1);
        conversation.setUserTwo(2);
        conversation.setTime(LocalDateTime.now());
        conversation.setStatus("active");

        String sql = "INSERT INTO [dbo].[Conversations] ([userOne] ,[userTwo],[time],[status]) VALUES(?,?,?,?)";

        // Mock the behavior of the database
        when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            conversationDAO.addConversation(conversation);
        });
        assertTrue(exception.getMessage().contains("SQL error while adding conversation between users: 1 and 2. Reason: Database error"));
    }

    @Test
    void testContactUsersWithConversationId_Success() throws Exception {
        // Arrange
        int userId = 1;
        String sql = "SELECT userOne AS userId, id FROM Conversations WHERE userTwo = ? AND status = 'active' " +
                "UNION " +
                "SELECT userTwo AS userId, id FROM Conversations WHERE userOne = ? AND status = 'active'";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simulate one result
        when(resultSet.getInt("userId")).thenReturn(2); // Simulate contact user ID
        when(resultSet.getInt("id")).thenReturn(100); // Simulate conversation ID

        // Act
        Map<Integer, Integer> contactUserIds = conversationDAO.contactUsersWithConversationId(userId);

        // Assert
        assertEquals(1, contactUserIds.size());
        assertTrue(contactUserIds.containsKey(2));
        assertEquals(100, contactUserIds.get(2));
    }

    @Test
    void testContactUsersWithConversationId_NoActiveConversations() throws Exception {
        // Arrange
        int userId = 1;
        String sql = "SELECT userOne AS userId, id FROM Conversations WHERE userTwo = ? AND status = 'active' " +
                "UNION " +
                "SELECT userTwo AS userId, id FROM Conversations WHERE userOne = ? AND status = 'active'";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate no results

        // Act
        Map<Integer, Integer> contactUserIds = conversationDAO.contactUsersWithConversationId(userId);

        // Assert
        assertTrue(contactUserIds.isEmpty());
    }

    @Test
    void testContactUsersWithConversationId_SQLException() throws Exception {
        // Arrange
        int userId = 1;
        String sql = "SELECT userOne AS userId, id FROM Conversations WHERE userTwo = ? AND status = 'active' " +
                "UNION " +
                "SELECT userTwo AS userId, id FROM Conversations WHERE userOne = ? AND status = 'active'";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            conversationDAO.contactUsersWithConversationId(userId);
        });
        assertTrue(exception.getMessage().contains("SQL error while fetching contact users for user ID 1. Reason: Database error"));
    }

}
