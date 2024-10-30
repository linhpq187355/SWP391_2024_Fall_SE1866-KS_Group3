/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
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

public interface ConversationService {

    Map<User, Reply> getListUserConversation(int userId) throws SQLException;

    int getConversationId(int userOne, int userTwo) throws SQLException;

    List<Reply> getListReplyConversation(int conversationId) throws SQLException;

    boolean addReply(String text, int conversationId, int userId, String contentType, String contentUrl) throws SQLException;

    User getMatchedUser(List<User> listUserConversation, int userTwo) throws SQLException;

}
