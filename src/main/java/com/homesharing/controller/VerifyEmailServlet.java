package com.homesharing.controller;

import com.homesharing.dao.TokenDao;
import com.homesharing.dao.impl.TokenDaoImpl;
import com.homesharing.service.EmailService;
import com.homesharing.service.impl.EmailServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/verify")
public class VerifyEmailServlet extends HttpServlet {
    private EmailService emailService;

    @Override
    public void init(){
        TokenDao tokenDao = new TokenDaoImpl();
        emailService = new EmailServiceImpl(tokenDao);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get verification code and user ID from the URL parameters
        String verificationCode = request.getParameter("code");
        String userIDString = request.getParameter("userId");

        // 2. Convert userID from String to int
        int userId = -1;
        try {
            userId = Integer.parseInt(userIDString);
        } catch (NumberFormatException e) {
            // Handle invalid user ID format
            response.getWriter().write("Invalid user ID.");
            return; // Exit the method if the user ID is invalid
        }

        // 3. Check the token using the email service
        try {
            boolean check = emailService.checkToken(verificationCode, userId);
            if (check) {
                response.getWriter().write("Email verified.");
            } else {
                response.getWriter().write("Email not verified.");
            }
        } catch (RuntimeException e) {
            // Handle any errors that occur during token verification
            response.getWriter().write("Error while verifying email: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            response.getWriter().write("Unexpected error occurred.");
        }
    }
}
