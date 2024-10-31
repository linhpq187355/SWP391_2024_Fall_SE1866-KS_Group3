package com.homesharing.controller;

import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.ConversationDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.dao.impl.ReplyDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.*;
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

@WebServlet("/notification")
public class NotificationServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServlet.class); // Logger instance

    private NotificationDAO notificationDAO;
    private NotificationService notificationService;
    private UserDAO userDAO;
    private ReplyDAO replyDAO;
    private ConversationDAO conversationDAO;
    private ConversationService conversationService;
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        replyDAO = new ReplyDAOImpl();
        notificationDAO = new NotificationDAOImpl();
        conversationDAO = new ConversationDAOImpl();
        notificationService = new NotificationServiceImpl(notificationDAO);
        conversationService = new ConversationServiceImpl(userDAO, conversationDAO, replyDAO);
    }

    /**
     * Handles HTTP GET requests.
     * This method retrieves new homes and their prices, sets them as attributes, and forwards
     * the request to the home page JSP for rendering.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if an input or output error is detected when the servlet handles the GET request
     * @throws IOException      if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = CookieUtil.getCookie(req, "id");
        String type = req.getParameter("type");
        if (userId == null) {
            ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra, vui lòng đăng nhập lại.");
            return;
        }
        if(type == null || type.equals("")) {
            type = "System";
        }
        if(userId!=null){
            try {
                int uId = Integer.parseInt(userId);
                int countNewMessage = replyDAO.countNewMessages(uId);
                Map<User, Reply> listUserConversation = conversationService.getListUserConversation(uId);
                req.setAttribute("listUserConversation", listUserConversation);
                req.setAttribute("countNewMessage", countNewMessage);
                List<Notification> notifications = notificationService.getUnReadNotifications(uId);
                List<Notification> notificationListOfType = notificationDAO.getSentNotificationsByTypeAndReceiverId(uId, type);
                int unreadCount = notifications.size();
                req.setAttribute("notificationListOfType", notificationListOfType);
                req.setAttribute("unreadCount", unreadCount);
                req.setAttribute("type", type);
                req.setAttribute("notifications", notifications);
            } catch (SQLException | GeneralException e) {
                logger.error("Error home-page: {}", e.getMessage(), e); // Log the exception with stack trace
                // Handle invalid user ID format
                ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra, vui lòng đăng nhập lại.");
            }
        }
        // Forward the request to the JSP page for rendering
        req.getRequestDispatcher("/notification.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String userId = CookieUtil.getCookie(req, "id");
        String type = req.getParameter("type");
        if (type == null || type.isEmpty()) {
            type = "System"; // Giá trị mặc định nếu type là null hoặc rỗng
        }
        if (userId == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            int uId = Integer.parseInt(userId);

            if ("markRead".equals(action)) {
                notificationDAO.markAsSeenByTypeAndReceiverId(uId,type); // Thực hiện đánh dấu đã đọc
                resp.setStatus(HttpServletResponse.SC_OK);
            } else if ("deleteAll".equals(action)) {
                notificationDAO.deleteNotificationsByTypeAndReceiverId(uId,type); // Thực hiện xóa tất cả
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException | GeneralException e) {
            logger.error("Error processing notification action: {}", e.getMessage(), e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


}
