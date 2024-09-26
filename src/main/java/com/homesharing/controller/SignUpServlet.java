package com.homesharing.controller;

import com.homesharing.dao.TokenDao;
import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.TokenDaoImpl;
import com.homesharing.dao.impl.UserDaoImpl;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.UserServiceImpl;
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
    private static final String ERROR_PAGE = "/404.jsp"; // Define constant for error page path
    private static final String FORWARD_ERROR_MESSAGE = "Forwarding to error page failed."; // Define constant for forward error message
    private static final String REDIRECT_ERROR_MESSAGE = "Redirecting to home page failed."; // Define constant for redirect error message
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDao userDao = new UserDaoImpl();
        TokenDao tokenDao = new TokenDaoImpl();
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao);
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
                    redirectToHomePage(req, resp);
                } else {
                    // Set error message if registration fails
                    req.setAttribute(ERROR_ATTRIBUTE, result);
                    forwardToErrorPage(req, resp);
                }
            } catch (RuntimeException e) {
                // Handle any runtime exceptions thrown by the service
                req.setAttribute(ERROR_ATTRIBUTE, "An error occurred during registration: " + e.getMessage());
                forwardToErrorPage(req, resp);
            }
        } else {
            // Set error message for invalid input
            req.setAttribute(ERROR_ATTRIBUTE, "Invalid data provided.");
            forwardToErrorPage(req, resp);
        }
    }

    /**
     * Redirects the user to the home page and handles exceptions.
     *
     * @param req  The HttpServletRequest containing the user's input.
     * @param resp The HttpServletResponse used to send a response to the client.
     */
    private void redirectToHomePage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } catch (IOException e) {
            handleError(resp, REDIRECT_ERROR_MESSAGE);
        }
    }

    /**
     * Forwards the request to the error page and handles exceptions.
     *
     * @param req  The HttpServletRequest containing the user's input.
     * @param resp The HttpServletResponse used to send a response to the client.
     */
    private void forwardToErrorPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            handleError(resp, FORWARD_ERROR_MESSAGE);
        }
    }

    /**
     * Handles errors by sending an error response with a given message.
     *
     * @param resp    The HttpServletResponse used to send a response to the client.
     * @param message The error message to send.
     */
    private void handleError(HttpServletResponse resp, String message) {
        try {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException e) {
            logger.error("An error occurred while sending error response: {}", e.getMessage(), e);
        }
    }
}
