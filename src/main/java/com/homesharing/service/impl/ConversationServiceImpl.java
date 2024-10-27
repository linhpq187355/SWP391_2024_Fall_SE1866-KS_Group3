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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<User, Reply> map = new HashMap<>();
        List<Conversation> listConversation = conversationDao.getAllConversationsByUserId(userId);
        for (Conversation conversation : listConversation) {
            if(conversation.getUserOne() == userId) {
                User user = userDao.getUser(conversation.getUserTwo());
                map.put(user,replyDao.getLastestReply(conversation.getId()));
            } else if(conversation.getUserTwo() == userId) {
                User user = userDao.getUser(conversation.getUserOne());
                map.put(user,replyDao.getLastestReply(conversation.getId()));
            }
        }
        return map;
    }

    @Override
    public int getConversationId(int userOne, int userTwo) throws SQLException {
        List<Conversation> listConversation = conversationDao.getAllConversationsByUserId(userOne);
        for (Conversation conversation : listConversation) {
            if(conversation.getUserOne() == userOne && conversation.getUserTwo() == userTwo
              || conversation.getUserTwo() == userOne && conversation.getUserOne() == userTwo) {
                return conversation.getId();
            }
        }
        return 0;
    }

    @Override
    public List<Reply> getListReplyConversation(int conversationId) throws SQLException {
        return replyDao.getReplies(conversationId);
    }

    @Override
    public boolean addReply(String text, int conversationId, int userId, String contentType, String contentUrl) throws SQLException {
        Reply reply = new Reply();
        reply.setText(text);
        reply.setUserId(userId);
        reply.setConversationId(conversationId);
        reply.setStatus("sent");
        reply.setTime(LocalDateTime.now());
        reply.setContentType(contentType);
        reply.setContentUrl(contentUrl);
        replyDao.addReply(reply);
        return true;
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
