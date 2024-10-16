/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second
 * 2024-10-10      1.0                 ManhNC            update some method
 */

package com.homesharing.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.homesharing.model.Home;


/**
 * HomeDAO is an interface that defines methods for interacting with the homes data in the database.
 * It provides methods to retrieve, search, and save home information.
 */
public interface HomeDAO {
    /**
     *
     * @return the list of homes
     */
    List<Home> getAllHomes();

    /**
     * Searches for homes based on the search parameters provided in a map.
     *
     * @param searchParams a map of search criteria where the key is the search parameter and the value is its value.
     * @return a list of Home objects matching the search criteria.
     * @throws SQLException if a database access error occurs.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    List<Home> getSearchedHomes(Map<String, Object> searchParams)  throws SQLException, IOException, ClassNotFoundException;

    /**
     * Fetches the first image URL for a given home by its ID.
     *
     * @param homeId the ID of the home.
     * @return a String representing the URL of the first image.
     * @throws SQLException if a database access error occurs.
     */
    String fetchFirstImage(int homeId) throws SQLException;

    /**
     * Retrieves the number of homes that match the search parameters provided.
     *
     * @param searchParams a map of search criteria where the key is the search parameter and the value is its value.
     * @return the number of homes matching the search criteria.
     * @throws SQLException if a database access error occurs.
     * @throws IOException if an I/O error occurs.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    int numOfHome(Map<String, Object> searchParams) throws SQLException, IOException, ClassNotFoundException;

    /**
     * Retrieves the minimum area size of all homes in the database.
     *
     * @return a BigDecimal representing the minimum area size.
     */
    BigDecimal getMinArea();

    /**
     * Retrieves the maximum area size of all homes in the database.
     *
     * @return a BigDecimal representing the maximum area size.
     */
    BigDecimal getMaxArea();

    /**
     * Retrieves the minimum number of bedrooms available in homes in the database.
     *
     * @return the minimum number of bedrooms.
     */
    int getMinBed();

    /**
     * Retrieves the maximum number of bedrooms available in homes in the database.
     *
     * @return the maximum number of bedrooms.
     */
    int getMaxBed();

    /**
     * Retrieves the minimum number of bathrooms available in homes in the database.
     *
     * @return the minimum number of bathrooms.
     */
    int getMinBath();

    /**
     * Retrieves the maximum number of bathrooms available in homes in the database.
     *
     * @return the maximum number of bathrooms.
     */
    int getMaxBath();

    /**
     * Save home's info to the database
     * @param home Home object that need to be saved
     * @return home id
     */
    int saveHome(Home home);

    /**
     * Retrieve new homes' info from the database.
     * @return List of Home objects.
     */

    List<Home> getNewHomes();

    /**
     * Get home object via id
     * @param id
     * @return the home that need to be found
     */
    Home getHomeById(int id);

    /**
     * Retrieves the total number of homes available in the database.
     *
     * @return the total number of homes.
     */
    int getNumberHomes();

    /**
     * Retrieves a list of homes that match the specified criteria.
     *
     * @param matchingHost an array of host IDs used for matching homes.
     * @return a List of Home objects that match the specified criteria.
     */
    List<Home> getMatchingHomes(int[] matchingHost);

    List<Home> getHomesByUserId(int userId);
    int updateHome(Home home);
    int changeStatus(int homeId, String status);
}
