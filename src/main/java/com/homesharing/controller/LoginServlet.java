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
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.User;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
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

/**
 * The {@code LoginServlet} handles user login requests.  It processes login attempts,
 * validates credentials, and manages session information upon successful login.  It handles both GET
 * requests (to display the login form) and POST requests (to process login submissions).
 * <p>
 * Bugs: None known.
 *
 * @author ManhNC
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class); // Logger instance for logging errors and important events

    private transient UserDAO userDao; // DAO for accessing user data
    private transient UserService userService; // Service for user-related operations

    /**
     * Initializes the servlet by instantiating the required DAO and service objects.
     * This method is called once when the servlet is loaded into memory.
     */
    @Override
    public void init() {
        userDao = new UserDAOImpl(); // Instantiate UserDAO for accessing user data
        TokenDAO tokenDao = new TokenDAOImpl(); // Instantiate TokenDAO for managing tokens
        TokenService tokenService = new TokenServiceImpl(tokenDao); // Instantiate TokenService with the TokenDAO
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,null); // Instantiate UserService with necessary dependencies
    }

    /**
     * Handles GET requests for the login page. This method forwards the request
     * to the "login.jsp" page to display the login form.
     *
     * @param req  HttpServletRequest object that contains the request
     *             the client has made of the servlet
     * @param resp HttpServletResponse object that contains the response
     *             the servlet sends to the client
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Forward request to the login page (login.jsp)
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to login page: {}", e.getMessage(), e);
            ServletUtils.handleError(req, resp, 404); // Handle any errors by sending an error response
        }
    }

    /**
     * Handles POST requests for login processing. This method receives login
     * credentials (email, password, and optional remember-me flag), validates
     * them using the UserService, and either redirects to the home page upon
     * success or displays error messages on the login page upon failure.
     *
     * @param req  HttpServletRequest object that contains the request
     *             the client has made of the servlet
     * @param resp HttpServletResponse object that contains the response
     *             the servlet sends to the client
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Retrieve login information from the request parameters
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            boolean rememberMe = req.getParameter("remember_me") != null; // Check if remember-me option is selected

            // Pass credentials to the userService for authentication
            String result = userService.login(email, password, rememberMe, resp);

            // Handle the result of the login attempt
            if (result.equals("success")) {
                // Login successful, redirect to home page
                req.getSession().setAttribute("message", "Đăng nhập thành công.");
                req.getSession().setAttribute("messageType", "success");
                resp.sendRedirect(req.getContextPath() + "/home-page");
            } else if (result.equals("not-verify")) {
                // Account not verified, forward to OTP input page
                User user = userDao.findUserByEmail(email); // Find user by email
                if (user != null) {
                    int userId = user.getId(); // Retrieve user ID
                    req.getSession().setAttribute("userId", userId); // Store user ID in session
                } else {
                    req.setAttribute("error", "Không tìm thấy người dùng trong hệ thống."); // Error message if user is not found
                    req.getRequestDispatcher("/login.jsp").forward(req, resp); // Forward back to login page
                    return;
                }
                req.getRequestDispatcher("/input-otp.jsp").forward(req, resp); // Forward to OTP input page
            } else {
                // Login failed, set error message and forward to login page
                req.setAttribute("error", result); // Set error message
                if(result.equals("Email hoặc mật khẩu không đúng")) {
                    req.setAttribute("email", email); // Preserve email for user to re-enter
                }
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (SQLException | ServletException | IOException | GeneralException e) {
            logger.error("Error processing login request: {}", e.getMessage(), e);
            ServletUtils.handleError(req, resp, 500); // Handle errors
        }
    }
}
