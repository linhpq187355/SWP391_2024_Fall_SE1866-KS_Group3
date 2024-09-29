package com.homesharing.controller;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.service.TokenService;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The VerifyEmailServlet handles email verification requests.
 * It checks the verification code against a stored token for the user.
 */
@WebServlet("/verify")
public class VerifyEmailServlet extends HttpServlet {
    private transient TokenService tokenService; // Service for token operations

    @Override
    public void init(){
        // Initialize the TokenService with the TokenDAO implementation
        TokenDAO tokenDao = new TokenDAOImpl();
        tokenService = new TokenServiceImpl(tokenDao);
    }

    /**
     * Handles the HTTP GET request for email verification.
     *
     * @param request  the HttpServletRequest object that contains the request
     * @param response the HttpServletResponse object that contains the response
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
            ServletUtils.forwardWithMessage(request, response, "Invalid user ID.");
            return; // Exit the method if the user ID is invalid
        }

        // 3. Check the token using the email service
        try {
            boolean check = tokenService.checkToken(verificationCode, userId);
            if (check) {
                ServletUtils.forwardWithMessage(request, response, "Xác thực thành công.");
            } else {
                ServletUtils.forwardWithMessage(request, response, "Xác thực không thành công.");
            }
        } catch (RuntimeException e) {
            // Handle any errors that occur during token verification
            ServletUtils.forwardWithMessage(request, response, "Lỗi khi xác thực email: " + e.getMessage());
        }
    }
}
