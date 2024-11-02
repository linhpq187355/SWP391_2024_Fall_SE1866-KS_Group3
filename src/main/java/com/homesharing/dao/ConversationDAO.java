/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.dao;

import com.homesharing.model.Conversation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * ConversationDAO is an interface that defines the operations for managing conversation of users.
 * It provides methods to insert, find, and update conversation information related to each user.
 *
 * @version 1.0
 * @since 2024-10-20
 * @author ManhNC
 */
public interface ConversationDAO {

    /**
     * Retrieves all conversations associated with a specific user ID.
     *
     * @param userId the ID of the user for whom to retrieve conversations
     * @return a list of Conversation objects associated with the user
     * @throws SQLException if a database access error occurs while retrieving conversations
     */
    List<Conversation> getAllConversationsByUserId(int userId) throws SQLException;

    /**
     * Retrieves the conversation ID for a conversation between two users.
     *
     * @param userOne the ID of the first user
     * @param userTwo the ID of the second user
     * @return the ID of the conversation, or -1 if no conversation exists
     * @throws SQLException if a database access error occurs while retrieving the conversation ID
     */
    int getConversationId(int userOne, int userTwo) throws SQLException;

    /**
     * Adds a new conversation to the database.
     *
     * @param conversation the Conversation object to be added
     * @return the ID of the newly created conversation
     * @throws SQLException if a database access error occurs while adding the conversation
     */
    int addConversation(Conversation conversation) throws SQLException;

    /**
     * Retrieves a map of contact users associated with their respective conversation IDs.
     *
     * @param userId the ID of the user for whom to retrieve contact users and conversation IDs
     * @return a map where keys are user IDs of contacts and values are their respective conversation IDs
     * @throws SQLException if a database access error occurs while retrieving contact users
     */
    Map<Integer, Integer> contactUsersWithConversationId(int userId) throws SQLException;
}
