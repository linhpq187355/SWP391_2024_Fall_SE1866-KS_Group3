/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-10      1.0                 ManhNC         Implement search service
 */
package com.homesharing.service;

import com.homesharing.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * This interface defines the service methods related to the home page functionality.
 * It provides methods for retrieving homes, adding new homes, searching for homes, and other related operations.
 */
public interface HomePageService {

    /**
     * Retrieves a list of all homes.
     *
     * @return A list of {@link Home} objects.
     */
    List<Home> getHomes();

    /**
     * Retrieves a list of new homes.  The criteria for "new" should be defined in the implementation.
     *
     * @return A list of {@link Home} objects representing new homes.
     */
    List<Home> getNewHomes();

    /**
     * Retrieves the prices for a given list of homes.
     *
     * @param homes The list of {@link Home} objects for which to retrieve prices.
     * @return A list of {@link Price} objects corresponding to the provided homes.
     */
    List<Price> getHomePrice(List<Home> homes);
    void addHome(Home home);
    Home getHomeById(int id);

    /**
     * Adds a range to a map, using the provided range parameter, minimum key, and maximum key.
     * This is likely used for filtering/searching.
     *
     * @param map        The map to add the range to.  This map is likely used to build search queries.
     * @param rangeParam The parameter representing the range (e.g., "price").
     * @param minKey     The key for the minimum value of the range (e.g., "minPrice").
     * @param maxKey     The key for the maximum value of the range (e.g., "maxPrice").
     */
    void addRangeToMap(Map<String, Object> map, String rangeParam, String minKey, String maxKey);

    /**
     * Searches for homes based on the provided search parameters.
     *
     * @param searchParams A map containing the search parameters.
     * @return A list of {@link Home} objects that match the search criteria.
     */
    List<Home> searchHome(Map<String, Object> searchParams);

    /**
     * Counts the number of homes that match the provided search parameters.
     *
     * @param searchParams A map containing the search parameters.
     * @return The number of homes that match the search criteria.
     * @throws SQLException            If a database access error occurs.
     * @throws IOException             If an I/O error occurs.
     * @throws ClassNotFoundException If a class cannot be found.
     */
    int countSearchHome(Map<String, Object> searchParams) throws SQLException, IOException, ClassNotFoundException;

    /**
     * update the target page if target page exceed total homes
     *
     * @param searchParams A map containing the search parameters.
     * @param totalHomes total homes in each searching
     */
    void updateTargetPage(Map<String, Object> searchParams, int totalHomes);
}
