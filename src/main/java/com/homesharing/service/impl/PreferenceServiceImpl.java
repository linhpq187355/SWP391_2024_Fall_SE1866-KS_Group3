package com.homesharing.service.impl;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import com.homesharing.service.PreferenceService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Implementation of the {@link PreferenceService} interface.
 * This class handles the business logic related to user preferences.
 */
public class PreferenceServiceImpl implements PreferenceService {

    private final PreferenceDAO preferenceDAO;
    private static final Logger logger = Logger.getLogger(PreferenceServiceImpl.class.getName());

    /**
     * Constructor to initialize PreferenceServiceImpl with a {@link PreferenceDAO}.
     *
     * @param preferenceDAO the data access object to handle preference-related operations.
     */
    public PreferenceServiceImpl(PreferenceDAO preferenceDAO) {
        this.preferenceDAO = preferenceDAO;
    }

    /**
     * Adds a default preference for a user.
     *
     * @param userId the ID of the user for whom preferences are added.
     * @return true if preferences were successfully added, false otherwise.
     */
    @Override
    public boolean addPreference(int userId) {
        try {
            // Call the insertPreference method from PreferenceDAO
            int affectedRows = preferenceDAO.insertPreference(userId);

            // If affectedRows is greater than 0, the insertion was successful
            return affectedRows > 0;
        } catch (Exception e) {
            // Log the error and rethrow a custom exception
            logger.severe("Error adding preference for user ID: " + userId);
            throw new GeneralException("Failed to add preference", e);
        }
    }

    /**
     * Updates the user's preferences based on the given parameters.
     *
     * @param r_cleanliness   user's cleanliness rating.
     * @param cleanlinessStatus status of the cleanliness preference.
     * @param r_smoking       user's smoking rating.
     * @param smokingStatus   status of the smoking preference.
     * @param r_drinking      user's drinking rating.
     * @param drinkingStatus  status of the drinking preference.
     * @param r_interaction   user's interaction rating.
     * @param interactionStatus status of the interaction preference.
     * @param r_cooking       user's cooking rating.
     * @param cookingStatus   status of the cooking preference.
     * @param r_pet           user's pet preference rating.
     * @param petStatus       status of the pet preference.
     * @param userId          the ID of the user.
     * @return number of rows updated in the preferences table.
     */
    @Override
    public int updateUserPreference(String r_cleanliness, String cleanlinessStatus, String r_smoking, String smokingStatus,
                                    String r_drinking, String drinkingStatus, String r_interaction, String interactionStatus,
                                    String r_cooking, String cookingStatus, String r_pet, String petStatus, String userId) {
        try {
            // Create a map to store preference updates for the user
            Map<String, Integer> preferenceMap = new HashMap<>();
            preferenceMap.put("user_id", Integer.parseInt(userId));

            // Check the status of each preference and update the map accordingly
            if ("true".equals(cleanlinessStatus)) {
                preferenceMap.put("cleanliness", Integer.parseInt(r_cleanliness));
            }
            if ("true".equals(smokingStatus)) {
                preferenceMap.put("smoking", Integer.parseInt(r_smoking));
            }
            if ("true".equals(drinkingStatus)) {
                preferenceMap.put("drinking", Integer.parseInt(r_drinking));
            }
            if ("true".equals(interactionStatus)) {
                preferenceMap.put("interaction", Integer.parseInt(r_interaction));
            }
            if ("true".equals(cookingStatus)) {
                preferenceMap.put("cooking", Integer.parseInt(r_cooking));
            }
            if ("true".equals(petStatus)) {
                preferenceMap.put("pet", Integer.parseInt(r_pet));
            }

            // Update the preferences in the database using PreferenceDAO
            return preferenceDAO.updatePreference(preferenceMap);
        } catch (Exception e) {
            // Log and throw a custom exception if any error occurs during the update
            logger.severe("Error updating preferences for user ID: " + userId);
            throw new GeneralException("Failed to update preferences", e);
        }
    }

    /**
     * Retrieves the preferences of a specific user.
     *
     * @param userId the ID of the user whose preferences are being retrieved.
     * @return the {@link Preference} object containing the user's preferences.
     */
    @Override
    public Preference getPreference(int userId) {
        try {
            // Fetch the preferences for the specified user from the DAO
            return preferenceDAO.getPreference(userId);
        } catch (Exception e) {
            logger.severe("Error fetching preferences for user ID: " + userId);
            throw new GeneralException("Failed to retrieve preferences", e);
        }
    }
}