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
 * 2024-10-25      2.0              Pham Quang Linh     Add functions
 */

package com.homesharing.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.homesharing.model.Appointment;
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
     */
    List<Home> getSearchedHomes(Map<String, Object> searchParams)  throws SQLException;

    /**
     * Fetches the first image URL for a given home by its ID.
     *
     * @param homeId the ID of the home.
     * @return a String representing the URL of the first image.
     * @throws SQLException if a database access error occurs.
     */
    String fetchFirstImage(int homeId) throws SQLException;
    List<String> fetchImages(int homeId);

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
    BigDecimal getMinArea() throws SQLException;

    /**
     * Retrieves the maximum area size of all homes in the database.
     *
     * @return a BigDecimal representing the maximum area size.
     */
    BigDecimal getMaxArea() throws SQLException;

    /**
     * Retrieves the minimum number of bedrooms available in homes in the database.
     *
     * @return the minimum number of bedrooms.
     */
    int getMinBed() throws SQLException;

    /**
     * Retrieves the maximum number of bedrooms available in homes in the database.
     *
     * @return the maximum number of bedrooms.
     */
    int getMaxBed() throws SQLException;

    /**
     * Retrieves the minimum number of bathrooms available in homes in the database.
     *
     * @return the minimum number of bathrooms.
     */
    int getMinBath() throws SQLException;

    /**
     * Retrieves the maximum number of bathrooms available in homes in the database.
     *
     * @return the maximum number of bathrooms.
     */
    int getMaxBath() throws SQLException;

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


    /**
     * Retrieves a list of homes associated with a list of appointments.
     *
     * @param appointments a list of Appointment objects to filter homes.
     * @return a List of Home objects associated with the specified appointments.
     */
    List<Home> getHomeByAppointment(List<Appointment> appointments);

    /**
     * Fetch home list via user id
     * @param userId
     * @return the home list
     */
    List<Home> getHomesByUserId(int userId);

    /**
     * Update the given home
     * @param home
     * @return number of rows affected
     */
    int updateHome(Home home);

    /**
     * Change the status of home
     * @param homeId
     * @param status
     * @return the result
     */
    int changeStatus(int homeId, String status);

    /**
     * Retrieves the total number of homes.
     * @return The total count of homes.
     */
    int getTotalHome();

    List<Home> getByCreatedBy(int createdById);

    /**
     * Counts the number of homes with the specified status.
     * @param status The status to filter homes by.
     * @return The count of homes that match the specified status.
     */
    int countHomesByStatus(String status);
    /**
     * Counts the number of homes created in the current month.
     * @return The count of homes created in the current month.
     */
    int countHomesInMonth();
    /**
     * Calculates the average lease duration of all homes.
     * @return The average lease duration in days.
     */
    float avgLeaseDuration();
    /**
     * Counts the number of move-in dates that fall within the current month.
     * @return The count of move-in dates in the current month.
     */
    int countMoveInDateInMonth();

    /**
     * Fetches the latest homes from the database.
     * @param numberOfHomes The number of latest homes to return.
     * @return A list of the latest homes.
     */
    List<Home> getLatestHomes(int numberOfHomes);


}
