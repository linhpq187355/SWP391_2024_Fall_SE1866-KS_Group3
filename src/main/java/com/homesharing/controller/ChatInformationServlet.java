package com.homesharing.controller;

import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.ConversationDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.dao.impl.ReplyDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = CookieUtil.getCookie(req, "id");
        String rawUserTwo = req.getParameter("userId");

        if (userId == null || rawUserTwo == null) {
            ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra, vui lòng đăng nhập lại.");
            return;
        }

        int uId;
        int userTwo;
        try {
            uId = Integer.parseInt(userId);
            userTwo = Integer.parseInt(rawUserTwo);
            int countNewMessage = replyDAO.countNewMessages(uId);
            Map<User, Reply> listUserConversation = conversationService.getListUserConversation(uId);
            req.setAttribute("listUserConversation", listUserConversation);
            req.setAttribute("countNewMessage", countNewMessage);
            List<Notification> notifications = notificationService.getUnReadNotifications(uId);
            int unreadCount = notifications.size();
            req.setAttribute("unreadCount", unreadCount);
            req.setAttribute("notifications", notifications);
        } catch (NumberFormatException | SQLException e) {
            ServletUtils.forwardWithMessage(req, resp, "ID người dùng không hợp lệ, vui lòng đăng nhập lại.");
            return;
        }

        try {
            int conversationId = conversationService.getConversationId(uId, userTwo);
            if (conversationId == 0) {
                ServletUtils.forwardWithMessage(req, resp, "Không thể tìm thấy cuộc trò chuyện.");
                return;
            }

            User matchedUser = userDAO.getUser(userTwo);

            if (matchedUser == null) {
                ServletUtils.forwardWithMessage(req, resp, "Người dùng không tồn tại trong cuộc trò chuyện.");
                return;
            }
            List<Reply> replies = conversationService.getListReplyConversation(conversationId);

            // Khởi tạo ba danh sách con
            List<Reply> textReplies = new ArrayList<>();   // Danh sách cho contentType là null (text)
            List<Reply> mediaReplies = new ArrayList<>();   // Danh sách cho contentType là image hoặc video
            List<Reply> fileReplies = new ArrayList<>();    // Danh sách cho contentType là file

            // Phân loại các reply
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
            req.setAttribute("conversationId",conversationId);
        } catch (SQLException e) {
            logger.error("Error fetching data: {}", e.getMessage(), e); // Log the exception with stack trace
            ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra trong quá trình lấy dữ liệu.");
            return;
        }

        req.getRequestDispatcher("chat-information.jsp").forward(req, resp);
    }
}
