package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Preference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PreferenceDAOImplTest {
    private PreferenceDAO preferenceDao;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private MockedStatic<DBContext> mockedDBContext;

    @BeforeEach
    public void setUp() {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        // Mock DBContext.getConnection() to return the mock connection
        mockedDBContext = Mockito.mockStatic(DBContext.class);
        mockedDBContext.when(DBContext::getConnection).thenReturn(connection);
        preferenceDao = new PreferenceDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }


    @Test
    public void testUpdatePreference_Success() throws Exception {
        Map<String, Integer> preferences = new HashMap<>();
        preferences.put("usersId", 1);
        preferences.put("cleanliness", 80);
        preferences.put("drinking", 90);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        String sql = "UPDATE [dbo].[Preferences] SET cleanliness = ?, drinking = ? WHERE usersId = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // simulate 1 row updated

        int rowsUpdated = preferenceDao.updatePreference(preferences);

        assertEquals(1, rowsUpdated);
    }

    /*@Test
    public void testUpdatePreference_NullMap() {
        int rowsUpdated = preferenceDao.updatePreference(null);
        assertEquals(0, rowsUpdated);
    }

    @Test
    public void testUpdatePreference_SQLException() throws Exception {
        Map<String, Integer> preferences = new HashMap<>();
        preferences.put("usersId", 1);
        preferences.put("cleanliness", 80);

        String sql = "UPDATE [dbo].[Preferences] SET cleanliness = ? WHERE usersId = ?";
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(GeneralException.class, () -> {
            preferenceDao.updatePreference(preferences);
        });

        assertEquals("Error updating user preferences: Database error", exception.getMessage());
    }*/
//
//    @Test
//    public void testInsertPreference_Success() throws Exception {
//        int userId = 1;
//        String sql = "INSERT INTO [dbo].[Preferences] ([usersId]) VALUES (?)";
//        when(connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
//        when(preparedStatement.executeUpdate()).thenReturn(1); // simulate 1 row affected
//
//        int affectedRows = preferenceDao.insertPreference(userId);
//
//        assertEquals(1, affectedRows);
//    }
//
//    @Test
//    public void testInsertPreference_SQLException() throws Exception {
//        int userId = 1;
//        String sql = "INSERT INTO [dbo].[Preferences] ([usersId]) VALUES (?)";
//        when(connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
//        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));
//
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            preferenceDao.insertPreference(userId);
//        });
//
//        assertEquals("Error saving preferences to the database: Database error", exception.getMessage());
//    }

    @Test
    public void testGetPreference_NoPreferencesFound() throws Exception {
        int userId = 1;

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the execution of the query to return the result set
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // no rows

        // Call the method under test
        Preference preference = preferenceDao.getPreference(userId);

        // Assert that the returned preference is null
        assertNull(preference);
    }

    @Test
    public void testGetPreference_SQLException() throws Exception {
        int userId = 1;

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Call the method under test and verify that it throws the expected exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            preferenceDao.getPreference(userId);
        });

        // Verify that the exception message is correct
        assertEquals("Error retrieving user preferences from the database", exception.getMessage());
    }

//    @Test
//    public void testListMatchingPreference_Success() throws Exception {
//        int userId = 1;
//
//        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        // Simulate two rows in the result set
//        when(resultSet.next()).thenReturn(true, true, false);
//        when(resultSet.getInt("id")).thenReturn(101, 102);
//        when(resultSet.getInt("cleanliness")).thenReturn(70, 85);
//        when(resultSet.getInt("smoking")).thenReturn(30, 50);
//        when(resultSet.getInt("drinking")).thenReturn(40, 60);
//        when(resultSet.getInt("interaction")).thenReturn(50, 70);
//        when(resultSet.getInt("guests")).thenReturn(10, 15);
//        when(resultSet.getInt("cooking")).thenReturn(20, 25);
//        when(resultSet.getInt("pet")).thenReturn(1, 0);
//        when(resultSet.getInt("usersId")).thenReturn(2, 3);
//
//        List<Preference> preferences = preferenceDao.listMatchingPreference(userId);
//
//        assertNotNull(preferences);
//        assertEquals(2, preferences.size());
//
//        // Check details of the first preference
//        assertEquals(101, preferences.get(0).getId());
//        assertEquals(70, preferences.get(0).getCleanliness());
//        assertEquals(30, preferences.get(0).getSmoking());
//        assertEquals(40, preferences.get(0).getDrinking());
//        assertEquals(50, preferences.get(0).getInteraction());
//        assertEquals(10, preferences.get(0).getGuest());
//        assertEquals(20, preferences.get(0).getCooking());
//        assertEquals(1, preferences.get(0).getPet());
//        assertEquals(2, preferences.get(0).getUserId());
//
//        // Check details of the second preference
//        assertEquals(102, preferences.get(1).getId());
//        assertEquals(85, preferences.get(1).getCleanliness());
//        assertEquals(50, preferences.get(1).getSmoking());
//        assertEquals(60, preferences.get(1).getDrinking());
//        assertEquals(70, preferences.get(1).getInteraction());
//        assertEquals(15, preferences.get(1).getGuest());
//        assertEquals(25, preferences.get(1).getCooking());
//        assertEquals(0, preferences.get(1).getPet());
//        assertEquals(3, preferences.get(1).getUserId());
//    }

    @Test
    public void testListMatchingPreference_NoResults() throws Exception {
        int userId = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Simulate no rows in the result set
        when(resultSet.next()).thenReturn(false);

        List<Preference> preferences = preferenceDao.listMatchingPreference(userId);

        assertNotNull(preferences);
        assertTrue(preferences.isEmpty());
    }

    @Test
    public void testListMatchingPreference_SQLException() throws Exception {
        int userId = 1;

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            preferenceDao.listMatchingPreference(userId);
        });

        assertTrue(exception.getMessage().contains("Error: "));
    }
}
