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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private transient UserService userService;// Mark userService as transient
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class); // Logger instance

    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService);
    }

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
            } else {
                // Login failed, display error message
                req.setAttribute("error", result);
                req.setAttribute("email", email);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("Error processing login request: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your request.");
        }
    }
}
