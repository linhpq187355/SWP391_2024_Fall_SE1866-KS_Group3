package com.homesharing.controller;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet to handle password reset requests.
 * This servlet processes GET and POST requests for resetting user passwords.
 */
@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {

    private transient TokenService tokenService; // Service for managing token-related operations
    private transient UserService userService; // Service for managing user-related operations
    private static final Logger LOGGER = Logger.getLogger(ResetPasswordServlet.class.getName()); // Logger for logging messages

    /**
     * Initializes the servlet by setting up the necessary DAO implementations and services.
     * This method is called once when the servlet is first created.
     */
    @Override
    public void init() {
        TokenDAO tokenDao = new TokenDAOImpl(); // Initialize TokenDAO
        UserDAO userDao = new UserDAOImpl(); // Initialize UserDAO
        tokenService = new TokenServiceImpl(tokenDao); // Initialize TokenService with TokenDAO
        userService = new UserServiceImpl(userDao, tokenDao, tokenService, null); // Initialize UserService with UserDAO, TokenDAO and TokenService
    }

    /**
     * Handles GET requests for password reset.
     * It verifies the provided verification code and user ID,
     * and forwards to the reset password page if valid.
     *
     * @param req  the HttpServletRequest object that contains the request
     * @param resp the HttpServletResponse object that contains the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String verificationCode = req.getParameter("code"); // Retrieve verification code from request
        String userIDString = req.getParameter("userId"); // Retrieve user ID from request

        // Validate verification code and user ID
        if (verificationCode == null || userIDString == null) {
            req.setAttribute("error", "Mã xác thực hoặc ID người dùng không hợp lệ."); // Set error message for invalid parameters
            req.getRequestDispatcher("forgot-password.jsp").forward(req, resp); // Forward to forgot password page
            return; // Exit the method
        }

        try {
            // Check if the token is valid
            boolean isValidToken = tokenService.checkToken(verificationCode, Integer.parseInt(userIDString));
            if (isValidToken) {
                req.setAttribute("userId", userIDString); // Set user ID for the reset password page
                req.getRequestDispatcher("reset-password.jsp").forward(req, resp); // Forward to reset password page
            } else {
                req.setAttribute("error", "Xác thực không thành công."); // Set error message for unsuccessful verification
                req.getRequestDispatcher("forgot-password.jsp").forward(req, resp); // Forward to forgot password page
            }
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Error during token verification", e); // Log the error
            req.setAttribute("error", "Đã xảy ra lỗi trong quá trình xác thực. Vui lòng thử lại."); // Set error message for exception
            req.getRequestDispatcher("forgot-password.jsp").forward(req, resp); // Forward to forgot password page
        }
    }

    /**
     * Handles POST requests for password reset.
     * It processes the new password and updates the user's password if valid.
     *
     * @param req  the HttpServletRequest object that contains the request
     * @param resp the HttpServletResponse object that contains the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdString = req.getParameter("id"); // Retrieve user ID from request
        String newPassword = req.getParameter("pass"); // Retrieve new password from request
        String rePassword = req.getParameter("re_pass"); // Retrieve re-entered password from request

        // Validate user ID and passwords
        if (userIdString == null || newPassword == null || rePassword == null) {
            req.setAttribute("error", "Dữ liệu không hợp lệ."); // Set error message for invalid data
            req.getRequestDispatcher("reset-password.jsp").forward(req, resp); // Forward to reset password page
            return; // Exit the method
        }

        int userId = Integer.parseInt(userIdString);

            try {
                // Attempt to reset the user's password
                int result = userService.resetUserPassword(userId, newPassword);
                if (result > 0) {
                    req.setAttribute("message", "Mật khẩu đã được đặt lại thành công."); // Set success message
                    req.getRequestDispatcher("login.jsp").forward(req, resp); // Forward to login page
                } else {
                    req.setAttribute("userId", userIdString);
                    req.setAttribute("error", "Có lỗi xảy ra khi đặt lại mật khẩu."); // Set error message for reset failure
                    req.getRequestDispatcher("reset-password.jsp").forward(req, resp); // Forward to reset password page
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error resetting user password", e); // Log the error
                req.setAttribute("error", "Đã xảy ra lỗi khi đặt lại mật khẩu."); // Set error message for exception
                req.setAttribute("userId", userIdString);
                req.getRequestDispatcher("reset-password.jsp").forward(req, resp); // Forward to reset password page
            }
    }
}