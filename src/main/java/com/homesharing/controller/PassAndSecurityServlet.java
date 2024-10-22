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
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Preference;
import com.homesharing.model.User;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.PreferenceServiceImpl;
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
 * The {@code PassAndSecurityServlet} handles requests related to user security settings,
 * such as password changes and other security-related preferences. It retrieves user
 * information and preferences and displays them on the `user-security.jsp` page.
 * <p>
 * Bugs: None known.
 *
 * @author ManhNC
 */
@WebServlet("/user-security")
public class PassAndSecurityServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PassAndSecurityServlet.class.getName());

    private UserService userService;
    private UserDAO userDAO;
    private PreferenceService preferenceService;

    /**
     * Initializes the servlet by instantiating the required DAOs and services.
     */
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl();
        this.userService = new UserServiceImpl(userDAO, null, null, null);
        this.preferenceService = new PreferenceServiceImpl(preferenceDAO);
    }
    /**
     * Handles GET requests to display the user security settings page.  Retrieves user information
     * and preferences from the database based on the user ID stored in a cookie, and sets them as
     * request attributes for rendering on the JSP page.  Handles errors if the user ID is missing
     * or invalid, or if there's a database error.
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
        try {
            int userId = Integer.parseInt(userIdCookie);

            // Fetch user information and preferences using the services
            User user = null;

            int hadPass = userDAO.passWordExists(userId);
            if (hadPass == -1) {
                LOGGER.warning("User ID cookie is missing.");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID cookie is missing.");
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("hadPass", hadPass);
            user = userService.getUser(userId);

            Preference preference = preferenceService.getPreference(userId);

            // Get message and error from request parameters
            String message = req.getParameter("message");
            String error = req.getParameter("error");

            // Set attributes for the JSP page
            req.setAttribute("user", user);
            req.setAttribute("preference", preference);
            req.setAttribute("message", message);
            req.setAttribute("error", error);

            // Forward the request to user-profile.jsp
            req.getRequestDispatcher("/user-security.jsp").forward(req, resp);
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving user security information: {}" + e.getMessage() + e); // Log the actual exception
            ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
        }
    }
}
