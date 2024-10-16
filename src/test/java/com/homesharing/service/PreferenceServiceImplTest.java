package com.homesharing.service;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import com.homesharing.service.impl.PreferenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PreferenceServiceImplTest {
    private PreferenceDAO preferenceDAO;
    private PreferenceServiceImpl preferenceService;

    @BeforeEach
    void setUp() {
        preferenceDAO = mock(PreferenceDAO.class);
        preferenceService = new PreferenceServiceImpl(preferenceDAO);
    }

    @Test
    void testAddPreference_success() {
        // Arrange
        int userId = 1;
        when(preferenceDAO.insertPreference(userId)).thenReturn(1); // Mocking the insertPreference method

        // Act
        boolean result = preferenceService.addPreference(userId);

        // Assert
        assertTrue(result);
        verify(preferenceDAO).insertPreference(userId);
    }

    @Test
    void testAddPreference_failure() {
        // Arrange
        int userId = 1;
        when(preferenceDAO.insertPreference(userId)).thenReturn(0); // Simulating failure

        // Act
        boolean result = preferenceService.addPreference(userId);

        // Assert
        assertFalse(result);
        verify(preferenceDAO).insertPreference(userId);
    }

    @Test
    void testAddPreference_generalException() {
        // Arrange
        int userId = 1;
        when(preferenceDAO.insertPreference(userId)).thenThrow(new GeneralException("Error"));

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            preferenceService.addPreference(userId);
        });

        assertEquals("Failed to add preference", thrown.getMessage());
    }

    /*@Test
    void testUpdateUserPreference_success() {
        // Arrange
        String r_cleanliness = "5";
        String cleanlinessStatus = "true";
        String r_smoking = "2";
        String smokingStatus = "false";
        String r_drinking = "3";
        String drinkingStatus = "true";
        String r_interaction = "4";
        String interactionStatus = "true";
        String r_cooking = "1";
        String cookingStatus = "true";
        String r_pet = "3";
        String petStatus = "false";
        String userId = "1";

        when(preferenceDAO.updatePreference(any())).thenReturn(1); // Mocking the updatePreference method

        // Act
        int result = preferenceService.updateUserPreference(r_cleanliness, cleanlinessStatus, r_smoking, smokingStatus,
                r_drinking, drinkingStatus, r_interaction, interactionStatus, r_cooking, cookingStatus, r_pet, petStatus, userId);

        // Assert
        assertEquals(1, result);
        ArgumentCaptor<Map<String, Integer>> captor = ArgumentCaptor.forClass(Map.class);
        verify(preferenceDAO).updatePreference(captor.capture());

        Map<String, Integer> capturedMap = captor.getValue();
        assertEquals(1, capturedMap.get("usersId"));
        assertEquals(5, capturedMap.get("cleanliness"));
        assertEquals(3, capturedMap.get("drinking"));
        assertEquals(4, capturedMap.get("interaction"));
        assertEquals(1, capturedMap.get("cooking"));
    }

    @Test
    void testUpdateUserPreference_generalException() {
        // Arrange
        String r_cleanliness = "5";
        String cleanlinessStatus = "true";
        String userId = "1";
        when(preferenceDAO.updatePreference(any())).thenThrow(new GeneralException("Error"));

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            preferenceService.updateUserPreference(r_cleanliness, cleanlinessStatus, null, null, null, null, null, null, null, null, null, null, userId);
        });

        assertEquals("Failed to update preferences", thrown.getMessage());
    }*/

    @Test
    void testGetPreference_success() {
        // Arrange
        int userId = 1;
        Preference expectedPreference = new Preference();
        when(preferenceDAO.getPreference(userId)).thenReturn(expectedPreference); // Mocking the getPreference method

        // Act
        Preference result = preferenceService.getPreference(userId);

        // Assert
        assertEquals(expectedPreference, result);
        verify(preferenceDAO).getPreference(userId);
    }

    @Test
    void testGetPreference_generalException() {
        // Arrange
        int userId = 1;
        when(preferenceDAO.getPreference(userId)).thenThrow(new GeneralException("Error"));

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            preferenceService.getPreference(userId);
        });

        assertEquals("Failed to retrieve preferences", thrown.getMessage());
    }

    @Test
    void testListMatchingPreferences_success() {
        // Arrange
        int userId = 1;
        Preference userPreference = new Preference();
        userPreference.setUserId(userId);
        userPreference.setCleanliness(5);
        userPreference.setSmoking(3);
        userPreference.setDrinking(4);
        userPreference.setInteraction(5);
        userPreference.setGuest(3);
        userPreference.setCooking(4);
        userPreference.setPet(2);

        List<Preference> hostPreferences = new ArrayList<>();
        Preference host1 = new Preference();
        host1.setUserId(2);
        host1.setCleanliness(5);
        host1.setSmoking(3);
        host1.setDrinking(4);
        host1.setInteraction(5);
        host1.setGuest(3);
        host1.setCooking(4);
        host1.setPet(2);

        Preference host2 = new Preference();
        host2.setUserId(3);
        host2.setCleanliness(6); // Difference of 1 with user, should still count as a match
        host2.setSmoking(2); // Difference of 1 with user, should still count as a match
        host2.setDrinking(4);
        host2.setInteraction(4);
        host2.setGuest(2);
        host2.setCooking(4);
        host2.setPet(3);

        hostPreferences.add(host1);
        hostPreferences.add(host2);

        when(preferenceDAO.getPreference(userId)).thenReturn(userPreference);
        when(preferenceDAO.listMatchingPreference(userId)).thenReturn(hostPreferences);

        // Act
        int[] matchingHosts = preferenceService.listMatchingPreferences(userId);

        // Assert
        assertNotNull(matchingHosts);
        assertEquals(2, matchingHosts.length); // Expecting 2 hosts
        assertEquals(2, matchingHosts[0]); // Host with ID 2 has the highest score
        assertEquals(3, matchingHosts[1]); // Host with ID 3 has the next highest score
        verify(preferenceDAO, times(1)).getPreference(userId);
        verify(preferenceDAO, times(1)).listMatchingPreference(userId);
    }

    @Test
    void testListMatchingPreferences_noMatches() {
        // Arrange
        int userId = 1;
        Preference userPreference = new Preference();
        userPreference.setUserId(userId);
        userPreference.setCleanliness(5);

        when(preferenceDAO.getPreference(userId)).thenReturn(userPreference);
        when(preferenceDAO.listMatchingPreference(userId)).thenReturn(new ArrayList<>());

        // Act
        int[] matchingHosts = preferenceService.listMatchingPreferences(userId);

        // Assert
        assertNotNull(matchingHosts);
        assertEquals(0, matchingHosts.length); // No matches expected
        verify(preferenceDAO, times(1)).getPreference(userId);
        verify(preferenceDAO, times(1)).listMatchingPreference(userId);
    }

}
