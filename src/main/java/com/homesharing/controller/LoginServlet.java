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

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class); // Logger instance

    private transient UserDAO userDao;
    private transient UserService userService;// Mark userService as transient

    /**
     * Initializes the servlet by instantiating the required DAOs and services.
     */
    @Override
    public void init() {
        userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,null);
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
            // Redirect to sign-up page
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to login page: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your request.");
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
            // Get information from login form
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            boolean rememberMe = req.getParameter("remember_me") != null;

            // Log the email for debugging (make sure to not log sensitive information)
            logger.debug("Login attempt for email: {}", email);
            // Pass information to service
            String result = userService.login(email, password, rememberMe, resp);

            if (result.equals("success")) {
                // Login successful, redirect to home page
                req.getSession().setAttribute("message", "Đăng nhập thành công.");
                req.getSession().setAttribute("messageType", "success");
                resp.sendRedirect(req.getContextPath() + "/home-page");
            } else if (result.equals("not-verify")) {
                int userId = userDao.findUserByEmail(email).getId();
                req.getSession().setAttribute("userId",userId);
                req.getRequestDispatcher("/input-otp.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", result);
                if(result.equals("Email hoặc mật khẩu không đúng")) {
                    req.setAttribute("email", email);
                }
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("Error processing login request: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your request.");
        }
    }
}
