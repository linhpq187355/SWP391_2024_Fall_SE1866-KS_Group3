/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-10      1.0                 ManhNC           Improved code and comments
 */
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

/**
 * The {@code ReSendOtpNewEmailServlet} handles requests to resend OTP to a new email address
 * during the email verification process.  It retrieves the user ID and new email from the request,
 * generates and sends a new OTP to the provided email, and forwards the user to the OTP input page.
 * <p>
 * Bugs: None known.
 *
 * @author ManhNC
 */
@WebServlet("/resend-otp")
public class ReSendOtpServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ReSendOtpServlet.class); // Logger instance

    private transient TokenServiceImpl tokenService;

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

    /**
     * Handles GET requests to resend the OTP to the new email.
     *
     * @param req  The HttpServletRequest object containing the userId and email parameters.
     * @param resp The HttpServletResponse object.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get user ID from request parameter
        String userIdParam = req.getParameter("userId");
        if (userIdParam == null) {
            // Handle invalid user ID format
            ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra, vui lòng đăng nhập lại để xác thực.");
            return; // Exit the method if the user ID is invalid
        }

        try {
            int userId = Integer.parseInt(userIdParam);
            tokenService.reSendToken(userId);
            // Check if userId is already in session
            HttpSession session = req.getSession();
            Integer sessionUserId = (Integer) session.getAttribute("userId");

            // If userId is not in session, add it
            if (sessionUserId == null || sessionUserId != userId) {
                session.setAttribute("userId", userId);
            }
            req.getRequestDispatcher("/input-otp.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            logger.warn("Invalid userId parameter: {}", userIdParam);
            ServletUtils.handleError(req, resp, 404);
        } catch (SQLException e) {
            logger.error("Error resending OTP: {}", e.getMessage(), e); // Log the exception with stack trace
            // Handle invalid user ID format
            ServletUtils.forwardWithMessage(req, resp, "Có lỗi xảy ra, vui lòng đăng nhập lại để xác thực.");
        }
    }

}
