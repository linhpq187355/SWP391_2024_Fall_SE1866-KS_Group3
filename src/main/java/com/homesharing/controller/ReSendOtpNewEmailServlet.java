package com.homesharing.controller;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/resend-otp-new-email")
public class ReSendOtpNewEmailServlet extends HttpServlet {
    private transient TokenServiceImpl tokenService;
    private static final Logger logger = LoggerFactory.getLogger(ReSendOtpNewEmailServlet.class); // Logger instance

    /**
     * Initializes the VerifyEmailServlet by creating an instance of the TokenService.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        TokenDAO tokenDao = new TokenDAOImpl();
        UserDAO userDao = new UserDAOImpl();
        tokenService = new TokenServiceImpl(tokenDao);
        tokenService.setUserDao(userDao);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get user ID from request parameter
        String userIdParam = req.getParameter("userId");
        String email = req.getParameter("email");
        if (userIdParam == null && email == null) {
            // Handle invalid user ID format
            ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra, vui lòng đăng nhập lại để xác thực.");
            return; // Exit the method if the user ID is invalid
        }
        int userId = Integer.parseInt(userIdParam);
        try {
            tokenService.sendToken(email, userId);
            // Check if userId is already in session
            HttpSession session = req.getSession();
            Integer sessionUserId = (Integer) session.getAttribute("userId");

            // If userId is not in session, add it
            if (sessionUserId == null || sessionUserId != userId) {
                session.setAttribute("userId", userId);
            }
            if(session.getAttribute("email") == null) {
                session.setAttribute("email", email);
            }
            req.getRequestDispatcher("/input-otp-2.jsp").forward(req, resp);
        } catch (SQLException e) {
            // Handle invalid user ID format
            ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra, vui lòng đăng nhập lại để xác thực.");
        }
    }

}
