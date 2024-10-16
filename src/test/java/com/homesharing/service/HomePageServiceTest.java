package com.homesharing.service;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.User;
import org.junit.jupiter.api.Test;
import com.homesharing.service.impl.HomePageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
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
    @Mock
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO,userDAO);
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
    void testGetNewHomes_Success() throws GeneralException {
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
    void testGetNewHomes_Exception() throws GeneralException {
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

    @Test
    void testGetMatchingHome_Success() throws GeneralException {
        // Arrange
        int[] matchingHostsId = {1, 2, 3};
        int userId = 1;

        // Mock homeDAO to return a list of homes
        List<Home> homeList = new ArrayList<>();
        Home home1 = new Home();
        home1.setMoveInDate(LocalDate.of(2024, 1, 5));
        home1.setLeaseDuration(6);
        homeList.add(home1);

        Home home2 = new Home();
        home2.setMoveInDate(LocalDate.of(2024, 3, 10));
        home2.setLeaseDuration(8);
        homeList.add(home2);

        when(homeDAO.getMatchingHomes(matchingHostsId)).thenReturn(homeList);

        // Mock priceDAO to return corresponding prices
        List<Price> priceList = new ArrayList<>();
        Price price1 = new Price();
        price1.setPrice(1000);
        priceList.add(price1);

        Price price2 = new Price();
        price2.setPrice(1200);
        priceList.add(price2);

        when(priceDAO.getPrices(homeList)).thenReturn(priceList);

        // Mock userDAO to return a user profile with budget and move-in dates
        User user = new User();
        user.setMaxBudget(1500);
        user.setDuration("long");
        user.setEarliestMoveIn(LocalDate.of(2024, 1, 1));
        user.setLatestMoveIn(LocalDate.of(2024, 12, 31));

        when(userDAO.getMatchingUserProfile(userId)).thenReturn(user);

        // Act
        List<Home> actualMatchingHomes = homePageService.getMatchingHome(matchingHostsId, userId);

        // Assert
        assertNotNull(actualMatchingHomes);
        assertEquals(2, actualMatchingHomes.size());
        assertEquals(home1, actualMatchingHomes.get(0));
        assertEquals(home2, actualMatchingHomes.get(1));

        verify(homeDAO, times(1)).getMatchingHomes(matchingHostsId);
        verify(priceDAO, times(1)).getPrices(homeList);
        verify(userDAO, times(1)).getMatchingUserProfile(userId);
    }

    @Test
    void testGetMatchingHome_NoMatchingHomes() throws GeneralException {
        // Arrange
        int[] matchingHostsId = {1, 2, 3};
        int userId = 1;

        // Mock homeDAO to return an empty list
        when(homeDAO.getMatchingHomes(matchingHostsId)).thenReturn(new ArrayList<>());

        // Act
        List<Home> actualMatchingHomes = homePageService.getMatchingHome(matchingHostsId, userId);

        // Assert
        assertNull(actualMatchingHomes); // Should return null if no homes are available
        verify(homeDAO, times(1)).getMatchingHomes(matchingHostsId);
        verify(priceDAO, never()).getPrices(anyList());
        verify(userDAO, never()).getMatchingUserProfile(anyInt());
    }

    @Test
    void testGetMatchingHome_PriceOutOfBudget() throws GeneralException {
        // Arrange
        int[] matchingHostsId = {1, 2};
        int userId = 1;

        // Mock homeDAO to return a list with one home
        List<Home> homeList = new ArrayList<>();
        Home home = new Home();
        home.setMoveInDate(LocalDate.of(2024, 2, 15));
        home.setLeaseDuration(12);
        homeList.add(home);

        when(homeDAO.getMatchingHomes(matchingHostsId)).thenReturn(homeList);

        // Mock priceDAO to return a price higher than the user's budget
        List<Price> priceList = new ArrayList<>();
        Price price = new Price();
        price.setPrice(2000); // User budget is lower than this
        priceList.add(price);

        when(priceDAO.getPrices(homeList)).thenReturn(priceList);

        // Mock userDAO to return a user profile
        User user = new User();
        user.setMaxBudget(1500); // User budget is 1500
        user.setDuration("long");
        user.setEarliestMoveIn(LocalDate.of(2024, 1, 1));
        user.setLatestMoveIn(LocalDate.of(2024, 12, 31));

        when(userDAO.getMatchingUserProfile(userId)).thenReturn(user);

        // Act
        List<Home> actualMatchingHomes = homePageService.getMatchingHome(matchingHostsId, userId);

        // Assert
        assertNotNull(actualMatchingHomes);
        assertTrue(actualMatchingHomes.isEmpty()); // No homes should be added as the price exceeds the user's budget
    }

    @Test
    void testGetMatchingHome_ExceptionHandling() throws GeneralException {
        // Arrange
        int[] matchingHostsId = {1, 2, 3};
        int userId = 1;

        // Mock homeDAO to throw a RuntimeException
        when(homeDAO.getMatchingHomes(matchingHostsId)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> homePageService.getMatchingHome(matchingHostsId, userId));
        assertEquals("Database error", thrown.getMessage());

        verify(homeDAO, times(1)).getMatchingHomes(matchingHostsId);
        verify(priceDAO, never()).getPrices(anyList());
        verify(userDAO, never()).getMatchingUserProfile(anyInt());
    }

}