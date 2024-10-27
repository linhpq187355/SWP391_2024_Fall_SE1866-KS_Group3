package com.homesharing.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.ConversationDAOImpl;
import com.homesharing.dao.impl.ReplyDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.ConversationService;
import com.homesharing.service.impl.ConversationServiceImpl;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class ChatEndpoint {

    // Sử dụng ConcurrentHashMap để thread-safe
    private static ConcurrentHashMap<Integer, Session> onlineUsers = new ConcurrentHashMap<>();
    private static Set<Session> sessions = new HashSet<>();
    private static UserDAO userDAO = new UserDAOImpl();
    private static ConversationDAO conversationDAO = new ConversationDAOImpl();
    private static ReplyDAO replyDAO = new ReplyDAOImpl();
    private static ConversationService conversationService = new ConversationServiceImpl(userDAO, conversationDAO, replyDAO);
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

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
            if(msg != null && !msg.equals("")) {
                conversationService.addReply(msg, conversationId, sentId, null, null);
            }
            // Nếu có file, lưu các file và gửi thông tin file
            if (!files.isEmpty()) {
                for (Map<String, String> fileData : files) {
                    String fileUrl = fileData.get("url");
                    String fileType = fileData.get("type");
                    // Lưu thông tin file vào cơ sở dữ liệu hoặc xử lý tùy theo yêu cầu
                    conversationService.addReply(null, conversationId, sentId, fileType, fileUrl);
                }
            }

            Map<Integer, Integer> contactUsers = conversationDAO.contactUsersWithConversationId(sentId);
            Session ses = onlineUsers.get(sentId);
            if (ses != null && ses.isOpen()) {
                ses.getBasicRemote().sendText(message);
            }
            // Gửi tin nhắn đến tất cả session của người dùng đã liên hệ (và đang online)
            for (Map.Entry<Integer, Integer> entry : contactUsers.entrySet()) {
                int userId = entry.getKey();
                int conversationIdFromMap = entry.getValue();

                if (conversationIdFromMap == conversationId) { // Kiểm tra conversationId
                    Session receiverSession = onlineUsers.get(userId);
                    if (receiverSession != null && receiverSession.isOpen()) {
                        receiverSession.getBasicRemote().sendText(message);
                    }
                }
            }

        } else if (type.equals("start")) {
            onlineUsers.put(sentId, session);
        } else if (type.equals("seen")) {
            replyDAO.updateStatusForLatestReply(conversationId, sentId);
            Session ses = onlineUsers.get(receivedId);
            if (ses != null && ses.isOpen()) {
                ses.getBasicRemote().sendText(message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

}