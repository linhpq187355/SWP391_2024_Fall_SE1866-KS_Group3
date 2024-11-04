/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-22      1.0                 ManhNC         First Implement
 */
package com.homesharing.controller;

import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.ConversationDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.dao.impl.ReplyDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Conversation;
import com.homesharing.model.Notification;
import com.homesharing.model.Reply;
import com.homesharing.model.User;
import com.homesharing.service.ConversationService;
import com.homesharing.service.NotificationService;
import com.homesharing.service.impl.ConversationServiceImpl;
import com.homesharing.service.impl.NotificationServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servlet for handling chat information retrieval.
 * This servlet retrieves conversation details, messages, and user information for display on the chat information page.
 */
@WebServlet("/chat-information")
public class ChatInformationServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ChatInformationServlet.class); // Logger instance
    private UserDAO userDAO;
    private ConversationDAO conversationDAO;
    private ConversationService conversationService;
    private ReplyDAO replyDAO;
    private NotificationDAO notificationDAO;
    private NotificationService notificationService;
    @Override
    public void init() throws ServletException {
        // Initialize the DAOs
        userDAO = new UserDAOImpl();
        conversationDAO = new ConversationDAOImpl();
        replyDAO = new ReplyDAOImpl();
        notificationDAO = new NotificationDAOImpl();
        notificationService = new NotificationServiceImpl(notificationDAO);
        conversationService = new ConversationServiceImpl(userDAO, conversationDAO,replyDAO);
    }

    /**
     * Handles GET requests to retrieve chat information.
     *
     * @param req The HTTP servlet request.
     * @param resp The HTTP servlet response.
     * @throws ServletException If a servlet error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = CookieUtil.getCookie(req, "id");
        String rawUserTwo = req.getParameter("userId");

        // Check for null user IDs
        if (userId == null || rawUserTwo == null) {
            ServletUtils.handleError(req, resp, 500);
            return;
        }

        int uId;
        int userTwo;
        try {
            uId = Integer.parseInt(userId);
            userTwo = Integer.parseInt(rawUserTwo);

            // Retrieve unread messages and notification counts
            int countNewMessage = replyDAO.countNewMessages(uId);
            Map<User, Reply> listUserConversation = conversationService.getListUserConversation(uId);
            req.setAttribute("listUserConversation", listUserConversation);
            req.setAttribute("countNewMessage", countNewMessage);
            List<Notification> notifications = notificationService.getUnReadNotifications(uId);
            int unreadCount = notifications.size();
            req.setAttribute("unreadCount", unreadCount);
            req.setAttribute("notifications", notifications);
        } catch (NumberFormatException | SQLException e) {
            ServletUtils.handleError(req, resp, 500);
            return;
        }

        try {
            int conversationId = conversationService.getConversationId(uId, userTwo);

            // Check if conversation ID is valid
            if (conversationId == 0) {
                ServletUtils.handleError(req, resp, 500); // Handle the error condition
                return;
            }

            Conversation conversation = conversationDAO.getConversation(conversationId);

            User matchedUser = userDAO.getUser(userTwo);

            // Check if conversation and user data are retrieved successfully
            if (conversation == null || matchedUser == null) {
                ServletUtils.handleError(req, resp, 500); // Handle missing data
                return;
            }
            List<Reply> replies = conversationService.getListReplyConversation(conversationId);

            // Categorize replies by content type
            List<Reply> textReplies = new ArrayList<>();
            List<Reply> mediaReplies = new ArrayList<>();
            List<Reply> fileReplies = new ArrayList<>();

            for (Reply reply : replies) {
                String contentType = reply.getContentType();
                if (contentType == null) {
                    textReplies.add(reply); // Thêm vào danh sách textReplies
                } else if ("image".equalsIgnoreCase(contentType) || "video".equalsIgnoreCase(contentType)) {
                    mediaReplies.add(reply); // Thêm vào danh sách mediaReplies
                } else if ("file".equalsIgnoreCase(contentType)) {
                    fileReplies.add(reply); // Thêm vào danh sách fileReplies
                }
            }

            // Tạo các biến để chứa số lượng của từng danh sách
            int textReplyCount = textReplies.size();   // Số lượng reply có contentType là null (text)
            int mediaReplyCount = mediaReplies.size();  // Số lượng reply có contentType là image hoặc video
            int fileReplyCount = fileReplies.size();    // Số lượng reply có contentType là file
            req.setAttribute("textReplies", textReplies);
            req.setAttribute("mediaReplies", mediaReplies);
            req.setAttribute("fileReplies", fileReplies);
            req.setAttribute("textReplyCount", textReplyCount);
            req.setAttribute("mediaReplyCount", mediaReplyCount);
            req.setAttribute("fileReplyCount", fileReplyCount);
            req.setAttribute("User", matchedUser);
            req.setAttribute("conversation", conversation);
            req.setAttribute("conversationId",conversationId);
        } catch (SQLException e) {
            logger.error("Error fetching data: {}", e.getMessage(), e); // Log the exception with stack trace
            ServletUtils.handleError(req, resp, 500);
            return;
        }
        try {
            req.getRequestDispatcher("chat-information.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            ServletUtils.handleError(req, resp, 500);
        }

    }
}
