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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The LogoutServlet handles user logout requests.
 * It interacts with services to manage the logout process.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private transient UserService userService; // Service for user-related operations
    private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class); // Logger instance

    @Override
    public void init() {
        // Initialize DAOs and Services
        UserDAO userDao = new UserDAOImpl(); // Create instance of UserDAO
        TokenDAO tokenDao = new TokenDAOImpl(); // Create instance of TokenDAO
        TokenService tokenService = new TokenServiceImpl(tokenDao); // Create instance of TokenService
        // Inject UserDAO, TokenDAO and TokenService into UserService
        userService = new UserServiceImpl(userDao, tokenDao,tokenService);
    }

    /**
     * Handles the HTTP GET request to log out the user.
     *
     * @param req  the HttpServletRequest object that contains the request
     * @param resp the HttpServletResponse object that contains the response
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            // Process the logout request
            String logoutMessage = userService.logout(resp);
            logger.info(logoutMessage); // Log the logout message
            resp.sendRedirect("home-page"); // Redirect to the home page after logout
        } catch (Exception e) {
            // Log error if processing the logout request fails
            logger.error("Error processing logout request: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your logout request."); // Handle the error response
        }

    }
}
