package com.homesharing.controller;

import com.homesharing.dao.TokenDao;
import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.TokenDaoImpl;
import com.homesharing.dao.impl.UserDaoImpl;
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
 * Servlet for handling user sign-up requests.
 * This servlet processes registration form submissions and communicates with the UserService.
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private transient  UserService userService;// Mark userService as transient
    private static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class); // Logger instance
    private static final String ERROR_ATTRIBUTE = "error"; // Define constant for error attribute
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDao userDao = new UserDaoImpl();
        TokenDao tokenDao = new TokenDaoImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService);
    }

    /**
     * Handles GET requests to display the sign-up page.
     *
     * @param req  The HttpServletRequest containing the user's input.
     * @param resp The HttpServletResponse used to send a response to the client.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Redirect to sign-up page
            req.getRequestDispatcher("/sign-up.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to sign-up page: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your request.");
        }
    }

    /**
     * Handles POST requests for user registration.
     * Validates input, registers the user, and handles potential errors.
     *
     * @param req  The HttpServletRequest containing the user's input.
     * @param resp The HttpServletResponse used to send a response to the client.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // Retrieve user input from the request
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String role = req.getParameter("role");

        // Validate user input
        boolean isValid = userService.validateUserInput(firstName, lastName, email, password, confirmPassword, role);

        if (isValid) {
            try {
                // Attempt to register the user
                String result = userService.registerUser(firstName, lastName, email, password, role);
                if ("success".equals(result)) {
                    /// Redirect to home page on success
                    ServletUtils.redirectToHomePage(req, resp);
                } else {
                    // Set error message if registration fails
                    req.setAttribute(ERROR_ATTRIBUTE, result);
                    ServletUtils.forwardToErrorPage(req, resp);
                }
            } catch (RuntimeException e) {
                // Handle any runtime exceptions thrown by the service
                req.setAttribute(ERROR_ATTRIBUTE, "An error occurred during registration: " + e.getMessage());
                ServletUtils.forwardToErrorPage(req, resp);
            }
        } else {
            // Set error message for invalid input
            req.setAttribute(ERROR_ATTRIBUTE, "Invalid data provided.");
            ServletUtils.forwardToErrorPage(req, resp);
        }
    }
}
