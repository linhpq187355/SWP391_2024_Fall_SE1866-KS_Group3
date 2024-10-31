/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-03      1.0                 ManhNC         First Implement
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
 * The {@code CreateAccountServlet} handles user registration requests.
 * It receives user input, validates the input, and interacts with the
 * {@link UserService} to create a new user account.
 * <p>
 * This servlet supports both GET (to display the registration form)
 * and POST (to process the registration request) methods.
 * <p>
 * Bugs: None known.
 *
 * @author ManhNC
 */
@WebServlet("/dashboard/create-account")
public class CreateAccountServlet extends HttpServlet {

    private transient UserService userService;// Mark userService as transient

    private static final Logger logger = LoggerFactory.getLogger(CreateAccountServlet.class); // Logger instance
    private static final String ERROR_ATTRIBUTE = "error"; // Define constant for error attribute

    /**
     * Initializes the SignUpServlet by creating instances of required services.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        // Initialize DAOs
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        PreferenceDAO preferenceDao = new PreferenceDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        PreferenceService preferenceService = new PreferenceServiceImpl(preferenceDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,preferenceService);
    }

    /**
     * Handles GET requests to display the create account page.
     * Forwards the request to the `create-account.jsp` page.
     *
     * @param req  The HttpServletRequest object.
     * @param resp The HttpServletResponse object.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Redirect to sign-up page
            req.getRequestDispatcher("/create-account.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to create-account.jsp: {}", e.getMessage(), e);
            ServletUtils.handleError(req, resp, 404);// Use a generic error message for the user
        }
    }

    /**
     * Handles POST requests for user registration. Retrieves user input,
     * validates it using the {@link UserService}, and creates a new user account.
     * Redirects to the appropriate page upon successful registration or displays an error message.
     *
     * @param req  The HttpServletRequest object containing user input.
     * @param resp The HttpServletResponse object.
     * @throws ServletException If a servlet error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve user input from the request
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("rePassword");
        String role = req.getParameter("role");
        int roleId = Integer.parseInt(role);
        // Retrieve optional phone field
        String phone = req.getParameter("phone");  // phone may be empty or null

        // Retrieve additional fields: dob and gender
        String dob = req.getParameter("dob");  // date of birth (required)
        String gender = req.getParameter("gender");  // gender (required)


        // Validate user input
        String isValid = userService.validateAccount(firstName, lastName, email, password, confirmPassword, roleId, gender, phone, dob);

        if (isValid.equalsIgnoreCase("Valid")) {
            try {
                // Attempt to register the user
                int result = userService.createAccount(firstName, lastName, email, password, roleId, gender, phone, dob);
                if (result > 0) {
                    //redirect to verify email
                    resp.sendRedirect(req.getContextPath() + "/dashboard/account-manage");
                } else if (result == -2) {
                    req.setAttribute("error", "Email này đã được sử dụng, vui lòng nhập email khác.");
                    req.setAttribute("firstName", firstName);
                    req.setAttribute("lastName", lastName);
                    req.setAttribute("phone", phone);
                    req.setAttribute("dob", dob);
                    req.setAttribute("gender", gender);
                    req.setAttribute("role", role);
                    req.getRequestDispatcher("/create-account.jsp").forward(req, resp);
                } else if (result == -1) {
                    req.setAttribute("error", "Số điện thoại này đã được sử dụng, vui lòng nhập số khác.");
                    req.setAttribute("firstName", firstName);
                    req.setAttribute("lastName", lastName);
                    req.setAttribute("email", email);
                    req.setAttribute("dob", dob);
                    req.setAttribute("gender", gender);
                    req.setAttribute("role", role);
                    req.getRequestDispatcher("/create-account.jsp").forward(req, resp);
                } else {
                    req.setAttribute("error", "Có lỗi xảy ra, vui lòng đăng kí lại.");
                    req.getRequestDispatcher("/create-account.jsp").forward(req, resp);
                }
            } catch (RuntimeException | SQLException | IOException | ServletException e) {
                // Log the exception for debugging purposes
                logger.error("Error during account creation: {}", e.getMessage(), e);

                // Set a generic error message for the user
                req.setAttribute(ERROR_ATTRIBUTE, "An error occurred during registration: " + e.getMessage());
                ServletUtils.forwardToErrorPage(req, resp);
            }
        } else {
            // Set error message for invalid input
            req.setAttribute(ERROR_ATTRIBUTE, "Invalid data provided.");
            ServletUtils.forwardToErrorPage(req, resp); // Forward to a dedicated error page
        }
    }

}
