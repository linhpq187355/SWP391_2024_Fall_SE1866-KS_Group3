/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second Implement
 * 2024-10-10      1.0                 ManhNC         Implement search service
 * 2024-10-25      2.0              Pham Quang Linh     Add functions
 */
package com.homesharing.service;

import com.homesharing.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Interface for the HomePageService.
 * This interface defines the methods related to home management on the homepage.
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
    void addHome(Home home) throws SQLException, IOException, ClassNotFoundException;
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

    /**
     * Retrieves the total count of homes available in the system.
     *
     * @return The total number of homes.
     */
    int getHomeCount();

    /**
     * Retrieves a list of homes that match the given host IDs for a specific user.
     *
     * @param matchingHostsId an array of host IDs to find matching homes.
     * @param userId the ID of the user looking for matching homes.
     * @return a list of {@link Home} objects that match the specified host IDs for the user.
     */
    List<Home> getMatchingHome(int[] matchingHostsId, int userId);

    List<Home> getHomesByUser(int userId);

    /**
     * Retrieves a list of homes associated with a specific list of appointments.
     *
     * @param appointments A list of {@link Appointment} objects to find matching homes.
     * @return A list of {@link Home} objects associated with the provided appointments.
     */
    List<Home> getHomesByAppoinment(List<Appointment> appointments);
    List<Price> getHomesByUserPrices(List<Home> getHomesByUser);
    void updateStatusHome(int homeId, String status);
}
