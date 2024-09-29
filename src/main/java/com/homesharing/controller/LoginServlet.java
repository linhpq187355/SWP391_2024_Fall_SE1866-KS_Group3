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
 * The LoginServlet handles user login requests and manages
 * the login process by interacting with services and DAOs.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private transient UserService userService;// Service for user-related operations
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class); // Logger instance for logging events


    @Override
    public void init() {
        // Initialize DAOs and Services
        UserDAO userDao = new UserDAOImpl(); // Create instance of UserDAO
        TokenDAO tokenDao = new TokenDAOImpl(); // Create instance of TokenDAO
        TokenService tokenService = new TokenServiceImpl(tokenDao); // Create instance of TokenService
        // Inject UserDAO, TokenDAO and TokenService into UserService
        userService = new UserServiceImpl(userDao, tokenDao,tokenService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Forward request to the login page
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            // Log error if forwarding to the login page fails
            logger.error("Error forwarding to login page: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your request.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Retrieve user credentials from the login form
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            boolean rememberMe = req.getParameter("remember_me") != null;// Check if "remember me" option is selected

            // Log the email for debugging (make sure to not log sensitive information)
            logger.debug("Login attempt for email: {}", email);
            // Attempt to log in the user by calling the service
            String result = userService.login(email, password, rememberMe, resp);

            if (result.equals("success")) {
                // If login is successful, redirect to the home page
                resp.sendRedirect("home-page");
            } else {
                // If login fails, set error message and forward back to login page
                req.setAttribute("error", result);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            // Log error if processing the login request fails
            logger.error("Error processing login request: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your request.");
        }
    }
}
