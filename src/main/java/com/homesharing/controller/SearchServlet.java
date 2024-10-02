package com.homesharing.controller;


import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.model.Home;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// URL pattern mapped to this servlet
@WebServlet("/searchHomes")
public class SearchServlet extends HttpServlet {

    private SearchSevice searchService;
    private HomePageService HomePageService;
    private HomeDAO homeDAO;
    private PriceDAO priceDAO;

    @Override
    public void init() throws ServletException {
        searchService = new SearchServiceImpl();
        HomePageService = new HomePageServiceImpl(homeDAO, priceDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve search parameters from the request
        String name = req.getParameter("name");
        String minPriceStr = req.getParameter("minPrice");
        String maxPriceStr = req.getParameter("maxPrice");

        List<Home> homes = new ArrayList<>(); // Initialize an empty list to store the search results
        int minPrice = 0; // Default minimum price
        int maxPrice; // Variable to hold the maximum price

        try {
            // Parse the minimum price from the request parameter if provided
            if (minPriceStr != null && !minPriceStr.trim().isEmpty()) {
                minPrice = Integer.parseInt(minPriceStr);
            }

            // Parse the maximum price from the request parameter if provided
            if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
                maxPrice = Integer.parseInt(maxPriceStr);
            } else {
                // If max price is not provided, retrieve the maximum price from the service
                maxPrice = searchService.getMaxPrice();
            }

            // Perform search based on the provided name or price range
            if (name != null && !name.trim().isEmpty()) {
                homes = searchService.searchHomesByName(name); // Search by name
            } else {
                homes = searchService.searchByPriceRange(minPrice, maxPrice); // Search by price range
            }
        } catch (NumberFormatException e) {
            // Handle case where price parameters cannot be parsed
            req.setAttribute("error", "error"); // Set error attribute for the request
            homes = HomePageService.getHomes(); // Retrieve all homes as a fallback
        } catch (SQLException | ClassNotFoundException e) {
            // Handle SQL or class not found exceptions
            throw new RuntimeException("error: " + e.getMessage(), e);
        }

        // Set the search results and parameters as attributes for the request
        req.setAttribute("homes", homes);
        req.setAttribute("searchName", name);
        req.setAttribute("minPrice", minPriceStr);
        req.setAttribute("maxPrice", maxPriceStr);

        // Forward the request to the home.jsp page to display results
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}

