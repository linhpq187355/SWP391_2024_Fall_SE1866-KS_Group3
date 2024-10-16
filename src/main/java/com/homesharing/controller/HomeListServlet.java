/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-02      1.0                 ManhNC         First Implement
 * 2024-10-27      1.1                 AI Assistant     Improved code and comments
 */
package com.homesharing.controller;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.model.*;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.SubmissonFormServiceImpl;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.homesharing.service.HomePageService;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

/**
 * The {@code HomeListServlet} handles requests to display a list of homes
 * based on various search criteria. It retrieves home data and related information
 * from the service layer and sets them as request attributes for rendering
 * on the `home-list.jsp` page.
 * <p>
 * Bugs: None known.
 *
 * @author ManhNC
 */
@WebServlet("/home-list")
public class HomeListServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(HomeListServlet.class.getName());

    private SubmissonFormServiceImpl submissonFormService;
    private HomePageService homePageService;  // Service layer for home page logic
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;  // Data Access Object for accessing price data

    /**
     * Initializes the servlet by instantiating the required DAOs and services.
     */
    @Override
    public void init() throws ServletException {
        // Initialize the DAOs
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        submissonFormService = new SubmissonFormServiceImpl();
        // Initialize the home page service with the required DAOs
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO);
    }

    /**
     * Handles HTTP GET requests.
     * This method retrieves new homes and their prices, sets them as attributes, and forwards
     * the request to the home page JSP for rendering.
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if an input or output error is detected when the servlet handles the GET request
     * @throws IOException      if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> searchParams0 = new HashMap<>();
        // Process request parameters
        String orderBy = req.getParameter("orderby");
        String order = req.getParameter("order");
        String perPage = req.getParameter("per_page");
        String keyword0 = req.getParameter("keyword");
        String homeType0 = req.getParameter("homeType");
        String province0 = req.getParameter("province");
        String district0 = req.getParameter("district");
        String ward0 = req.getParameter("ward");
        String priceRange0 = req.getParameter("priceRange");
        String currentPage0 = req.getParameter("currentPage");
        String targetPage0 = req.getParameter("targetPage");

        // Process price range
        if (priceRange0 != null && !priceRange0.isEmpty()) {
            String[] prices = priceRange0.replaceAll("[\\[\\]]", "").split(",");
            int sliderMinPrice = Integer.parseInt(prices[0].trim()) * 1000; // Nhân với 1000
            int sliderMaxPrice = Integer.parseInt(prices[1].trim()) * 1000; // Nhân với 1000
            searchParams0.put("minPrice", sliderMinPrice);
            searchParams0.put("maxPrice", sliderMaxPrice);
            searchParams0.put("priceRange", priceRange0);
        }

        // Process area range
        String areaRange0 = req.getParameter("areaRange");
        if (areaRange0 != null && !areaRange0.isEmpty()) {
            searchParams0.put("areaRange", areaRange0);
        }
        homePageService.addRangeToMap(searchParams0, areaRange0, "minArea", "maxArea");

        // Process bath range
        String bathRange0 = req.getParameter("bathRange");
        if (bathRange0 != null && !bathRange0.isEmpty()) {
            searchParams0.put("bathRange", bathRange0);
        }
        homePageService.addRangeToMap(searchParams0, bathRange0, "minBath", "maxBath");

        // Process bed range
        String bedRange0 = req.getParameter("bedRange");
        if (bedRange0 != null && !bedRange0.isEmpty()) {
            searchParams0.put("bedRange", bedRange0);
        }
        homePageService.addRangeToMap(searchParams0, bedRange0, "minBed", "maxBed");
        String[] amenities0 = req.getParameterValues("amenities[]");
        String[] fireEquipments0 = req.getParameterValues("fireEquipments[]");

        int totalHomes = 0;

        // add param to map
        if (keyword0 != null && !keyword0.isEmpty()) {
            searchParams0.put("keyword", keyword0);
        }
        if (currentPage0 != null && !currentPage0.isEmpty()) {
            searchParams0.put("currentPage", currentPage0);
        }
        if (targetPage0 != null && !targetPage0.isEmpty()) {
            searchParams0.put("targetPage", targetPage0);
        }
        if (perPage != null && !perPage.isEmpty()) {
            searchParams0.put("per_page", perPage);
        }
        if(order != null && !order.isEmpty()) {
            searchParams0.put("order", order);
        }
        if(orderBy != null && !orderBy.isEmpty()) {
            searchParams0.put("orderby", orderBy);
        }
        if (homeType0 != null && !homeType0.isEmpty()) {
            searchParams0.put("homeType", homeType0);
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
        if (amenities0 != null && amenities0.length > 0) {
            searchParams0.put("amenities", amenities0);
        }
        if (fireEquipments0 != null && fireEquipments0.length > 0) {
            searchParams0.put("fireEquipments", fireEquipments0);
        }

        // get total home of each searching
        try {
            totalHomes = homePageService.countSearchHome(searchParams0);
            homePageService.updateTargetPage(searchParams0, totalHomes);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Error while fetching new homes: " + e.getMessage());
            ServletUtils.forwardWithMessage(req, resp, e.getMessage());
        }

        //get list home with filter
        List<Home> homes0 = new ArrayList<>(homePageService.searchHome(searchParams0));
        req.setAttribute("totalHomes", totalHomes);

        //get price of each home
        List<Price> prices0 = new ArrayList<>(homePageService.getHomePrice(homes0));

        //get list homeType
        List<HomeType> homeTypes0 = new ArrayList<>(submissonFormService.getHomeTypes());

        //get list amenity
        List<Amentity> listAmen0 = new ArrayList<>(submissonFormService.getAmentities());

        //get list fireEquipment
        List<FireEquipment> listFire0 = new ArrayList<>(submissonFormService.getFireEquipments());

        //get list province
        List<Province> provinces0 = new ArrayList<>(submissonFormService.getProvinces());
        int maxPrice = priceDAO.getMaxPrice();
        int minPrice = priceDAO.getMinPrice();
        req.setAttribute("maxPrice", maxPrice);
        req.setAttribute("minPrice", minPrice);
        BigDecimal minArea = homeDAO.getMinArea();
        BigDecimal maxArea = homeDAO.getMaxArea();
        req.setAttribute("minArea", minArea);
        req.setAttribute("maxArea", maxArea);
        int minBed = homeDAO.getMinBed();
        int maxBed = homeDAO.getMaxBed();
        req.setAttribute("minBed", minBed);
        req.setAttribute("maxBed", maxBed);
        int minBath = homeDAO.getMinBath();
        int maxBath = homeDAO.getMaxBath();
        req.setAttribute("minBath", minBath);
        req.setAttribute("maxBath", maxBath);
        req.setAttribute("homeTypes", homeTypes0);
        req.setAttribute("listAmen", listAmen0);
        req.setAttribute("listFire", listFire0);
        req.setAttribute("provinces", provinces0);
        req.setAttribute("searchParams", searchParams0);
        // Set the home and price data as request attributes for use in the JSP
        req.setAttribute("homes", homes0);
        req.setAttribute("prices", prices0);

        // Forward the request to the JSP page for rendering
        req.getRequestDispatcher("/home-list.jsp").forward(req, resp);
    }
}