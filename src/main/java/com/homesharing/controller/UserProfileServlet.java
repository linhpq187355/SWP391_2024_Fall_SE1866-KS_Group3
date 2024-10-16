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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet("/user-profile")
public class UserProfileServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UserProfileServlet.class.getName());
    private UserService userService;
    private PreferenceService preferenceService;

    @Override
    public void init() throws ServletException {
        UserDAO userDAO = new UserDAOImpl();
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl();
        this.userService = new UserServiceImpl(userDAO, null, null, null);
        this.preferenceService = new PreferenceServiceImpl(preferenceDAO);
    }

    /**
     * Handles GET request to display the user update profile page.
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

        // Fetch user information and preferences using the services
        User user = null;

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
        req.getRequestDispatcher("/user-profile.jsp").forward(req, resp);
    }
}