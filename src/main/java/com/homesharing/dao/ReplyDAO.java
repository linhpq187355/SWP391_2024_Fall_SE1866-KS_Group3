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

import com.homesharing.exception.GeneralException;
import com.homesharing.model.Reply;

import java.sql.SQLException;
import java.util.List;

/**
 * ReplyDAO interface provides an abstraction for interacting with replies in the database.
 * It defines methods for common operations related to replies in the chat system.
 */
public interface ReplyDAO {

    /**
     * Fetches a list of replies associated with a specific conversation.
     *
     * @param conversationId The ID of the conversation whose replies are to be fetched.
     * @return A list of Reply objects corresponding to the specified conversation.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    List<Reply> getReplies(int conversationId) throws SQLException;

    /**
     * Adds a new reply to the database.
     *
     * @param reply The Reply object containing the information of the reply to be added.
     * @return The ID of the newly added reply.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    int addReply(Reply reply) throws SQLException;

    /**
     * Retrieves replies for a specific conversation filtered by content type.
     *
     * @param conversationId The ID of the conversation.
     * @param contentType    The type of content to filter replies (e.g., text, image).
     * @return A list of Reply objects that match the given conversation ID and content type.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    List<Reply> getRepliesByConversationIdAndContentType(int conversationId, String contentType) throws SQLException;

    /**
     * Fetches the latest reply for a specific conversation.
     *
     * @param conversationId The ID of the conversation whose latest reply is to be fetched.
     * @return The latest Reply object for the specified conversation.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    Reply getLastestReply(int conversationId) throws SQLException;

    /**
     * Counts the number of new messages for a specific user.
     * This method retrieves the count of messages that have been sent in conversations
     * involving the specified user but have not yet been seen by that user. A message
     * is considered new if it has been sent after the last seen message or the last
     * message sent by the user in the same conversation.
     *
     * @param userId the ID of the user for whom to count new messages
     * @return the count of new messages that the specified user has received
     * @throws SQLException if a database access error occurs
     * @throws GeneralException if there are issues retrieving the connection or executing the SQL query
     */
    int countNewMessages(int userId) throws SQLException, GeneralException;

    /**
     * Updates the status of the latest reply for a specific conversation for a given user.
     *
     * @param conversationId The ID of the conversation containing the latest reply.
     * @param userId        The ID of the user whose latest reply status will be updated.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    void updateStatusForLatestReply(int conversationId, int userId) throws SQLException;

    /**
     * Deletes a reply from the database by its ID.
     * @param replyId The ID of the reply to be deleted
     * @return true if the reply was successfully deleted, false otherwise
     * @throws SQLException if there is an error during the database operation
     */
    boolean deleteReplyById(int replyId, int conversationId, int sentId) throws SQLException;

}
