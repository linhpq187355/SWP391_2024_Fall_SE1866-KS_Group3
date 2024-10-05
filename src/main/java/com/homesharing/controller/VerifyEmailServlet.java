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

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.service.TokenService;
import com.homesharing.service.impl.TokenServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * VerifyEmailServlet handles the email verification process for users.
 * It retrieves the verification code and user ID from the request, verifies
 * the token, and provides feedback on whether the verification was successful.
 *
 * @version 1.0
 * @since 2024-10-02
 * @author ManhNC
 */
@WebServlet("/verify")
public class VerifyEmailServlet extends HttpServlet {
    private transient TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(VerifyEmailServlet.class); // Logger instance

    /**
     * Initializes the VerifyEmailServlet by creating an instance of the TokenService.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        TokenDAO tokenDao = null;
        try {
            tokenDao = new TokenDAOImpl();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.error("Lỗi khởi tạo TokenDAO trong init(): {}", e.getMessage(), e);
        }
        tokenService = new TokenServiceImpl(tokenDao);
    }

    /**
     * Handles GET requests for email verification.
     * Retrieves the verification code and user ID from the request, validates them,
     * and checks if the token is valid.
     *
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // 1. Get verification code and user ID from the URL parameters
        String verificationCode = request.getParameter("code");
        String userIDString = request.getParameter("userId");

        // 2. Convert userID from String to int
        int userId;
        try {
            userId = Integer.parseInt(userIDString);
        } catch (NumberFormatException e) {
            // Handle invalid user ID format
            forwardWithMessage(request, response, "Invalid user ID.");
            return; // Exit the method if the user ID is invalid
        }

        // 3. Check the token using the email service
        try {
            boolean check = tokenService.checkToken(verificationCode, userId);
            if (check) {
                forwardWithMessage(request, response, "Xác thực thành công.");
            } else {
                forwardWithMessage(request, response, "Xác thực không thành công.");
            }
        } catch (RuntimeException | SQLException e) {
            // Handle any errors that occur during token verification
            forwardWithMessage(request, response, "Lỗi khi xác thực email: " + e.getMessage());
        }
    }

    /**
     * Helper method to forward the request to the announcement page with a message.
     * The message is displayed on the target page.
     *
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     * @param message  The message to be displayed to the user.
     */
    private void forwardWithMessage(HttpServletRequest request, HttpServletResponse response, String message) {
        request.setAttribute("notificationMessage", message);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/announce.jsp");
            dispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            logger.error("Error forwarding to announce page: {}", e.getMessage(), e); // Log the exception for debugging
        }
    }
}
