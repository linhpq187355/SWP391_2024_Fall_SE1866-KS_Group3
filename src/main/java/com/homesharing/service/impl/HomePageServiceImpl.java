package com.homesharing.service.impl;
import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.*;
import com.homesharing.service.HomePageService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the HomePageService interface.
 * This class provides methods to manage home listings and pricing information.
 */
public class HomePageServiceImpl implements HomePageService {
    private static final Logger logger = Logger.getLogger(HomePageServiceImpl.class.getName());
    private final HomeDAO homeDAO;
    private final PriceDAO priceDAO;


    public HomePageServiceImpl(HomeDAO homeDAO, PriceDAO priceDAO) {
        this.homeDAO = homeDAO;
        this.priceDAO = priceDAO;
    }

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
