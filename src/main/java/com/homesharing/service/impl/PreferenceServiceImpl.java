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

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import com.homesharing.service.PreferenceService;

import java.util.HashMap;
import java.util.List;
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
            preferenceMap.put("usersId", Integer.parseInt(userId));

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
     * Updates the user preferences based on the provided values.
     *
     * @param r_cleanliness the cleanliness preference as a string
     * @param r_smoking     the smoking preference as a string
     * @param r_drinking    the drinking preference as a string
     * @param r_interaction the interaction preference as a string
     * @param r_guest       the guest preference as a string
     * @param r_cooking     the cooking preference as a string
     * @param r_pet         the pet preference as a string
     * @param userId        the ID of the user whose preferences are being updated
     * @return the number of rows affected in the database
     */
    @Override
    public int updateUserPreference(String r_cleanliness, String r_smoking,
                                    String r_drinking, String r_interaction,String r_guest,
                                    String r_cooking, String r_pet, String userId) {
        try {
            Preference preference = new Preference();
            preference.setUserId(Integer.parseInt(userId));
            preference.setCleanliness(Integer.parseInt(r_cleanliness));
            preference.setSmoking(Integer.parseInt(r_smoking));
            preference.setDrinking(Integer.parseInt(r_drinking));
            preference.setInteraction(Integer.parseInt(r_interaction));
            preference.setCooking(Integer.parseInt(r_cooking));
            preference.setPet(Integer.parseInt(r_pet));
            preference.setGuest(Integer.parseInt(r_guest));

            // Update the preferences in the database using PreferenceDAO
            return preferenceDAO.updatePreference(preference);
        } catch (Exception e) {
            // Log and throw a custom exception if any error occurs during the update
            logger.severe("Error updating preferences for user ID: " + userId);
            throw new GeneralException("Failed to update preferences", e);
        }
    }

    /**
     * Lists the matching preferences for the specified user.
     *
     * @param userId the ID of the user whose matching preferences are being retrieved
     * @return an array of user IDs representing the top 10 matches based on preferences
     */
    @Override
    public int[] listMatchingPreferences(int userId) {
        List<Preference> listMatchingPreference = preferenceDAO.listMatchingPreference(userId);
        Preference pref = preferenceDAO.getPreference(userId);
        Map<Integer, Integer> hostScores = new HashMap<>();
        for(int i = 0; i < listMatchingPreference.size(); i++){
            Preference hostPref = listMatchingPreference.get(i);
            int score = 0;
            if (Math.abs(pref.getCleanliness() - hostPref.getCleanliness()) <= 1) {
                score += 10;
            }
            if (Math.abs(pref.getSmoking() - hostPref.getSmoking()) <= 1) {
                score += 10;
            }
            if (Math.abs(pref.getDrinking() - hostPref.getDrinking()) <= 1) {
                score += 10;
            }
            if (Math.abs(pref.getInteraction() - hostPref.getInteraction()) <= 1) {
                score += 10;
            }
            if (Math.abs(pref.getGuest() - hostPref.getGuest()) <= 1) {
                score += 10;
            }
            if (Math.abs(pref.getCooking() - hostPref.getCooking()) <= 1) {
                score += 10;
            }
            if (Math.abs(pref.getPet() - hostPref.getPet()) <= 1) {
                score += 10;
            }
            hostScores.put(hostPref.getUserId(), score);
        }
        return hostScores.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // Sắp xếp giảm dần theo điểm số
                .limit(10) // Giới hạn 10 phần tử
                .mapToInt(Map.Entry::getKey) // Lấy ID của host (key)
                .toArray();
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