/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-10      1.0                 ManhNC         First Implement
 */
package com.homesharing.controller;

import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.User;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Servlet that handles the user password update functionality.
 * It retrieves the user ID from a cookie and allows the user to update their password.
 * The process checks if the old password matches before allowing the update.
 *
 * @author ManhNC
 */
@WebServlet("/user-update-password")
public class UpdatePasswordServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdatePasswordServlet.class.getName());
    private UserService userService;
    private UserDAO userDAO;

    /**
     * Initializes the servlet by setting up the UserDAO and UserService.
     *
     * @throws ServletException if an error occurs during servlet initialization
     */
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        this.userService = new UserServiceImpl(userDAO, null, null, null);
    }

    /**
     * Handles the GET request to display the password update page.
     * It retrieves user information and checks if the user already has a password set.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve user ID from the cookie
        String userIdCookie = CookieUtil.getCookie(req, "id");
        if (userIdCookie == null) {
            LOGGER.warning("User ID cookie is missing.");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID cookie is missing.");
            return;
        }

        int userId = Integer.parseInt(userIdCookie);

        // Fetch user information and check if a password exists
        User user = null;
        try {
            int hadPass = userDAO.passWordExists(userId);
            if (hadPass == -1) {
                LOGGER.warning("User ID cookie is missing.");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID cookie is missing.");
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("hadPass", hadPass);
            user = userService.getUser(userId);
        } catch (SQLException e) {
            ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
        }

        // Get message and error from request parameters
        String message = req.getParameter("message");
        String error = req.getParameter("error");

        // Set attributes for the JSP page
        req.setAttribute("user", user);
        req.setAttribute("message", message);
        req.setAttribute("error", error);

        // Forward the request to user-profile.jsp
        req.getRequestDispatcher("/update-password.jsp").forward(req, resp);
    }

    /**
     * Handles the POST request to update the user's password.
     * The new password is validated against the old password, and if successful, the password is updated.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve user ID from the cookie
        String userIdCookie = CookieUtil.getCookie(req, "id");
        if (userIdCookie == null) {
            LOGGER.warning("User ID cookie is missing.");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID cookie is missing.");
            return;
        }

        int userId = Integer.parseInt(userIdCookie);
        String hadPassRaw = req.getSession().getAttribute("hadPass").toString();
        if (hadPassRaw == null) {
            ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
            return;
        }
        int hadPass = Integer.parseInt(hadPassRaw);
        String password = req.getParameter("password");
        String oldPassword = req.getParameter("old-password");
        try {
            int result = userService.updatePassword(userId, hadPass, oldPassword, password);
            if(result == 1) {
                req.getSession().setAttribute("message", "Cập nhật mật khẩu thành công.");
                req.getSession().setAttribute("messageType", "success");
                resp.sendRedirect(req.getContextPath() + "/user-security");
            } else if (result == -1) {
                req.setAttribute("error", "Mật khẩu cũ không khớp.");
                req.getRequestDispatcher("/update-password.jsp").forward(req, resp);
            } else {
                ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
            }
        } catch (SQLException e) {
            ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
        }
    }

}
