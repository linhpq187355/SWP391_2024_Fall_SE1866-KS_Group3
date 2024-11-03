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

import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.ReplyDAOImpl;
import com.homesharing.model.Conversation;
import com.homesharing.model.Reply;
import com.homesharing.model.User;
import com.homesharing.service.ConversationService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

/**
 * Implementation of the ConversationService interface, providing services for
 * managing conversations, including retrieving user conversations, managing messages,
 * and adding new messages in the home-sharing application.
 */
public class ConversationServiceImpl implements ConversationService {

    private final UserDAO userDao;
    private final ConversationDAO conversationDao;
    private final ReplyDAO replyDao;
    private static final Logger logger = Logger.getLogger(ConversationServiceImpl.class.getName());

    /**
     * Constructs a ConversationServiceImpl with specified DAO instances.
     *
     * @param userDao DAO for user-related database operations
     * @param conversationDao DAO for conversation-related database operations
     * @param replyDao DAO for reply-related database operations
     */
    public ConversationServiceImpl(UserDAO userDao, ConversationDAO conversationDao, ReplyDAO replyDao) {
        this.userDao = userDao;
        this.conversationDao = conversationDao;
        this.replyDao = new ReplyDAOImpl();
    }

    /**
     * Retrieves a list of users with whom the specified user has had conversations, along with
     * the latest message in each conversation. The list is sorted in descending order based on the
     * timestamp of the latest message.
     *
     * @param userId The ID of the user
     * @return A sorted map where each key is a user, and the value is the latest message (Reply) in the conversation
     * @throws SQLException If a database access error occurs
     */
    @Override
    public Map<User, Reply> getListUserConversation(int userId) throws SQLException {
        Map<User, Reply> unsortedMap = new HashMap<>(); // Temporary HashMap
        List<Conversation> listConversation = conversationDao.getAllConversationsByUserId(userId);

        for (Conversation conversation : listConversation) {
            int otherUserId = (conversation.getUserOne() == userId) ? conversation.getUserTwo() : conversation.getUserOne();
            User user = userDao.getUser(otherUserId);
            unsortedMap.put(user, replyDao.getLastestReply(conversation.getId()));
        }

        // Sort the map entries by Reply time in descending order
        List<Map.Entry<User, Reply>> sortedEntries = new ArrayList<>(unsortedMap.entrySet());
        sortedEntries.sort((entry1, entry2) -> {
            Reply reply1 = entry1.getValue();
            Reply reply2 = entry2.getValue();

            // Check for null before comparing
            if (reply1 == null && reply2 == null) {
                return 0; // Both are null, retain current order
            } else if (reply1 == null) {
                return 1; // Place reply2 before reply1 if reply1 is null
            } else if (reply2 == null) {
                return -1; // Place reply1 before reply2 if reply2 is null
            } else {
                // Both are not null, compare based on timestamp
                return reply2.getTime().compareTo(reply1.getTime());
            }
        });

        // Use LinkedHashMap to maintain the order of sorted entries
        Map<User, Reply> sortedMap = new LinkedHashMap<>(); // Use a LinkedHashMap to preserve order
        for (Map.Entry<User, Reply> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /**
     * Retrieves the conversation ID between two specific users. If no conversation exists, a new conversation is created.
     *
     * @param userOne The ID of the first user
     * @param userTwo The ID of the second user
     * @return The ID of the existing or newly created conversation
     * @throws SQLException If a database access error occurs
     */
    @Override
    public int getConversationId(int userOne, int userTwo) throws SQLException {
        // Check if a conversation exists from userOne to userTwo
        int id = conversationDao.getConversationId(userOne, userTwo);

        // If not found, check in the opposite direction
        if (id == 0) {
            id = conversationDao.getConversationId(userTwo, userOne);
        }

        // If still not found, create a new conversation
        if (id == 0) {
            Conversation conversation = new Conversation();
            conversation.setUserOne(userOne);
            conversation.setUserTwo(userTwo);
            conversation.setTime(LocalDateTime.now());
            conversation.setStatus("active");
            id = conversationDao.addConversation(conversation);
            if (id > 0) {
                return id; // Return the ID of the newly created conversation
            } else {
                return 0; // Return 0 if there was an error creating the conversation
            }
        }

        return id; // Return the ID of the existing conversation
    }

    /**
     * Retrieves a list of messages in a specified conversation.
     *
     * @param conversationId The ID of the conversation to retrieve messages from
     * @return A list of messages (Reply) within the specified conversation
     * @throws SQLException If a database access error occurs
     */
    @Override
    public List<Reply> getListReplyConversation(int conversationId) throws SQLException {
        return replyDao.getReplies(conversationId);
    }

    /**
     * Adds a new message to a conversation.
     *
     * @param text The content of the message
     * @param conversationId The ID of the conversation to which the message is added
     * @param userId The ID of the user sending the message
     * @param contentType The type of content in the message (e.g., text, image)
     * @param contentUrl The URL of the content, if applicable (for media messages)
     * @return A Reply object representing the newly added message, or null if an error occurred
     * @throws SQLException If a database access error occurs
     */
    @Override
    public Reply addReply(String text, int conversationId, int userId, String contentType, String contentUrl) throws SQLException {
        Reply reply = new Reply();
        reply.setText(text);
        reply.setUserId(userId);
        reply.setConversationId(conversationId);
        reply.setStatus("sent");
        reply.setTime(LocalDateTime.now());
        reply.setContentType(contentType);
        reply.setContentUrl(contentUrl);
        reply.setId(replyDao.addReply(reply));

        // Return the newly created reply, or null if there was an error
        if (reply.getId() == 0) {
            return null;
        }
        return reply;
    }
}
