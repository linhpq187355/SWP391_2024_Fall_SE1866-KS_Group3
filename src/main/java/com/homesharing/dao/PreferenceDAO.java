package com.homesharing.dao;

import com.homesharing.model.Preference;

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
}