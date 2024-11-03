package com.homesharing.service;

import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.model.Conversation;
import com.homesharing.model.Reply;
import com.homesharing.model.User;
import com.homesharing.service.impl.ConversationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversationServiceImplTest {

    private UserDAO userDao;
    private ConversationDAO conversationDao;
    private ReplyDAO replyDao;
    private ConversationService conversationService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDAO.class);
        conversationDao = mock(ConversationDAO.class);
        replyDao = mock(ReplyDAO.class);
        conversationService = new ConversationServiceImpl(userDao, conversationDao, replyDao);
    }

    @Test
    void testGetListUserConversation_WithMultipleConversations() throws SQLException {
        int userId = 1;

        // Mock conversations
        List<Conversation> conversations = new ArrayList<>();
        Conversation conversation1 = new Conversation(1, userId, 2, LocalDateTime.now(), "active");
        Conversation conversation2 = new Conversation(2, userId, 3, LocalDateTime.now(), "active");
        conversations.add(conversation1);
        conversations.add(conversation2);

        when(conversationDao.getAllConversationsByUserId(userId)).thenReturn(conversations);

        // Mock users
        User user2 = new User("Hi", "User2", "user2@example.com", "password");
        User user3 = new User("Hi", "User3", "user3@example.com", "password");
        when(userDao.getUser(2)).thenReturn(user2);
        when(userDao.getUser(3)).thenReturn(user3);

        // Mock replies
        Reply reply1 = new Reply(1, "Hello from User2", LocalDateTime.now(),"sent", conversation1.getId(), userId);
        Reply reply2 = new Reply(2, "Hi User1", LocalDateTime.now().minusMinutes(1),"sent", conversation2.getId(), userId);
        when(replyDao.getLastestReply(conversation1.getId())).thenReturn(reply1);
        when(replyDao.getLastestReply(conversation2.getId())).thenReturn(reply2);

        // Call the method to test
        Map<User, Reply> result = conversationService.getListUserConversation(userId);

        // Verify results
        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    void testGetListUserConversation_NoConversations() throws SQLException {
        int userId = 1;

        // Mock empty conversation list
        when(conversationDao.getAllConversationsByUserId(userId)).thenReturn(Collections.emptyList());

        // Call the method to test
        Map<User, Reply> result = conversationService.getListUserConversation(userId);

        // Verify results
        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result should be an empty map when there are no conversations.");
    }

    @Test
    void testGetListUserConversation_WithNullReplies() throws SQLException {
        int userId = 1;

        // Mock conversations
        List<Conversation> conversations = new ArrayList<>();
        Conversation conversation1 = new Conversation(1, userId, 2, LocalDateTime.now(), "active");
        conversations.add(conversation1);

        when(conversationDao.getAllConversationsByUserId(userId)).thenReturn(conversations);

        // Mock user
        User user2 = new User("Hi", "User2", "user2@example.com", "password");
        when(userDao.getUser(2)).thenReturn(user2);

        // Mock reply as null
        when(replyDao.getLastestReply(conversation1.getId())).thenReturn(null);

        // Call the method to test
        Map<User, Reply> result = conversationService.getListUserConversation(userId);

        // Verify results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user2, result.keySet().iterator().next());
    }

    @Test
    void testGetListUserConversation_WithNullUser() throws SQLException {
        int userId = 1;

        // Mock conversations
        List<Conversation> conversations = new ArrayList<>();
        Conversation conversation1 = new Conversation(1, userId, 2, LocalDateTime.now(), "active");
        conversations.add(conversation1);

        when(conversationDao.getAllConversationsByUserId(userId)).thenReturn(conversations);

        // Mock user as null
        when(userDao.getUser(2)).thenReturn(null);

        // Mock reply
        Reply reply1 = new Reply(1, "Hello from User2", LocalDateTime.now(),"sent", conversation1.getId(), userId);
        when(replyDao.getLastestReply(conversation1.getId())).thenReturn(reply1);

        // Call the method to test
        Map<User, Reply> result = conversationService.getListUserConversation(userId);

        // Verify results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.keySet().iterator().next(), "The user should be null if the user does not exist.");
    }

    @Test
    void testGetListUserConversation_WithException() throws SQLException {
        int userId = 1;

        // Mock DAO to throw SQLException
        when(conversationDao.getAllConversationsByUserId(userId)).thenThrow(new SQLException("Database error"));

        // Call the method and assert exception
        assertThrows(SQLException.class, () -> conversationService.getListUserConversation(userId), "Expected SQLException was not thrown.");
    }

    @Test
    void testGetConversationId_ExistingConversation() throws SQLException {
        int userOne = 1;
        int userTwo = 2;
        int existingConversationId = 3;

        // Mock existing conversation
        when(conversationDao.getConversationId(userOne, userTwo)).thenReturn(existingConversationId);

        // Call the method to test
        int result = conversationService.getConversationId(userOne, userTwo);

        // Verify results
        assertEquals(existingConversationId, result);
        verify(conversationDao, times(1)).getConversationId(userOne, userTwo);
        verify(conversationDao, never()).getConversationId(userTwo, userOne);
        verify(conversationDao, never()).addConversation(any());
    }

    @Test
    void testGetConversationId_ReverseDirectionConversation() throws SQLException {
        int userOne = 1;
        int userTwo = 2;
        int existingConversationId = 3;

        // Mock reverse direction
        when(conversationDao.getConversationId(userOne, userTwo)).thenReturn(0);
        when(conversationDao.getConversationId(userTwo, userOne)).thenReturn(existingConversationId);

        // Call the method to test
        int result = conversationService.getConversationId(userOne, userTwo);

        // Verify results
        assertEquals(existingConversationId, result);
        verify(conversationDao, times(1)).getConversationId(userOne, userTwo);
        verify(conversationDao, times(1)).getConversationId(userTwo, userOne);
        verify(conversationDao, never()).addConversation(any());
    }

    @Test
    void testGetConversationId_NewConversation() throws SQLException {
        int userOne = 1;
        int userTwo = 2;
        int newConversationId = 4;

        // Mock no existing conversation
        when(conversationDao.getConversationId(userOne, userTwo)).thenReturn(0);
        when(conversationDao.getConversationId(userTwo, userOne)).thenReturn(0);
        when(conversationDao.addConversation(any())).thenReturn(newConversationId);

        // Call the method to test
        int result = conversationService.getConversationId(userOne, userTwo);

        // Verify results
        assertEquals(newConversationId, result);
        verify(conversationDao, times(1)).getConversationId(userOne, userTwo);
        verify(conversationDao, times(1)).getConversationId(userTwo, userOne);
        verify(conversationDao, times(1)).addConversation(any());
    }

    @Test
    void testGetConversationId_AddConversationFails() throws SQLException {
        int userOne = 1;
        int userTwo = 2;

        // Mock no existing conversation and failed addition
        when(conversationDao.getConversationId(userOne, userTwo)).thenReturn(0);
        when(conversationDao.getConversationId(userTwo, userOne)).thenReturn(0);
        when(conversationDao.addConversation(any())).thenReturn(0);

        // Call the method to test
        int result = conversationService.getConversationId(userOne, userTwo);

        // Verify results
        assertEquals(0, result);
        verify(conversationDao, times(1)).getConversationId(userOne, userTwo);
        verify(conversationDao, times(1)).getConversationId(userTwo, userOne);
        verify(conversationDao, times(1)).addConversation(any());
    }

    @Test
    void testGetListReplyConversation_WithReplies() throws SQLException {
        int conversationId = 1;

        // Mock replies
        List<Reply> mockReplies = new ArrayList<>();
        Reply reply1 = new Reply(1, "Hello", LocalDateTime.now(), "sent", conversationId, 2);
        Reply reply2 = new Reply(2, "Hi there", LocalDateTime.now().minusMinutes(1), "sent", conversationId, 1);
        mockReplies.add(reply1);
        mockReplies.add(reply2);

        // Mock replyDao behavior
        when(replyDao.getReplies(conversationId)).thenReturn(mockReplies);

        // Call the method to test
        List<Reply> result = conversationService.getListReplyConversation(conversationId);

        // Verify results
        assertNotNull(result);
        assertEquals(242, result.size());

        // Verify that the replyDao's getReplies method was called with the correct conversationId
        verify(replyDao, never()).getReplies(conversationId);
    }

}
