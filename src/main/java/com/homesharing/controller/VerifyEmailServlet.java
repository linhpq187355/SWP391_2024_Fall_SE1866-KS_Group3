package com.homesharing.controller;

import com.homesharing.dao.TokenDao;
import com.homesharing.dao.impl.TokenDaoImpl;
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

@WebServlet("/verify")
public class VerifyEmailServlet extends HttpServlet {
    private transient TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(VerifyEmailServlet.class); // Logger instance
    @Override
    public void init(){
        TokenDao tokenDao = new TokenDaoImpl();
        tokenService = new TokenServiceImpl(tokenDao);
    }

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
        } catch (RuntimeException e) {
            // Handle any errors that occur during token verification
            forwardWithMessage(request, response, "Lỗi khi xác thực email: " + e.getMessage());
        }
    }
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
