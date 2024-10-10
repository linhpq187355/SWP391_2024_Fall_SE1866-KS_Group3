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
     * Handles GET requests to display the create-account page.
     * This method forwards the request to the sign-up JSP page.
     *
     * @param req  HttpServletRequest containing the client request.
     * @param resp HttpServletResponse used to send a response to the client.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Redirect to sign-up page
            req.getRequestDispatcher("/create-account.jsp").forward(req, resp);
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
        boolean isValid = userService.validateAccount(firstName, lastName, email, password, confirmPassword, roleId, gender, phone, dob);

        if (isValid) {
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
