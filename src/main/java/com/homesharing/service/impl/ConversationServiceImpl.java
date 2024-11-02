/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
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

public class ConversationServiceImpl implements ConversationService {

    private final UserDAO userDao;
    private final ConversationDAO conversationDao;
    private final ReplyDAO replyDao;
    private static final Logger logger = Logger.getLogger(ConversationServiceImpl.class.getName());

    public ConversationServiceImpl(UserDAO userDao, ConversationDAO conversationDao, ReplyDAO replyDao) {
        this.userDao = userDao;
        this.conversationDao = conversationDao;
        this.replyDao = new ReplyDAOImpl();
    }

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
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().getTime().compareTo(entry1.getValue().getTime()));


        Map<User, Reply> sortedMap = new LinkedHashMap<>(); // Use a LinkedHashMap to preserve order
        for (Map.Entry<User, Reply> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    @Override
    public List<Reply> getRepliesByConversationIdAndContentType(int conversationId, String contentType) throws SQLException {
        return replyDao.getRepliesByConversationIdAndContentType(conversationId, contentType);
    }

    @Override
    public int getConversationId(int userOne, int userTwo) throws SQLException {
        // Kiểm tra nếu tồn tại cuộc trò chuyện từ userOne đến userTwo
        int id = conversationDao.getConversationId(userOne, userTwo);

        // Nếu không có, kiểm tra theo hướng ngược lại
        if (id == 0) {
            id = conversationDao.getConversationId(userTwo, userOne);
        }

        // Nếu vẫn không tìm thấy, tạo mới cuộc trò chuyện
        if (id == 0) {
            Conversation conversation = new Conversation();
            conversation.setUserOne(userOne);
            conversation.setUserTwo(userTwo);
            conversation.setTime(LocalDateTime.now());
            conversation.setStatus("active");
            id = conversationDao.addConversation(conversation);
            if (id > 0) {
                return id; // Trả về ID của cuộc trò chuyện mới
            } else {
                return 0; // Nếu có lỗi khi tạo cuộc trò chuyện mới, trả về 0
            }
        }

        return id; // Trả về ID của cuộc trò chuyện đã tồn tại
    }

    @Override
    public List<Reply> getListReplyConversation(int conversationId) throws SQLException {
        return replyDao.getReplies(conversationId);
    }

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
        if (reply.getId() == 0) {
            return null;
        }
        return reply;
    }

    @Override
    public User getMatchedUser(List<User> listUserConversation, int userTwo) throws SQLException {
        for (User user : listUserConversation) {
            if (user.getId() == userTwo) {
                return user;
            }
        }
        return null;
    }
}
