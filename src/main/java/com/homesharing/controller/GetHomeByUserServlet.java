package com.homesharing.controller;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Preference;
import com.homesharing.model.Price;
import com.homesharing.model.User;
import com.homesharing.service.HomePageService;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.PreferenceServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home-by-user")
public class GetHomeByUserServlet extends HttpServlet {
    private HomePageService homePageService;  // Service layer for home page logic
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;  // Data Access Object for accessing price data
    private UserDAO userDAO;
    private PreferenceDAO preferenceDAO;
    private PreferenceService preferenceService;
    private UserService userService;

    /**
     * Initializes the servlet by creating an instance of HomePageService.
     * The init method is called once when the servlet is first loaded into memory.
     *
     * @throws ServletException if servlet initialization fails
     */
    @Override
    public void init() throws ServletException {
        // Initialize the DAOs
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        userDAO = new UserDAOImpl();
        this.userService = new UserServiceImpl(userDAO, null, null, null);
        PreferenceDAO preferenceDAO = new PreferenceDAOImpl();
        preferenceService = new PreferenceServiceImpl(preferenceDAO);
        // Initialize the home page service with the required DAOs
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO, userDAO,null);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Assume userId is passed as a parameter
            String userIdStr = request.getParameter("id");
            int userId = Integer.parseInt(userIdStr);
            User user = null;

            user = userService.getUser(userId);

            // Use homePageService to fetch homes by user
            List<Home> userHomes = homePageService.getHomesByUser(userId);
            for (Home home : userHomes) {
                List<String> images = new ArrayList<>();
                images.add(homeDAO.fetchFirstImage(home.getId()));
                home.setImages(images);
            }
            List<Price> price = homePageService.getHomesByUserPrices(userHomes);
            Preference preference = preferenceService.getPreference(userId);

            // Set the result as a request attribute to pass to the JSP
            request.setAttribute("homes", userHomes);
            request.setAttribute("user", user);
            request.setAttribute("price", price);
            request.setAttribute("preference", preference);

            // Forward the request to the JSP page to display the homes
            request.getRequestDispatcher("home-by-user.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving user homes", e);
        }


    }
}
