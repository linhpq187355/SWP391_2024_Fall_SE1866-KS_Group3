package com.homesharing.service;

import com.homesharing.model.Preference;

/**
 * Interface for managing user preferences.
 * Provides methods for adding, updating, and retrieving user preferences.
 */
public interface PreferenceService {

    /**
     * Adds default preferences for a new user.
     *
     * @param userId the ID of the user for whom the preferences are being added.
     * @return true if the preferences were successfully added, false otherwise.
     */
    boolean addPreference(int userId);

    /**
     * Updates user preferences based on the given parameters.
     *
     * @param r_cleanliness the user's cleanliness rating.
     * @param cleanlinessStatus the user's cleanliness status.
     * @param r_smoking the user's smoking rating.
     * @param smokingStatus the user's smoking status.
     * @param r_drinking the user's drinking rating.
     * @param drinkingStatus the user's drinking status.
     * @param r_interaction the user's interaction rating.
     * @param interactionStatus the user's interaction status.
     * @param r_cooking the user's cooking rating.
     * @param cookingStatus the user's cooking status.
     * @param r_pet the user's pet rating.
     * @param petStatus the user's pet status.
     * @param userId the ID of the user whose preferences are being updated.
     * @return the number of rows affected in the database after the update operation.
     */
    int updateUserPreference(String r_cleanliness, String cleanlinessStatus, String r_smoking, String smokingStatus,
                             String r_drinking, String drinkingStatus, String r_interaction, String interactionStatus,
                             String r_cooking, String cookingStatus, String r_pet, String petStatus, String userId);

    /**
     * Retrieves the preferences of a user.
     *
     * @param userId the ID of the user whose preferences are being retrieved.
     * @return the user's preferences as a {@link Preference} object.
     */
    Preference getPreference(int userId);
}