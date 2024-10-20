/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-15      1.0              Pham Quang Linh     Second Implement
 */

package com.homesharing.controller;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.ProvinceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.ProvinceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import com.homesharing.model.Province;
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
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet that handles user matching profile and preference updates.
 * Processes GET requests to display the form and POST requests to update
 * user information and preferences using UserService and PreferenceService.
 * Forwards to "matching-profile.jsp" with success or error messages.
 */

@WebServlet("/matching")
public class MatchingServlet extends HttpServlet {

    private UserService userService;
    private PreferenceService preferenceService;
    private ProvinceDAO provinceDAO;

    private static final Logger LOGGER = Logger.getLogger(UserUpdateProfileServlet.class.getName());


    /**
     * Initializes the servlet by creating instances of the necessary services and DAOs.
     * The init method is called once when the servlet is first loaded into memory.
     *
     * @throws ServletException if an error occurs during servlet initialization
     */
    @Override
    public void init() throws ServletException {
        UserDAO userDAO = new UserDAOImpl(); // Initialize UserDAO
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl(); // Initialize PreferenceDAO

        // Initialize services with corresponding DAOs
        this.userService = new UserServiceImpl(userDAO, null, null, null);
        this.preferenceService = new PreferenceServiceImpl(preferenceDAO);
        this.provinceDAO = new ProvinceDAOImpl();
    }

    /**
     * Handles HTTP GET requests.
     * This method retrieves any error or message parameters from the request and sets them
     * as attributes, then forwards the request to the "matching-profile.jsp" page.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if an input or output error occurs during request processing
     * @throws IOException      if an I/O error occurs while handling the GET request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = CookieUtil.getCookie(req, "id");
        String error = req.getParameter("error");
        String message = req.getParameter("message");
        List<Province> provinceList = provinceDAO.getAllProvinces();

        req.setAttribute("error", error);
        req.setAttribute("message", message);
        req.setAttribute("provinceList", provinceList);
        req.getRequestDispatcher("/matching-profile.jsp").forward(req, resp);
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
        String prefProv = req.getParameter("prefProvince");

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
                int rowsUpdated = userService.updateMatchingProfile(dob, gender, rawHowLong, emvdate, lmvdate, rawMinBudget, rawMaxBudget,prefProv, userId);
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
            resp.sendRedirect("matching?message="+ URLEncoder.encode("Cài đặt thành công.", "UTF-8"));
        } else {
            resp.sendRedirect("matching?error="+URLEncoder.encode("Không thể cài đặt thông tin.", "UTF-8"));
        }
    }
}
