/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second Implement
 */

package com.homesharing.service.impl;
import com.homesharing.dao.*;
import com.homesharing.dao.impl.*;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.*;
import com.homesharing.service.HomePageService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the HomePageService interface.
 * This class provides methods to manage home listings and pricing information.
 */
public class HomePageServiceImpl implements HomePageService {
    private static final Logger logger = Logger.getLogger(HomePageServiceImpl.class.getName());
    private final HomeDAO homeDAO;
    private final PriceDAO priceDAO;
    private final UserDAO userDAO;


    public HomePageServiceImpl(HomeDAO homeDAO, PriceDAO priceDAO, UserDAO userDAO) {
        this.homeDAO = homeDAO;
        this.priceDAO = priceDAO;
        this.userDAO = userDAO;
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

    @Override
    public int getHomeCount() {
        try {
            return homeDAO.getNumberHomes();
        } catch (GeneralException e) {
            logger.log(Level.SEVERE, "Failed to retrieve total homes: ", e);
            throw new GeneralException("Failed to retrieve total homes: ", e);
        }
    }

    /**
     * Retrieves a list of homes that match the given host IDs for a specific user.
     *
     * @param matchingHostsId an array of host IDs to find matching homes.
     * @param userId the ID of the user looking for matching homes.
     * @return a list of {@link Home} objects that match the specified host IDs for the user.
     */
    @Override
    public List<Home> getMatchingHome(int[] matchingHostsId, int userId) {
        List<Home> homeList = homeDAO.getMatchingHomes(matchingHostsId);

        if(homeList == null || homeList.isEmpty()) {
            logger.warning("Home list is null or empty. Returning an empty home list.");
            return null;
        }
        List<Price> listPrice = priceDAO.getPrices(homeList);
        User user = userDAO.getMatchingUserProfile(userId);
        List<Home> listMatchingHomes = new ArrayList<>();

        for(int i = 0; i < homeList.size(); i++) {
            boolean moveInCheck = false;
            if ((homeList.get(i).getMoveInDate().isEqual(user.getEarliestMoveIn()) || homeList.get(i).getMoveInDate().isAfter(user.getEarliestMoveIn()))
                    && (homeList.get(i).getMoveInDate().isBefore(user.getLatestMoveIn()) || homeList.get(i).getMoveInDate().isEqual(user.getLatestMoveIn()))) {
                moveInCheck = true;
            }

            if (user.getEarliestMoveIn().isBefore(homeList.get(i).getMoveInDate())
                    && user.getEarliestMoveIn().plusDays(7).isAfter(homeList.get(i).getMoveInDate())) {
                moveInCheck = true;
            }
            Home home = homeList.get(i);
            if(user.getMaxBudget() >= listPrice.get(i).getPrice()){
                if((user.getDuration().equals("short") && homeList.get(i).getLeaseDuration() <6) || (user.getDuration().equals("long") && homeList.get(i).getLeaseDuration() >=6) ){
                    if(moveInCheck){
                        listMatchingHomes.add(home);
                    }
                }
            }
        }
        return listMatchingHomes;
    }


}
