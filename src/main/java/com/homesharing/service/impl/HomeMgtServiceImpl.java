package com.homesharing.service.impl;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.HomeMgtService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class HomeMgtServiceImpl implements HomeMgtService {
    private static final Logger logger = Logger.getLogger(HomePageServiceImpl.class.getName());
    private final HomeDAO homeDAO;
    private final PriceDAO priceDAO;

    public HomeMgtServiceImpl(HomeDAO homeDAO, PriceDAO priceDAO) {
        this.homeDAO = homeDAO;
        this.priceDAO = priceDAO;
    }

    @Override
    public List<Home> getPersonalHome(int userId) {
        return homeDAO.getHomesByUserId(userId);
    }

    @Override
    public int updatePersonalHome(Home home) {
        return homeDAO.updateHome(home);
    }

    @Override
    public int deletePersonalHome(Home home) {
        return homeDAO.changeStatus(home.getId(),"inactive");
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
    public Map<Integer, Price> getHomePrice(List<Home> homes) {
        // Validate input
        if (homes == null || homes.isEmpty()) {
            logger.warning("Home list is null or empty. Returning an empty price map.");
            return new HashMap<>(); // Return an empty map instead of a list
        }

        List<Price> prices = new ArrayList<>();
        Map<Integer, Price> priceMap = new HashMap<>(); // Create a map to store prices

        try {
            // Call the DAO method to get prices
            prices = priceDAO.getPrices(homes);
            logger.info("Retrieved prices: " + prices); // Log the retrieved prices

            // Populate the priceMap with home ID as key and Price as value
            for (Price price : prices) {
                Integer homeId = price.getHomesId(); // Ensure this is the correct type
                priceMap.put(homeId, price); // Assuming getHomesId() returns the home ID
                logger.info("Mapping home ID " + homeId + " to price " + price + " - " + price.getPrice()); // Log the mapping
            }
        } catch (Exception e) {
            logger.severe("Unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("Error retrieving prices from the database: " + e.getMessage(), e);
        }

        return priceMap; // Return the map instead of the list
    }
}
