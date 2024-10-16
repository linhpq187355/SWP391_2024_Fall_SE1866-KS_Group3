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

package com.homesharing.dao;

import java.util.List;
import com.homesharing.model.Home;


/**
 * Data Access Object (DAO) interface for managing home-related operations.
 * This interface defines methods for retrieving, saving, and managing home
 * information in the database.
 */
public interface HomeDAO {
    /**
     *
     * @return the list of homes
     */
    List<Home> getAllHomes();

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
}
