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

import com.homesharing.model.Preference;

import java.util.List;
import java.util.Map;

/**
 * PreferenceDAO interface provides methods for
 * accessing and managing user preferences in the system.
 */
public interface PreferenceDAO {

    /**
     * Retrieves the Preference object associated with a specific user ID.
     *
     * @param userId the ID of the user whose preferences are to be retrieved
     * @return the Preference object for the specified user ID, or null if not found
     */
    Preference getPreference(int userId);

    /**
     * Updates the preferences of the user with the provided preference values.
     *
     * @param preferences a map containing the preference names and their corresponding values
     * @return the number of rows affected in the database
     */
    int updatePreference(Map<String, Integer> preferences);

    /**
     * Inserts a new preference record for the specified user ID.
     *
     * @param userId the ID of the user for whom the preference record is to be created
     * @return the number of rows affected in the database
     */
    int insertPreference(int userId);

    /**
     * Updates the preferences for the specified Preference object.
     *
     * @param preference the Preference object containing updated values
     * @return the number of rows affected in the database
     */
    int updatePreference(Preference preference);

    /**
     * Retrieves a list of matching preferences for a specific user ID.
     *
     * @param userId the ID of the user whose matching preferences are to be retrieved
     * @return a list of Preference objects that match the specified user ID
     */
    List<Preference> listMatchingPreference(int userId);
}