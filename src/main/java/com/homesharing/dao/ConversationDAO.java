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

    List<Conversation> getAllConversationsByUserId(int userId) throws SQLException;

    int getConversationId(int userOne, int userTwo) throws SQLException;

    Map<Integer, Integer> contactUsersWithConversationId(int userId) throws SQLException;
}
