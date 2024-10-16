/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-10      1.0             Pham Quang Linh      Second Implement
 */

package com.homesharing.controller;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
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
import java.net.URLEncoder;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet that handles updating user matching profile and preferences.
 * Processes GET requests to retrieve user information and preferences
 * based on a user ID stored in cookies and displays the data in a JSP page.
 * Redirects to the login page if the user ID is not found.
 */
@WebServlet("/update-matching-profile")
public class UpdateMatchingProfile extends HttpServlet {

    // Service for user-related operations
    private UserService userService;
    // Service for preference-related operations
    private PreferenceService preferenceService;

    // Logger for logging events and errors
    private static final Logger LOGGER = Logger.getLogger(UserUpdateProfileServlet.class.getName());

    /**
     * Initializes services and DAOs used by the servlet.
     * This method is called when the servlet is first loaded.
     *
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        UserDAO userDAO = new UserDAOImpl(); // Initialize UserDAO
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl(); // Initialize PreferenceDAO

        // Initialize services with the corresponding DAOs
        this.userService = new UserServiceImpl(userDAO, null, null, null);
        this.preferenceService = new PreferenceServiceImpl(preferenceDAO);
    }

    /**
     * Handles HTTP GET requests to retrieve user and preference data.
     * Retrieves the user ID from a cookie, fetches user and preference
     * details, and forwards the data to the JSP page for display.
     * Redirects to the login page if the user ID is not available.
     *
     * @param req  the HttpServletRequest object containing client request data
     * @param resp the HttpServletResponse object for sending the response
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve user ID from cookies
        String userId = CookieUtil.getCookie(req, "id");
        String error = req.getParameter("error");
        String message = req.getParameter("message");

        if (userId != null) {
            // Fetch user and preference data from the services
            User user = userService.getUser(Integer.parseInt(Objects.requireNonNull(userId)));
            Preference preference = preferenceService.getPreference(Integer.parseInt(userId));

            // Set retrieved data as request attributes for JSP display
            req.setAttribute("user", user);
            req.setAttribute("preference", preference);
            req.setAttribute("error", error);
            req.setAttribute("message", message);

            // Forward to the update profile JSP page
            req.getRequestDispatcher("/update-matching-profile.jsp").forward(req, resp);
        } else {
            // Log missing user ID and redirect to login page
            LOGGER.warning("User ID cookie is missing. Redirecting to login page.");
            resp.sendRedirect("login.jsp");
        }
    }


    /**
     * Handles HTTP POST requests.
     * This method processes form data for updating the user's matching profile information
     * and preferences. If the user is a tenant (roleId 3), it updates their profile and
     * preferences. The method catches any GeneralException that occurs during this process
     * and logs it while providing a relevant error message to the user.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if an input or output error occurs during request processing
     * @throws IOException      if an I/O error occurs while handling the POST request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roleId = req.getParameter("roleId");
        String userId = req.getParameter("userId");
        String rawHowLong = req.getParameter("howLong");
        String emvdate = req.getParameter("emvdate");
        String lmvdate = req.getParameter("lmvdate");
        String rawMinBudget = req.getParameter("minBudget");
        String rawMaxBudget = req.getParameter("maxBudget");

        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String rawCleanliness = req.getParameter("cleanliness");
        String rawSmoking = req.getParameter("smoking");
        String rawDrinking = req.getParameter("drinking");
        String rawInteraction = req.getParameter("interaction");
        String rawGuest = req.getParameter("guest");
        String rawCooking = req.getParameter("cooking");
        String rawPet = req.getParameter("pet");


        boolean updateSuccessful = true;

        // Check if the user is a tenant and update their matching profile
        if ("3".equals(roleId)) {
            try {
                int rowsUpdated = userService.updateMatchingProfile(dob, gender, rawHowLong, emvdate, lmvdate, rawMinBudget, rawMaxBudget, userId);
                if (rowsUpdated < 0) {
                    LOGGER.warning("Failed to update user matching profile.");
                    updateSuccessful = false;
                }
            } catch (GeneralException e) {
                LOGGER.log(Level.SEVERE, "Error updating user matching profile.", e);
                updateSuccessful = false;
            }
        }

        // Update the user's preferences
        try {
            int rowsUpdated = preferenceService.updateUserPreference(rawCleanliness, rawSmoking, rawDrinking, rawInteraction, rawGuest, rawCooking, rawPet, userId);
            if (rowsUpdated <= 0) {
                LOGGER.warning("Failed to update user preferences.");
                updateSuccessful = false;
            }
        } catch (GeneralException e) {
            LOGGER.log(Level.SEVERE, "Error updating user preferences.", e);
            updateSuccessful = false;
        }

        // Send redirect response only once
        if (updateSuccessful) {
            resp.sendRedirect("update-matching-profile?message="+ URLEncoder.encode("Cài đặt thành công.", "UTF-8"));
        } else {
            resp.sendRedirect("update-matching-profile?error="+URLEncoder.encode("Không thể cài đặt thông tin.", "UTF-8"));
        }
    }
}