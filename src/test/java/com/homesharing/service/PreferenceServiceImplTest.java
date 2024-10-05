package com.homesharing.service;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import com.homesharing.service.impl.PreferenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

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

    @Test
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
    }

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
}
