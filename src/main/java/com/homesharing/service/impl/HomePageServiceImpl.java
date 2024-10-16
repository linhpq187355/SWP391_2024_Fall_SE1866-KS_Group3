/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-10      1.0                 ManhNC         Implement search service
 */
package com.homesharing.service.impl;
import com.homesharing.dao.*;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.*;
import com.homesharing.service.HomePageService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Implementation of the HomePageService interface.
 * This class provides services related to home listings, including retrieval, searching, and adding new homes.
 */
public class HomePageServiceImpl implements HomePageService {

    private static final Logger logger = Logger.getLogger(HomePageServiceImpl.class.getName());

    private final HomeDAO homeDAO;
    private final PriceDAO priceDAO;
    private static final int DEFAULT_NUMBER_OF_HOMES = 12;

    /**
     * Constructor for HomePageServiceImpl.
     * @param homeDAO The Data Access Object for Home entities.
     * @param priceDAO The Data Access Object for Price entities.
     */
    public HomePageServiceImpl(HomeDAO homeDAO, PriceDAO priceDAO) {
        this.homeDAO = homeDAO;
        this.priceDAO = priceDAO;
    }

    /**
     * Retrieves all homes from the data source.
     *
     * @return A list of all Home objects.
     */
    @Override
    public List<Home> getHomes() {
        return homeDAO.getAllHomes();
    }

    /**
     * Retrieves a list of the newest homes from the data source.
     *
     * @return List of the newest Home objects.
     * @throws GeneralException if there is an error retrieving new homes.
     */
    @Override
    public List<Home> getNewHomes() {
        List<Home> homeList = new ArrayList<>();

        try {
            // Call the DAO method to retrieve new homes
            homeList = homeDAO.getNewHomes();
            for (Home h : homeList) {
                List<String> image = new ArrayList<>();
                image.add(homeDAO.fetchFirstImage(h.getId()));
                h.setImages(image);
            }
        } catch (GeneralException e) {
            // Log the specific exception thrown from the DAO
            logger.severe("Error while fetching new homes: " + e.getMessage());
            // Optionally, you can throw a custom exception or handle it as needed
            throw new GeneralException("Service layer: Unable to retrieve new homes", e);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            logger.severe("Unexpected error in service layer: " + e.getMessage());
            throw new GeneralException("Service layer: Unexpected error", e);
        }

        return homeList; // Return the list of homes
    }

    /**
     * Adds a price range to the provided map based on the given range parameter.
     *
     * @param map          The map to which the price range will be added.
     * @param rangeParam   The price range parameter as a string (e.g., "[min,max]").
     * @param minKey       The key for the minimum price in the map.
     * @param maxKey       The key for the maximum price in the map.
     * @throws NumberFormatException If the rangeParam is not in the correct format or contains non-numeric values.
     */
    @Override
    public void addRangeToMap(Map<String, Object> map, String rangeParam, String minKey, String maxKey) {
        if (rangeParam != null && !rangeParam.isEmpty()) {
            String[] ranges = rangeParam.replaceAll("[\\[\\]]", "").split(",");
            if (ranges.length == 2) { // Check for exactly two values (min and max)
                try {
                    int minValue = Integer.parseInt(ranges[0].trim());
                    int maxValue = Integer.parseInt(ranges[1].trim());
                    map.put(minKey, minValue);
                    map.put(maxKey, maxValue);
                } catch (NumberFormatException e) {
                    logger.warning("Invalid number format for range parameter: " + rangeParam);
                    // Handle the exception appropriately, e.g., throw an exception or set default values.
                    throw new NumberFormatException("Invalid number format for price range: " + rangeParam); // Re-throw for upper layers to handle.
                }
            } else {
                logger.warning("Invalid range parameter format: " + rangeParam);
                // Handle the invalid format appropriately, e.g., throw an exception, log a warning, or ignore.
                throw new GeneralException("Invalid range parameter format: " + rangeParam);
            }
        }
    }

    /**
     * Searches for homes based on the provided search parameters.
     * Includes the first image associated with each home.
     *
     * @param searchParams A map containing the search criteria.
     * @return A list of Home objects matching the search criteria, including their first image.
     */
    @Override
    public List<Home> searchHome(Map<String, Object> searchParams) {
        try {
            if (!searchParams.containsKey("per_page")) {
                searchParams.put("per_page", DEFAULT_NUMBER_OF_HOMES);
            }
            List<Home> homeList = homeDAO.getSearchedHomes(searchParams);
            for (Home home : homeList) {
                List<String> images = new ArrayList<>();
                images.add(homeDAO.fetchFirstImage(home.getId()));
                home.setImages(images);
            }
            return homeList;

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.severe("Error during home search: " + e.getMessage());
            throw new GeneralException("Failed to search for homes.", e);
        }
    }

    /**
     * Counts the number of homes that match the provided search parameters.
     *
     * @param searchParams A map containing the search criteria.
     * @return The total count of homes matching the search criteria.
     * @throws SQLException            If a database access error occurs.
     * @throws IOException             If an I/O error occurs.
     * @throws ClassNotFoundException If the JDBC driver class cannot be found.
     */
    @Override
    public int countSearchHome(Map<String, Object> searchParams) throws SQLException, IOException, ClassNotFoundException {
        return homeDAO.numOfHome(searchParams);
    }

    /**
     * update the target page if target page exceed total homes
     *
     * @param searchParams A map containing the search parameters.
     * @param totalHomes   total homes in each searching
     */
    @Override
    public void updateTargetPage(Map<String, Object> searchParams, int totalHomes){
        String perPage = null;
        if(searchParams.containsKey("per_page")) {
            perPage = searchParams.get("per_page").toString();
        }
        String targetPage = null;
        if(searchParams.containsKey("targetPage")) {
            targetPage = searchParams.get("targetPage").toString();
        }
        int perPageValue;
        if (perPage == null || perPage.isEmpty()) {
            perPageValue = DEFAULT_NUMBER_OF_HOMES;
        } else {
            perPageValue = Integer.parseInt(perPage);
        }
        int targetPageValue;
        if (targetPage == null || targetPage.isEmpty()) {
            targetPageValue = 1;
        } else {
            targetPageValue = Integer.parseInt(targetPage);
        }
        int numOfPages;
        if(totalHomes % perPageValue == 0) {
            numOfPages = totalHomes / perPageValue;
        } else {
            numOfPages = totalHomes / perPageValue + 1;
        }
        if(targetPageValue > numOfPages) {
            searchParams.put("targetPage", "1");
        }
    }

    /**
     * Retrieves price information for a list of homes.
     *
     * @param homes List of Home objects to retrieve prices for.
     * @return List of Price objects associated with the provided homes.
     * @throws IllegalArgumentException if the input list of homes is null or empty.
     * @throws RuntimeException if there is an error retrieving prices.
     */
    @Override
    public List<Price> getHomePrice(List<Home> homes) {
        // Validate input
        if (homes == null || homes.isEmpty()) {
            logger.warning("Home list is null or empty. Returning an empty price list.");
            return new ArrayList<>();
        }

        List<Price> prices = new ArrayList<>();

        try {
            // Call the DAO method to get prices
            prices = priceDAO.getPrices(homes);
        } catch (Exception e) {
            logger.severe("Unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("Error retrieving prices from the database: " + e.getMessage(), e);
        }

        return prices;
    }

    @Override
    public void addHome(Home home) { homeDAO.saveHome(home);}

    @Override
    public Home getHomeById(int id) { return homeDAO.getHomeById(id); }
}
