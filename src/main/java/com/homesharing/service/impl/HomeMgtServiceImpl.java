package com.homesharing.service.impl;

import com.homesharing.dao.AmentityDAO;
import com.homesharing.dao.AmentityHomeDAO;
import com.homesharing.dao.DistrictDAO;
import com.homesharing.dao.FireEquipHomeDAO;
import com.homesharing.dao.FireEquipmentDAO;
import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.HomeImageDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.ProvinceDAO;
import com.homesharing.dao.WardDAO;
import com.homesharing.model.District;
import com.homesharing.model.FireEquipment;
import com.homesharing.model.Home;
import com.homesharing.model.HomeImage;
import com.homesharing.model.Price;
import com.homesharing.model.Province;
import com.homesharing.model.Ward;
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
    private final HomeImageDAO homeImgDAO;
    private final AmentityHomeDAO amentityDAO;
    private final FireEquipHomeDAO fireEquipDAO;

    public HomeMgtServiceImpl(HomeDAO homeDAO, PriceDAO priceDAO, HomeImageDAO homeImgDAO, AmentityHomeDAO amentityDAO, FireEquipHomeDAO fireEquipDAO) {
        this.homeDAO = homeDAO;
        this.priceDAO = priceDAO;
        this.homeImgDAO = homeImgDAO;
        this.amentityDAO = amentityDAO;
        this.fireEquipDAO = fireEquipDAO;
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
    public int deletePersonalHome(int id) {
        return homeDAO.changeStatus(id,"inactive");
    }

    @Override
    public int clearImageByHomeId(int homeId) {
        return homeImgDAO.clear(homeId);
    }

    @Override
    public int clearAmentityByHomeId(int homeId) {
        return amentityDAO.clear(homeId);
    }

    @Override
    public int clearFireEquipByHomeId(int homeId) {
        return fireEquipDAO.clear(homeId);
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
