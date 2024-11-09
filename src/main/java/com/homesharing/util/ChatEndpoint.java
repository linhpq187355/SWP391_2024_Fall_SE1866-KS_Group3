/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-20      1.0                 ManhNC         First Implement
 */
package com.homesharing.util;

import com.google.gson.*;
import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.ConversationDAOImpl;
import com.homesharing.dao.impl.ReplyDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Reply;
import com.homesharing.service.ConversationService;
import com.homesharing.service.impl.ConversationServiceImpl;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket endpoint for real-time chat functionality.
 * Manages user connections, message broadcasting, and various chat actions.
 */
@ServerEndpoint("/chat")
public class ChatEndpoint {

    // Using ConcurrentHashMap for thread-safe
    private static ConcurrentHashMap<Integer, Session> onlineUsers = new ConcurrentHashMap<>();
    private static Set<Session> sessions = new HashSet<>();
    private static UserDAO userDAO = new UserDAOImpl();
    private static ConversationDAO conversationDAO = new ConversationDAOImpl();
    private static ReplyDAO replyDAO = new ReplyDAOImpl();
    private static ConversationService conversationService = new ConversationServiceImpl(userDAO, conversationDAO, replyDAO);

    /**
     * Called when a new client connects to the WebSocket.
     * Adds the session to the set of active sessions.
     * @param session The WebSocket session of the newly connected client.
     */
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    /**
     * Called when a message is received from a client.
     * Processes the message based on its type and performs corresponding actions.
     * @param message The message received from the client.
     * @param session The WebSocket session of the client that sent the message.
     * @throws IOException If an I/O error occurs during message processing.
     * @throws SQLException If a database error occurs during message processing.
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, SQLException {
        JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
        int receivedId = jsonMessage.get("received") != null ? jsonMessage.get("received").getAsInt() : -1; // Lấy receivedId
        int conversationId = jsonMessage.get("conversationId") != null ? jsonMessage.get("conversationId").getAsInt() : -1; // Lấy conversationId
        String msg = jsonMessage.has("message") ? jsonMessage.get("message").getAsString() : ""; // Lấy nội dung message
        String type = jsonMessage.has("type") ? jsonMessage.get("type").getAsString() : ""; // Lấy type
        int sentId = jsonMessage.get("send") != null ? jsonMessage.get("send").getAsInt() : -1; // Lấy sentId

        // Lấy danh sách files từ jsonMessage
        JsonArray filesArray = jsonMessage.has("files") ? jsonMessage.getAsJsonArray("files") : new JsonArray();
        List<Map<String, String>> files = new ArrayList<>();

        // Chuyển đổi JsonArray thành List<Map>
        for (JsonElement fileElement : filesArray) {
            JsonObject fileObject = fileElement.getAsJsonObject();
            Map<String, String> fileData = new HashMap<>();
            fileData.put("url", fileObject.get("url").getAsString());
            fileData.put("type", fileObject.get("type").getAsString());
            files.add(fileData);
        }

        if (type.equals("message")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeUtil())
                    .create();
            List<Reply> replies = new ArrayList<>();
            if(msg != null && !msg.equals("")) {
                Reply messageReply = conversationService.addReply(msg, conversationId, sentId, null, null);
                replies.add(messageReply);
            }
            // Nếu có file, lưu các file và gửi thông tin file
            if (!files.isEmpty()) {
                for (Map<String, String> fileData : files) {
                    String fileUrl = fileData.get("url");
                    String fileType = fileData.get("type");
                    // Lưu thông tin file vào cơ sở dữ liệu hoặc xử lý tùy theo yêu cầu
                    Reply mediaReply = conversationService.addReply(null, conversationId, sentId, fileType, fileUrl);
                    replies.add(mediaReply);
                }
            }
            // Chuyển danh sách Reply thành JSON
            String repliesJson = gson.toJson(replies);
            JsonArray repliesArray = JsonParser.parseString(repliesJson).getAsJsonArray();
            jsonMessage.add("replies", repliesArray);
            sendMessage(jsonMessage.toString(), sentId, conversationId);
        } else if (type.equals("start")) {
            session.getUserProperties().put("userId", sentId);
            onlineUsers.put(sentId, session);
            sendMessage(message, sentId, conversationId);
        } else if (type.equals("seen")) {
            replyDAO.updateStatusForLatestReply(conversationId, sentId);
            Session ses = onlineUsers.get(receivedId);
            if (ses != null && ses.isOpen()) {
                ses.getBasicRemote().sendText(message);
            }
        } else if (type.equals("ask")) {
            if(onlineUsers.containsKey(receivedId) && onlineUsers.get(receivedId) != null) {
                jsonMessage.addProperty("type", "yes");
                Session ses = onlineUsers.get(sentId);
                if (ses != null && ses.isOpen()) {
                    try {
                        ses.getBasicRemote().sendText(jsonMessage.toString());
                    } catch (IOException e) {
                        throw new GeneralException(e.getMessage());
                    }
                }
            } else {
                jsonMessage.addProperty("type", "no");
                Session ses = onlineUsers.get(sentId);
                if (ses != null && ses.isOpen()) {
                    try {
                        ses.getBasicRemote().sendText(jsonMessage.toString());
                    } catch (IOException e) {
                        throw new GeneralException(e.getMessage());
                    }
                }
            }
        } else if (type.equals("block")) {
            boolean isBlocked = conversationDAO.updateConversationStatus(conversationId, sentId, "block");
            jsonMessage.addProperty("isBlocked",  isBlocked ? "yes" : "no");
            Session ses = onlineUsers.get(sentId);
            if (ses != null && ses.isOpen()) {
                try {
                    ses.getBasicRemote().sendText(jsonMessage.toString());
                } catch (IOException e) {
                    throw new GeneralException(e.getMessage());
                }
            }
            Session ses2 = onlineUsers.get(receivedId);
            if (ses2 != null && ses2.isOpen()) {
                try {
                    ses2.getBasicRemote().sendText(jsonMessage.toString());
                } catch (IOException e) {
                    throw new GeneralException(e.getMessage());
                }
            }
        } else if (type.equals("unblock")) {
            boolean isUnBlocked = conversationDAO.updateConversationStatus(conversationId, sentId, "unblock");
            if(isUnBlocked) {
                Session ses = onlineUsers.get(sentId);
                if (ses != null && ses.isOpen()) {
                    try {
                        ses.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        throw new GeneralException(e.getMessage());
                    }
                }
                Session ses2 = onlineUsers.get(receivedId);
                if (ses2 != null && ses2.isOpen()) {
                    try {
                        ses2.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        throw new GeneralException(e.getMessage());
                    }
                }
            }
        } else if (type.equals("delete")) {
            int replyId = jsonMessage.get("replyId") != null ? jsonMessage.get("replyId").getAsInt() : -1; // Lấy receivedId
            boolean isDeleted = replyDAO.deleteReplyById(replyId,conversationId,sentId);
                jsonMessage.addProperty("isDeleted",  isDeleted ? "yes" : "no");
                Session ses = onlineUsers.get(sentId);
                if (ses != null && ses.isOpen()) {
                    try {
                        ses.getBasicRemote().sendText(jsonMessage.toString());
                    } catch (IOException e) {
                        throw new GeneralException(e.getMessage());
                    }
                }
                Session ses2 = onlineUsers.get(receivedId);
                if (ses2 != null && ses2.isOpen()) {
                    try {
                        ses2.getBasicRemote().sendText(jsonMessage.toString());
                    } catch (IOException e) {
                        throw new GeneralException(e.getMessage());
                    }
                }
        }
    }

    /**
     * Called when a client disconnects from the WebSocket.
     * Removes the session from the set of active sessions and online users.
     * @param session The WebSocket session of the disconnected client.
     */
    @OnClose
    public void onClose(Session session) {
        int userId = (int) session.getUserProperties().get("userId");
        onlineUsers.remove(userId);
        sessions.remove(session);
    }

    /**
     * Sends a message to the specified user and all other users in the same conversation.
     * @param message The message to be sent.
     * @param senderId The ID of the user who sent the message.
     * @param conversationId The ID of the conversation.
     * @throws SQLException If a database error occurs during message sending.
     */
    private void sendMessage(String message, int senderId, int conversationId) throws SQLException {

        Map<Integer, Integer> contactUsers = conversationDAO.contactUsersWithConversationId(senderId);
        Session ses = onlineUsers.get(senderId);
        if (ses != null && ses.isOpen()) {
            try {
                ses.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new GeneralException(e.getMessage());
            }
        }
        // Gửi tin nhắn đến tất cả session của người dùng đã liên hệ (và đang online)
        for (Map.Entry<Integer, Integer> entry : contactUsers.entrySet()) {
            int userId = entry.getKey();
            int conversationIdFromMap = entry.getValue();

            if (conversationIdFromMap == conversationId) { // Kiểm tra conversationId
                Session receiverSession = onlineUsers.get(userId);
                if (receiverSession != null && receiverSession.isOpen()) {
                    try {
                        receiverSession.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        throw new GeneralException(e.getMessage());
                    }
                }
            }
        }


    }

}