package com.homesharing.controller;


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
import java.sql.SQLException;
import java.util.List;

// URL pattern mapped to this servlet
@WebServlet("/searchHomes")
public class SearchServlet extends HttpServlet {

    private SearchSevice searchService;
    private HomePageService homePageService;

    @Override
    public void init() throws ServletException {
        searchService = new SearchServiceImpl();
        homePageService = new HomePageServiceImpl();
    }

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
            // Parse the minimum price from the request parameter if provided
            if (minPriceStr != null && !minPriceStr.trim().isEmpty()) {
                minPrice = Integer.parseInt(minPriceStr);
            }

            // Parse the maximum price from the request parameter if provided
            if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
                maxPrice = Integer.parseInt(maxPriceStr);
            }
            // Perform search based on the provided name or price range
            if (name != null && !name.trim().isEmpty()) {
                homes = searchService.searchHomesByAdress(name); // Search by name
            } else {
                homes = searchService.searchByPriceRange(minPrice, maxPrice); // Search by price range
            }
        } catch (NumberFormatException e) {
            // Handle case wh   ere price parameters cannot be parsed
            req.setAttribute("error", "error"); // Set error attribute for the request
            homes = homePageService.getNewHomes(); // Retrieve all homes as a fallback
            // Handle SQL or class not found exceptions
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

