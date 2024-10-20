package com.homesharing.controller;


import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.HomePageService;
import com.homesharing.service.SearchSevice;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.SearchServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.List;

// URL pattern mapped to this servlet
@WebServlet("/searchHomes")
public class SearchServlet extends HttpServlet {

    private SearchSevice searchService;
    private HomePageService homePageService;
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;  // Data Access Object for accessing price data
    private UserDAO userDAO;

    /**
     * Initializes the SearchServlet by creating instances of necessary DAOs and services.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() throws ServletException {
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        userDAO = new UserDAOImpl();
        try {
            searchService = new SearchServiceImpl();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO, userDAO,null);
    }

    /**
     * Handles GET requests to search for homes based on provided parameters.
     * Retrieves search parameters, performs search, and forwards results to the JSP.
     *
     * @param req The HttpServletRequest object containing the request data.
     * @param resp The HttpServletResponse object for sending responses to the client.
     * @throws ServletException if the request cannot be handled.
     * @throws IOException if an input or output error occurs during the handling of the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve search parameters from the request
        String name = req.getParameter("name") != null ? req.getParameter("name").trim().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z0-9\\s]", "") : null;
        String minPriceStr = req.getParameter("minPrice") != null ? req.getParameter("minPrice").trim() : null;
        String maxPriceStr = req.getParameter("maxPrice") != null ? req.getParameter("maxPrice").trim() : null;

        List<Home> homes = homePageService.getNewHomes();
        List<Price> prices = homePageService.getHomePrice(homes);
        int minPrice = 0; // Default minimum price
        int maxPrice = 1000000; // Variable to hold the maximum price
            try {
            if (minPriceStr != null && !minPriceStr.trim().isEmpty()) {
                minPrice = Integer.parseInt(minPriceStr);
            }
            if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
                maxPrice = Integer.parseInt(maxPriceStr);
            }
            if (name != null && !name.trim().isEmpty()) {
                homes = searchService.searchHomesByAdress(name); // Search by name
            } else {
                homes = searchService.searchByPriceRange(minPrice, maxPrice); // Search by price range
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "error"); // Set error attribute for the request
            homes = homePageService.getNewHomes(); // Retrieve all homes as a fallback
            throw new RuntimeException("error: " + e.getMessage(), e);
        }
        // Set the search results and parameters as attributes for the request
        req.setAttribute("homes", homes);
        req.setAttribute("searchName", name);
        req.setAttribute("minPrice", minPriceStr);
        req.setAttribute("maxPrice", maxPriceStr);
        req.setAttribute("prices", prices);
        //Forward the request to the home.jsp page to display results
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}

