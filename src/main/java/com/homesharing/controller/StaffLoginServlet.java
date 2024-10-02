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
 * StaffLoginServlet handles staff login requests and processes
 * login form submissions. It validates credentials and communicates
 * with the UserService to authenticate staff users.
 *
 * @version 1.0
 * @since 2024-10-02
 */
@WebServlet("/staff-login")
public class StaffLoginServlet extends HttpServlet {
    private transient UserService userService;// Mark userService as transient
    private static final Logger logger = LoggerFactory.getLogger(StaffLoginServlet.class); // Logger instance

    /**
     * Initializes the StaffLoginServlet by creating instances of required services.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService);
    }

    /**
     * Handles GET requests to display the staff login page.
     * This method forwards the request to the login JSP page.
     *
     * @param req  HttpServletRequest containing the client request.
     * @param resp HttpServletResponse used to send a response to the client.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Redirect to sign-up page
            req.getRequestDispatcher("/staff-login.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to login page: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your request.");
        }
    }

    /**
     * Handles POST requests for staff login authentication.
     * This method validates the login credentials and processes the login request.
     *
     * @param req  HttpServletRequest containing the client input.
     * @param resp HttpServletResponse used to send a response to the client.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Get information from login form
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            // Log the email for debugging (make sure to not log sensitive information)
            logger.debug("Login attempt for email: {}", email);
            // Pass information to service
            String result = userService.loginStaff(email, password, resp);

            if (result.equals("success")) {
                // Login successful, redirect to home page
                req.getSession().setAttribute("message", "Đăng nhập thành công.");
                req.getSession().setAttribute("messageType", "success");
                resp.sendRedirect(req.getContextPath() + "/account-manage");
            } else {
                // Login failed, display error message
                req.setAttribute("error", result);
                if(result.equals("Email hoặc mật khẩu không đúng")) {
                    req.setAttribute("email", email);
                }
                req.getRequestDispatcher("/staff-login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("Error processing login request: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your request.");
        }
    }
}
