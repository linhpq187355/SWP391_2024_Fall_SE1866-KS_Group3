package com.homesharing.service;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import org.junit.jupiter.api.Test;
import com.homesharing.service.impl.HomePageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HomePageServiceTest {
    @InjectMocks
    private HomePageServiceImpl homePageService;

    @Mock
    private HomeDAO homeDAO;

    @Mock
    private PriceDAO priceDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO);
    }

    @Test
    void testGetHomes() {
        // Arrange
        List<Home> expectedHomes = new ArrayList<>();
        when(homeDAO.getAllHomes()).thenReturn(expectedHomes);

        // Act
        List<Home> actualHomes = homePageService.getHomes();

        // Assert
        assertEquals(expectedHomes, actualHomes);
        verify(homeDAO, times(1)).getAllHomes();
    }

    @Test
    void testGetNewHomes_Success() throws GeneralException, SQLException, IOException, ClassNotFoundException {
        // Arrange
        List<Home> expectedNewHomes = new ArrayList<>();
        expectedNewHomes.add(new Home()); // Thêm một đối tượng Home giả định vào danh sách
        when(homeDAO.getNewHomes()).thenReturn(expectedNewHomes); // Mô phỏng phương thức

        // Act
        List<Home> actualNewHomes = homePageService.getNewHomes();

        // Assert
        assertEquals(expectedNewHomes, actualNewHomes);
        verify(homeDAO, times(1)).getNewHomes(); // Xác nhận rằng phương thức đã được gọi một lần
    }

    @Test
    void testGetNewHomes_Exception() throws GeneralException, SQLException, IOException, ClassNotFoundException {
        // Arrange
        when(homeDAO.getNewHomes()).thenThrow(new GeneralException("Database error"));

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> homePageService.getNewHomes());
        assertEquals("Service layer: Unable to retrieve new homes", thrown.getMessage());
    }

    @Test
    void testGetHomePrice_ValidInput() {
        // Arrange
        List<Home> homes = new ArrayList<>();
        List<Price> expectedPrices = new ArrayList<>();
        homes.add(new Home()); // Add a sample Home object
        when(priceDAO.getPrices(homes)).thenReturn(expectedPrices);

        // Act
        List<Price> actualPrices = homePageService.getHomePrice(homes);

        // Assert
        assertEquals(expectedPrices, actualPrices);
        verify(priceDAO, times(1)).getPrices(homes);
    }

    @Test
    void testGetHomePrice_EmptyInput() {
        // Act
        List<Price> actualPrices = homePageService.getHomePrice(new ArrayList<>());

        // Assert
        assertTrue(actualPrices.isEmpty());
        verify(priceDAO, never()).getPrices(anyList());
    }

    @Test
    void testGetHomePrice_NullInput() {
        // Act
        List<Price> actualPrices = homePageService.getHomePrice(null);

        // Assert
        assertTrue(actualPrices.isEmpty());
        verify(priceDAO, never()).getPrices(anyList());
    }

    @Test
    void testGetHomePrice_Exception() {
        // Arrange
        List<Home> homes = new ArrayList<>();
        homes.add(new Home());
        when(priceDAO.getPrices(homes)).thenThrow(new RuntimeException("Price retrieval error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> homePageService.getHomePrice(homes));
        assertEquals("Error retrieving prices from the database: Price retrieval error", thrown.getMessage());
    }

}