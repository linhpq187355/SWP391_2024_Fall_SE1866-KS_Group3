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
import com.homesharing.model.GoogleAccount;
import com.homesharing.model.User;
import com.homesharing.service.NotificationService;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.NotificationServiceImpl;
import com.homesharing.service.impl.PreferenceServiceImpl;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.AddNotificationUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The {@code SetRoleServlet} class handles requests for setting user roles.
 * It provides functionality for directing users to the role selection page
 * and processes the submitted role information.
 *
 * @author ManhNC
 */
@WebServlet("/set-role")
public class SetRoleServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SetRoleServlet.class); // Logger instance
    private UserDAO userDao;
    private transient UserService userService;// Mark userService as transient
    private static final String ERROR_ATTRIBUTE = "error"; // Define constant for error attribute

    /**
     * Initializes the SignUpServlet by creating instances of required services.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        PreferenceDAO preferenceDao = new PreferenceDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        PreferenceService preferenceService = new PreferenceServiceImpl(preferenceDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,preferenceService);
    }

    /**
     * Handles GET requests to display the role selection page.
     *
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     * @throws IOException if an I/O error occurs during the request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Redirect to set-role page
            request.getRequestDispatcher("/set-role.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("Error forwarding to set-role page: {}", e.getMessage(), e);
            ServletUtils.handleError(request, response, 404);
        }
    }

    /**
     * Handles POST requests for setting the user role.
     *
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     * @throws IOException if an I/O error occurs during the request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String role = request.getParameter("role");
        if (role == null) {
            // If role is not selected, redirect to the set-role page with an error message
            request.setAttribute("message", "Vui lòng chọn vai trò.");
            request.setAttribute("messageType", "error");
            forwardToSetRolePage(request, response);
            return;
        }
        HttpSession session = request.getSession();
        GoogleAccount acc = (GoogleAccount) session.getAttribute("googleAccount");
        session.removeAttribute("googleAccount");
        if (acc != null) {
            try {
                int result = userService.registerByGoogle(acc, role, response);
                handleRegistrationResult(result, request, response, acc);
            } catch (SQLException e) {
                logger.error("SQL error during registration: {}", e.getMessage(), e);
                request.setAttribute(ERROR_ATTRIBUTE, "Có lỗi xảy ra ở phía server.");
                ServletUtils.handleError(request, response, 500);
            }
        }else{
            // If the Google account is not present, prompt for re-login
            request.setAttribute(ERROR_ATTRIBUTE, "Đã hết thời gian, vui lòng đăng nhập lại.");
            ServletUtils.handleError(request, response, 500);
        }
    }

    /**
     * Forwards the request to the set-role page with an error message.
     *
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     * @throws IOException if an I/O error occurs during the request.
     */
    private void forwardToSetRolePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.getRequestDispatcher("/set-role.jsp").forward(request, response);
        } catch (ServletException e) {
            logger.error("Error forwarding to set-role page: {}", e.getMessage(), e);
            ServletUtils.handleError(request, response, 404);
        }
    }

    /**
     * Handles the result of the user registration process.
     *
     * @param result   The result of the registration attempt.
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     * @param acc     The Google account associated with the registration.
     * @throws IOException if an I/O error occurs during the request.
     */
    private void handleRegistrationResult(int result, HttpServletRequest request, HttpServletResponse response, GoogleAccount acc) throws IOException, SQLException {
        if (result == 2) {
            User user = userDao.findUserByEmail(acc.getEmail());
            String url = Config.getBaseUrl();
            AddNotificationUtil.getInstance().addNotification(user.getId(),"Chào mừng bạn đến với Rommify, chúc bạn có những trải nghiệm tuyệt vời ở đây.", "Chào mừng","System",url);
            AddNotificationUtil.getInstance().addNotification(user.getId(),"Bạn chưa có mật khẩu, vui lòng click vào đường dẫn này để cài đặt mật khẩu.", "Thiết lập bảo mật cho tài khoản.","System",url + "/user-update-password");
            request.getSession().setAttribute("message", "Đăng ký thành công.");
            request.getSession().setAttribute("messageType", "success");
            response.sendRedirect(request.getContextPath() + "/matching");
        } else if (result == 1) {
            // Login successful, redirect to home page
            request.getSession().setAttribute("message", "Đăng nhập thành công.");
            request.getSession().setAttribute("messageType", "success");
            response.sendRedirect(request.getContextPath() + "/home-page");
        } else if (result == -1) {
            // Prompt user to set role again
            request.getSession().setAttribute("googleAccount", acc);
            response.sendRedirect(request.getContextPath() + "/set-role");
        } else {
            // Set error message if registration fails
            request.setAttribute(ERROR_ATTRIBUTE, "Có lỗi xảy ra ở phía server.");
            ServletUtils.forwardToErrorPage(request, response);
        }
    }
}
