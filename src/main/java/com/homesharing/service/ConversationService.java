/*
 * Copyright(C) 2024, Home sharing Inc.
 * Home sharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */

package com.homesharing.service;

import com.homesharing.model.Reply;
import com.homesharing.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The ConversationService interface provides methods for managing conversations
 * in the home-sharing application. It includes functions for retrieving the list
 * of users a user has messaged with, retrieving messages in a conversation,
 * adding new messages, and obtaining the conversation ID between two users.
 */
public interface ConversationService {

    /**
     * Retrieves a list of users who have had conversations with the specified user.
     *
     * @param userId The ID of the user whose conversations are to be retrieved
     * @return A map where each entry represents a user (User) and the latest message
     *         (Reply) in each conversation.
     * @throws SQLException If a database access error occurs
     */
    Map<User, Reply> getListUserConversation(int userId) throws SQLException;

    /**
     * Retrieves the conversation ID between two specific users.
     *
     * @param userOne The ID of the first user
     * @param userTwo The ID of the second user
     * @return The conversation ID between the two users, if one exists; otherwise,
     *         a placeholder value is returned
     * @throws SQLException If a database access error occurs
     */
    int getConversationId(int userOne, int userTwo) throws SQLException;

    /**
     * Retrieves the list of messages in a specific conversation.
     *
     * @param conversationId The ID of the conversation to retrieve messages from
     * @return A list of messages (Reply) within the specified conversation
     * @throws SQLException If a database access error occurs
     */
    List<Reply> getListReplyConversation(int conversationId) throws SQLException;

    /**
     * Adds a new message to a conversation.
     *
     * @param text The content of the message
     * @param conversationId The ID of the conversation to which the message is added
     * @param userId The ID of the user sending the message
     * @param contentType The type of content in the message (text, image, etc.)
     * @param contentUrl The URL of the content, if applicable (for media messages)
     * @return A Reply object representing the newly added message
     * @throws SQLException If a database access error occurs
     */
    Reply addReply(String text, int conversationId, int userId, String contentType, String contentUrl) throws SQLException;

}
