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

import com.homesharing.conf.Config;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.NotificationService;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.NotificationServiceImpl;
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
 * @author ManhNC
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    private transient UserService userService;// Mark userService as transient
    private static final Logger logger = LoggerFactory.getLogger(SignUpServlet.class); // Logger instance
    private static final String ERROR_ATTRIBUTE = "error"; // Define constant for error attribute
    private NotificationDAO notificationDAO;
    private NotificationService notificationService;
    /**
     * Initializes the SignUpServlet by creating instances of required services.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        notificationDAO = new NotificationDAOImpl();
        notificationService = new NotificationServiceImpl(notificationDAO);
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
            ServletUtils.handleError(req, resp, 404);
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
                int result = userService.registerUser(firstName, lastName, email, password, role);
                if (result > 0) {
                    //redirect to verify email
                    String url = Config.getBaseUrl();
                    notificationService.addNotification(result,"Chào mừng bạn đến với Rommify, chúc bạn có những trải nghiệm tuyệt vời ở đây.","System",url,"Chào mừng");
                    req.getSession().setAttribute("userId", result);
                    resp.sendRedirect(req.getContextPath() + "/verify");
                } else if (result == 0) {
                    req.setAttribute("error", "Email này đã được sử dụng, vui lòng nhập email khác.");
                    req.setAttribute("firstName", firstName);
                    req.setAttribute("lastName", lastName);
                    req.getRequestDispatcher("/sign-up.jsp").forward(req, resp);
                } else {
                    req.setAttribute("error", "Có lỗi xảy ra, vui lòng đăng kí lại.");
                    req.getRequestDispatcher("/sign-up.jsp").forward(req, resp);
                }
            } catch (RuntimeException | SQLException | IOException | ServletException e) {
                // Handle any runtime exceptions thrown by the service
                req.setAttribute(ERROR_ATTRIBUTE, "An error occurred during registration: " + e.getMessage());
                ServletUtils.handleError(req, resp, 500);
            }
        } else {
            // Set error message for invalid input
            req.setAttribute(ERROR_ATTRIBUTE, "Invalid data provided.");
            ServletUtils.handleError(req, resp, 500);
        }
    }
}
