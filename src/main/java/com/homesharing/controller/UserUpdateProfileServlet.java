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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet for handling user profile update requests.
 * Handles both GET and POST requests for retrieving and updating user profile and preferences.
 */
@MultipartConfig
@WebServlet("/user-update-profile")
public class UserUpdateProfileServlet extends HttpServlet {

    // Declare service layer interfaces
    private UserService userService;
    private PreferenceService preferenceService;

    private static final Logger LOGGER = Logger.getLogger(UserUpdateProfileServlet.class.getName());

    /**
     * Initializes services and DAOs used by the servlet.
     */
    @Override
    public void init() throws ServletException {
        UserDAO userDAO = new UserDAOImpl(); // Initialize UserDAO
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl(); // Initialize PreferenceDAO

        // Initialize services with corresponding DAOs
        this.userService = new UserServiceImpl(userDAO, null, null, null);
        this.preferenceService = new PreferenceServiceImpl(preferenceDAO);
    }

    /**
     * Handles GET request to display the user update profile page.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve user information based on the user ID stored in cookies
        String userId = CookieUtil.getCookie(req, "id");

        if (userId != null) {
            // Fetch user and preference data from the services
            User user = null;

                user = userService.getUser(Integer.parseInt(Objects.requireNonNull(userId)));

            Preference preference = preferenceService.getPreference(Integer.parseInt(userId));

            // Set the retrieved data as request attributes for displaying on the JSP page
            req.setAttribute("user", user);
            req.setAttribute("preference", preference);

            // Forward the request to the user update profile JSP page
            req.getRequestDispatcher("/user-update-profile.jsp").forward(req, resp);
        } else {
            // Redirect to login page if user ID is not found
            LOGGER.warning("User ID cookie is missing. Redirecting to login page.");
            resp.sendRedirect("login.jsp");
        }
    }

    /**
     * Handles POST request to update user profile and preferences.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve user profile information from request parameters
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String address = req.getParameter("address");
        String gender = req.getParameter("gender");
        String dob = req.getParameter("dob"); // Optional field for date of birth

        // Retrieve user preferences from request parameters
        String r_cleanliness = req.getParameter("cleanliness");
        String cleanlinessStatus = req.getParameter("cleanlinessStatus");
        String r_smoking = req.getParameter("smoking");
        String smokingStatus = req.getParameter("smokingStatus");
        String r_drinking = req.getParameter("drinking");
        String drinkingStatus = req.getParameter("drinkingStatus");
        String r_interaction = req.getParameter("interaction");
        String interactionStatus = req.getParameter("interactionStatus");
        String r_cooking = req.getParameter("cooking");
        String cookingStatus = req.getParameter("cookingStatus");
        String r_pet = req.getParameter("pet");
        String petStatus = req.getParameter("petStatus");

        // Handle avatar upload
        Part avatarPart = req.getPart("avatar");
        String avatarFileName = null;

        if (avatarPart != null && avatarPart.getSize() > 0) {
            // Extract the avatar file name and save the file to the specified upload directory
            avatarFileName = Path.of(avatarPart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = getServletContext().getRealPath("/assets/img/user-avatar");
            LOGGER.info(uploadDir);
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            avatarPart.write(uploadDir + File.separator + avatarFileName);

            // Update the file path to be saved in the database
            avatarFileName = "user-avatar/" + avatarFileName;
        }

        // Retrieve the user ID from the cookies
        String userId = CookieUtil.getCookie(req, "id");

        if (userId != null) {
            try {
                // Call the service to update the user profile
                int rowsUpdated = userService.updateUserProfile(userId, firstName, lastName, address, gender, dob, avatarFileName);

                if (rowsUpdated > 0) {
                    // If the user profile update is successful, update the user preferences
                    try {
                        int rowsUpdatedPref = preferenceService.updateUserPreference(
                                r_cleanliness, cleanlinessStatus, r_smoking, smokingStatus,
                                r_drinking, drinkingStatus, r_interaction, interactionStatus,
                                r_cooking, cookingStatus, r_pet, petStatus, userId
                        );

                        if (rowsUpdatedPref > 0) {
                            // If preferences are updated successfully, redirect to the profile page
                            resp.sendRedirect("user-profile?message=Profile updated successfully!");
                        } else {
                            // If preferences update fails, display an error message
                            LOGGER.warning("Failed to update user preferences.");
                            req.getRequestDispatcher("user-profile?error=Unable to update preferences.").forward(req, resp);
                        }
                    } catch (GeneralException e) {
                        LOGGER.log(Level.SEVERE, "Error updating user preferences.", e);
                        req.getRequestDispatcher("user-profile?error=Unable to update preferences.").forward(req, resp);
                    }

                } else {
                    // If no rows are updated in the user profile, display an appropriate message
                    LOGGER.warning("No rows updated for user profile.");
                    req.getRequestDispatcher("user-profile?error=Unable to update profile.").forward(req, resp);
                }
            } catch (NumberFormatException e) {
                // Handle invalid user ID errors
                LOGGER.log(Level.SEVERE, "Invalid user ID: " + userId, e);
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
            }
        } else {
            // Redirect to the login page if the user is not authenticated
            LOGGER.warning("User ID cookie is missing. Redirecting to login page.");
            resp.sendRedirect("login.jsp");
        }
    }
}