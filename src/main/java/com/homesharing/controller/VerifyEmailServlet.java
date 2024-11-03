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

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.PreferenceServiceImpl;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.RequestDispatcher;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * VerifyEmailServlet handles the email verification process for users.
 * It retrieves the verification code and user ID from the request, verifies
 * the token, and provides feedback on whether the verification was successful.
 *
 * @author ManhNC
 */
@WebServlet("/verify")
public class VerifyEmailServlet extends HttpServlet {
    private transient UserService userService;// Mark userService as transient
    private transient TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(VerifyEmailServlet.class); // Logger instance

    /**
     * Initializes the VerifyEmailServlet by creating an instance of the TokenService.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        PreferenceDAO preferenceDao = new PreferenceDAOImpl();
        tokenService = new TokenServiceImpl(tokenDao);
        PreferenceService preferenceService = new PreferenceServiceImpl(preferenceDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,preferenceService);
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
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if(userId == null) {
            // Handle invalid user ID format
            forwardWithMessage(request, response, "Có lỗi xảy ra, vui lòng đăng nhập lại để xác thực.");
            return; // Exit the method if the user ID is invalid
        }
        HttpSession session = request.getSession();
        Map<Integer, Integer> otpAttemptsMap = (Map<Integer, Integer>) session.getAttribute("otpAttemptsMap");
        if (otpAttemptsMap == null) {
            otpAttemptsMap = new HashMap<>();
            session.setAttribute("otpAttemptsMap", otpAttemptsMap);
        }
        request.getSession().setMaxInactiveInterval(5 * 60);
        try {
            request.getRequestDispatcher("/input-otp.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            // Handle any runtime exceptions thrown by the service or servlet
            request.setAttribute("error", "An error occurred during registration: " + e.getMessage());
            ServletUtils.forwardToErrorPage(request, response);
        }
    }

    /**
     * Handles POST requests for email verification.
     * Retrieves the verification code and user ID from the request, validates them,
     * and checks if the token is valid.
     *
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        //Get verification code from the URL parameters
        String verificationCode = request.getParameter("otp");

        //Get userId from session
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if(userId == null) {
            // Handle invalid user ID format
            forwardWithMessage(request, response, "Có lỗi xảy ra, vui lòng đăng nhập lại để xác thực.");
            return; // Exit the method if the user ID is invalid
        }

        Map<Integer, Integer> otpAttemptsMap = (Map<Integer, Integer>) request.getSession().getAttribute("otpAttemptsMap");
        if (otpAttemptsMap == null) {
            otpAttemptsMap = new HashMap<>();
            request.getSession().setAttribute("otpAttemptsMap", otpAttemptsMap);
        }
        int otpAttempts = otpAttemptsMap.getOrDefault(userId, 0);
        //Check the token using the email service
        try {
            boolean check = tokenService.checkToken(verificationCode, userId, LocalDateTime.now());
            if (check) {
                otpAttemptsMap.put(userId, 0);
                userService.putAccountOnCookie(userId, response);
                request.getSession().removeAttribute("userId");
                // Login successful, redirect to home page
                request.getSession().setAttribute("message", "Xác thực thành công.");
                request.getSession().setAttribute("messageType", "success");
                response.sendRedirect(request.getContextPath() + "/home-page");
            } else {
                otpAttempts++;
                otpAttemptsMap.put(userId, otpAttempts);
                if (otpAttempts >= 5) {
                    request.setAttribute("error", "Bạn đã nhập sai OTP quá 5 lần. Vui lòng thử lại sau.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "OTP không hợp lệ.");
                    request.getRequestDispatcher("/input-otp.jsp").forward(request, response);
                }
            }
        } catch (RuntimeException | SQLException | IOException | ServletException e) {
            // Handle any errors that occur during token verification
            forwardWithMessage(request, response, "Lỗi khi xác thực: " + e.getMessage());
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
        request.setAttribute("error", message);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/500.jsp");
            dispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            logger.error("Error forwarding to announce page: {}", e.getMessage(), e); // Log the exception for debugging
        }
    }
}
