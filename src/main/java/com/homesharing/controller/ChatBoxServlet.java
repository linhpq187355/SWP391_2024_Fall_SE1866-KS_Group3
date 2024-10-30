package com.homesharing.controller;

import com.homesharing.dao.ConversationDAO;
import com.homesharing.dao.ReplyDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.*;
import com.homesharing.model.Reply;
import com.homesharing.model.User;
import com.homesharing.service.ConversationService;
import com.homesharing.service.impl.ConversationServiceImpl;
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

@WebServlet("/chat-box")
public class ChatBoxServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ChatBoxServlet.class); // Logger instance
    private UserDAO userDAO;
    private ConversationDAO conversationDAO;
    private ConversationService conversationService;
    private ReplyDAO replyDAO;
    @Override
    public void init() throws ServletException {
        // Initialize the DAOs
        userDAO = new UserDAOImpl();
        conversationDAO = new ConversationDAOImpl();
        replyDAO = new ReplyDAOImpl();
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
        } catch (NumberFormatException e) {
            ServletUtils.forwardWithMessage(req, resp, "ID người dùng không hợp lệ, vui lòng đăng nhập lại.");
            return;
        }

        try {
            Map<User, Reply> listUserConversation = conversationService.getListUserConversation(uId);
            User matchedUser = userDAO.getUser(userTwo);

            if (matchedUser == null) {
                ServletUtils.forwardWithMessage(req, resp, "Người dùng không tồn tại trong cuộc trò chuyện.");
                return;
            }

            req.setAttribute("User", matchedUser);
            req.setAttribute("listUserConversation", listUserConversation);

            int conversationId = conversationService.getConversationId(uId, userTwo);
            if (conversationId == 0) {
                ServletUtils.forwardWithMessage(req, resp, "Không thể tìm thấy cuộc trò chuyện.");
                return;
            }
            String messageStatus = null;
            List<Reply> replies = conversationService.getListReplyConversation(conversationId);
            if(!replies.isEmpty()) {
                Reply reply = replies.get(replies.size() - 1);
                if(reply.getUserId() == uId) {
                    messageStatus = reply.getStatus();
                } else {
                    messageStatus = "sent";
                }
            }
            req.setAttribute("messageStatus", messageStatus);
            req.setAttribute("replies", replies);
            req.setAttribute("conversationId",conversationId);
        } catch (SQLException e) {
            logger.error("Error fetching data: {}", e.getMessage(), e); // Log the exception with stack trace
            ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra trong quá trình lấy dữ liệu.");
            return;
        }

        req.getRequestDispatcher("chat.jsp").forward(req, resp);
    }
}
