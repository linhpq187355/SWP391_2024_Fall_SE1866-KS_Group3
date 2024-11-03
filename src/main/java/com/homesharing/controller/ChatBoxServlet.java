/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-02      1.0                 ManhNC         First Implement
 */
package com.homesharing.controller;

import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.*;
import com.homesharing.exception.GeneralException;
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
import java.util.List;
import java.util.Map;

/**
 * The {@code ChatBoxServlet} handles requests related to the chat functionality,
 * including fetching conversations and replies between users, and displaying
 * the chat interface.
 * <p>
 * Bugs: None known.
 * Author: ManhNC
 */
@WebServlet("/chat-box")
public class ChatBoxServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ChatBoxServlet.class); // Logger instance for logging errors

    private UserDAO userDAO; // DAO for accessing user data
    private ConversationDAO conversationDAO; // DAO for accessing conversation data
    private ConversationService conversationService; // Service for managing conversations
    private ReplyDAO replyDAO; // DAO for accessing replies
    private NotificationDAO notificationDAO; // DAO for accessing notifications
    private NotificationService notificationService; // Service for managing notifications

    /**
     * Initializes the servlet and its dependencies, including DAO and service instances.
     * This method is called once when the servlet is loaded into memory.
     */
    @Override
    public void init() throws ServletException {
        // Instantiate all necessary DAO implementations
        userDAO = new UserDAOImpl();
        conversationDAO = new ConversationDAOImpl();
        replyDAO = new ReplyDAOImpl();
        notificationDAO = new NotificationDAOImpl();

        // Instantiate services with the required DAOs
        notificationService = new NotificationServiceImpl(notificationDAO);
        conversationService = new ConversationServiceImpl(userDAO, conversationDAO, replyDAO);
    }

    /**
     * Handles GET requests for displaying the chat interface.
     * This method retrieves user conversations, unread notifications, and replies.
     *
     * @param req  HttpServletRequest object that contains the request made by the client
     * @param resp HttpServletResponse object that contains the response to be sent to the client
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the user ID from cookies and the ID of the user to chat with from request parameters
        String userId = CookieUtil.getCookie(req, "id");
        String rawUserTwo = req.getParameter("userId");

        // Validate that both user IDs are provided
        if (userId == null || rawUserTwo == null) {
            ServletUtils.handleError(req, resp, 500); // Handle the error if IDs are missing
            return;
        }

        int uId; // ID of the current user
        int userTwo; // ID of the user to chat with

        try {
            // Parse the user IDs from string to integer
            uId = Integer.parseInt(userId);
            userTwo = Integer.parseInt(rawUserTwo);

            // Count the number of new messages for the current user
            int countNewMessage = replyDAO.countNewMessages(uId);

            // Get the list of user conversations for the current user
            Map<User, Reply> listUserConversation = conversationService.getListUserConversation(uId);
            req.setAttribute("listUserConversation", listUserConversation); // Set the conversation list in request attributes
            req.setAttribute("countNewMessage", countNewMessage); // Set new message count in request attributes

            // Retrieve unread notifications for the current user
            List<Notification> notifications = notificationService.getUnReadNotifications(uId);
            int unreadCount = notifications.size(); // Count unread notifications
            req.setAttribute("unreadCount", unreadCount); // Set unread count in request attributes
            req.setAttribute("notifications", notifications); // Set notifications in request attributes
        } catch (NumberFormatException | SQLException e) {
            // Handle errors related to number format or SQL exceptions
            ServletUtils.handleError(req, resp, 500);
            return;
        }

        try {
            // Retrieve the conversation ID for the chat between the current user and the specified user
            int conversationId = conversationService.getConversationId(uId, userTwo);
            if (conversationId == 0) {
                ServletUtils.handleError(req, resp, 500); // Handle error if no conversation found
                return;
            }

            Conversation conversation = conversationDAO.getConversation(conversationId);
            if (conversation == null) {
                ServletUtils.forwardWithMessage(req, resp, "Không thể tìm thấy cuộc trò chuyện.");
                return;
            }

            // Get the list of user conversations again for setting attributes
            Map<User, Reply> listUserConversation = conversationService.getListUserConversation(uId);
            User matchedUser = userDAO.getUser(userTwo); // Retrieve the user object for the user to chat with

            if (matchedUser == null) {
                ServletUtils.handleError(req, resp, 500); // Handle error if matched user is not found
                return;
            }

            // Set matched user and conversation list in request attributes
            req.setAttribute("User", matchedUser);
            req.setAttribute("listUserConversation", listUserConversation);

            // Determine the status of the last message sent by the current user
            String messageStatus = "sent";
            List<Reply> replies = conversationService.getListReplyConversation(conversationId);
            if (!replies.isEmpty()) {
                Reply reply = replies.get(replies.size() - 1); // Get the last reply
                if (reply.getUserId() == uId) {
                    messageStatus = reply.getStatus(); // Update status if it's from the current user
                }
            }

            // Set various attributes related to the conversation in the request
            req.setAttribute("messageStatus", messageStatus);
            req.setAttribute("replies", replies); // Set replies in request attributes
            req.setAttribute("conversationId", conversationId); // Set conversation ID in request attributes
            req.setAttribute("conversation", conversation);
            // Forward the request to the chat JSP page for rendering
            req.getRequestDispatcher("chat.jsp").forward(req, resp);
        } catch (SQLException | ServletException | IOException | GeneralException e) {
            // Log any exceptions that occur during data fetching and handle the error
            logger.error("Error fetching data: {}", e.getMessage(), e); // Log the exception with stack trace
            ServletUtils.handleError(req, resp, 500);
        }
    }
}
