package com.homesharing.controller;

import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.model.Home;
import com.homesharing.model.Province;
import com.homesharing.model.User;
import com.homesharing.service.HomePageService;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.*;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HomeListServlet.class.getName());

    private HomePageService homePageService;  // Service layer for home page logic
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;  // Data Access Object for accessing price data
    private UserDAO userDAO;
    private UserService userService;
    private PreferenceDAO preferenceDAO;
    private PreferenceService preferenceService;
    private TokenDAO tokenDAO;
    private TokenService tokenService;
    private SubmissonFormServiceImpl submissonFormService;

    @Override
    public void init() throws ServletException {
        // Initialize the DAOs
        tokenDAO = new TokenDAOImpl();
        tokenService = new TokenServiceImpl(tokenDAO);
        preferenceDAO = new PreferenceDAOImpl();
        preferenceService = new PreferenceServiceImpl(preferenceDAO);
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        userDAO = new UserDAOImpl();
        // Initialize the home page service with the required DAOs
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO, userDAO,null);
        submissonFormService = new SubmissonFormServiceImpl();

        // Initialize UserService with required DAOs and Services
        userService = new UserServiceImpl(userDAO, tokenDAO, tokenService,preferenceService);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> searchParams0 = new HashMap<>();
        String province0 = req.getParameter("province");
        String district0 = req.getParameter("district");
        String ward0 = req.getParameter("ward");
        String cleanliness0 = req.getParameter("cleanliness");
        String gender0 = req.getParameter("gender");
        String smoking0 = req.getParameter("smoking");
        String drinking0 = req.getParameter("drinking");
        String interaction0 = req.getParameter("interaction");
        String guests0 = req.getParameter("guests");
        String cooking0 = req.getParameter("cooking");
        String pet0 = req.getParameter("pet");
        String currentPage0 = req.getParameter("currentPage");
        String targetPage0 = req.getParameter("targetPage");
        String perPage = req.getParameter("per_page");

        int totalUser = 0;
        if (targetPage0 != null && !targetPage0.isEmpty()) {
            searchParams0.put("targetPage", targetPage0);
        }
        if (perPage != null && !perPage.isEmpty()) {
            searchParams0.put("per_page", perPage);
        }
        if (currentPage0 != null && !currentPage0.isEmpty()) {
            searchParams0.put("currentPage", currentPage0);
        }
        if (province0 != null && !province0.isEmpty()) {
            searchParams0.put("province", province0);
        }
        if (district0 != null && !district0.isEmpty()) {
            searchParams0.put("district", district0);
        }
        if (ward0 != null && !ward0.isEmpty()) {
            searchParams0.put("ward", ward0);
        }

        String cleanlinessRange0 = req.getParameter("cleanlinessRange");
        if (cleanlinessRange0 != null && !cleanlinessRange0.isEmpty()) {
            searchParams0.put("cleanlinessRange", cleanlinessRange0);
        }
        userService.addRangeToMap(searchParams0, cleanlinessRange0, "minCleanliness", "maxCleanliness");

        String smokingRange0 = req.getParameter("smokingRange");
        if (smokingRange0 != null && !smokingRange0.isEmpty()) {
            searchParams0.put("smokingRange", smokingRange0);
        }
        userService.addRangeToMap(searchParams0, smokingRange0, "minSmoking", "maxSmoking");

        String drinkingRange0 = req.getParameter("drinkingRange");
        if (drinkingRange0 != null && !drinkingRange0.isEmpty()) {
            searchParams0.put("drinkingRange", drinkingRange0);
        }
        userService.addRangeToMap(searchParams0, drinkingRange0, "minDrinking", "maxDrinking");

        String interactionRange0 = req.getParameter("interactionRange");
        if (interactionRange0 != null && !interactionRange0.isEmpty()) {
            searchParams0.put("interactionRange", interactionRange0);
        }
        userService.addRangeToMap(searchParams0, interactionRange0, "minInteraction", "maxInteraction");

        String guestsRange0 = req.getParameter("guestRange");
        if (guestsRange0 != null && !guestsRange0.isEmpty()) {
            searchParams0.put("guestRange", guestsRange0);
        }
        userService.addRangeToMap(searchParams0, guestsRange0, "minGuests", "maxGuests");

        String cookingRange0 = req.getParameter("cookingRange");
        if (cookingRange0 != null && !cookingRange0.isEmpty()) {
            searchParams0.put("cookingRange", cookingRange0);
        }
        userService.addRangeToMap(searchParams0, cookingRange0, "minCooking", "maxCooking");

        String petRange0 = req.getParameter("petRange");
        if (petRange0 != null && !petRange0.isEmpty()) {
            searchParams0.put("petRange", petRange0);
        }
        userService.addRangeToMap(searchParams0, petRange0, "minPet", "maxPet");



        if (gender0 != null && !gender0.isEmpty()) {
            searchParams0.put("gender", gender0);
        }
        try {
            totalUser = userService.CountSearchUser(searchParams0);
            userService.updateTargetPage(searchParams0, totalUser);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error while fetching new homes: " + e.getMessage());
            ServletUtils.forwardWithMessage(req, resp, e.getMessage());
        }
        List<User> User0 = userService.searchUserByPreference(searchParams0);

        List<Province> provinces0 = new ArrayList<>(submissonFormService.getProvinces());

        req.setAttribute("minCleanliness", 1);
        req.setAttribute("maxCleanliness", 5);

        req.setAttribute("minSmoking", 1);
        req.setAttribute("maxSmoking", 5);

        req.setAttribute("minDrinking", 1);
        req.setAttribute("maxDrinking", 5);

        req.setAttribute("minInteraction", 1);
        req.setAttribute("maxInteraction", 5);

        req.setAttribute("minGuests", 1);
        req.setAttribute("maxGuests", 5);

        req.setAttribute("minCooking", 1);
        req.setAttribute("maxCooking", 5);

        req.setAttribute("provinces", provinces0);
        req.setAttribute("searchParams", searchParams0);
        req.setAttribute("users", User0);
        req.setAttribute("minPet", 1);
        req.setAttribute("maxPet", 5);
        req.setAttribute("gender", gender0);
        req.setAttribute("cleanliness", cleanliness0);
        req.setAttribute("smoking", smoking0);
        req.setAttribute("drinking", drinking0);
        req.setAttribute("interaction", interaction0);
        req.setAttribute("guests", guests0);
        req.setAttribute("cooking", cooking0);
        req.setAttribute("pet", pet0);

        req.getRequestDispatcher("/user-list.jsp").forward(req, resp);

    }

}
