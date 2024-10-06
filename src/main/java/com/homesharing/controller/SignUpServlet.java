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
 * SignUpServlet handles user registration requests and processes
 * form submissions for account creation. It validates the input
 * and communicates with the UserService to register new users.
 *
 * @version 1.0
 * @since 2024-10-02
 * @author ManhNC
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private transient UserService userService;// Mark userService as transient
    private static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class); // Logger instance
    private static final String ERROR_ATTRIBUTE = "error"; // Define constant for error attribute

    /**
     * Initializes the SignUpServlet by creating instances of required services.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        PreferenceDAO preferenceDao = new PreferenceDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        PreferenceService preferenceService = new PreferenceServiceImpl(preferenceDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,preferenceService);
    }

    /**
     * Handles GET requests to display the sign-up page.
     * This method forwards the request to the sign-up JSP page.
     *
     * @param req  HttpServletRequest containing the client request.
     * @param resp HttpServletResponse used to send a response to the client.
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
                    // Redirect to home page on success
                    req.getSession().setAttribute("message", "Đăng kí thành công. Vui lòng kiểm tra email để xác thực tài khoản.");
                    req.getSession().setAttribute("messageType", "success");
                    resp.sendRedirect(req.getContextPath() + "/home-page");
                } else {
                    // Set error message if registration fails
                    req.setAttribute(ERROR_ATTRIBUTE, result);
                    ServletUtils.forwardToErrorPage(req, resp);
                }
            } catch (RuntimeException | SQLException | IOException e) {
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
