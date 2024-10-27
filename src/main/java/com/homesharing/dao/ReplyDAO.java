package com.homesharing.dao;

import com.homesharing.model.Reply;

import java.sql.SQLException;
import java.util.List;

public interface ReplyDAO {

    List<Reply> getReplies(int conversationId) throws SQLException;

    void addReply(Reply reply) throws SQLException;

    Reply getLastestReply(int conversationId) throws SQLException;

    void updateStatusForLatestReply(int conversationId, int userId) throws SQLException;
}
