package com.homesharing.dao;


import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PriceDAOImplTest {
    private PriceDAOImpl priceDAO;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private MockedStatic<DBContext> mockedDBContext;

    @BeforeEach
    public void setUp() throws SQLException, IOException, ClassNotFoundException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        // Mock DBContext.getConnection() to return the mock connection
        mockedDBContext = Mockito.mockStatic(DBContext.class);
        mockedDBContext.when(DBContext::getConnection).thenReturn(connection);
        priceDAO = new PriceDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

//    @Test
//    public void testGetPrices_Success() throws Exception {
//        // Set up mock data
//        Home home1 = new Home();
//        home1.setPriceId(1);
//        Home home2 = new Home();
//        home2.setPriceId(2);
//        List<Home> homes = List.of(home1, home2);
//
//        // Mock the behavior of prepareStatement to return the preparedStatement
//        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
//
//        // Mock the execution of the query to return the result set
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        // Mock the result set to return two rows of prices
//        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false); // two rows
//        when(resultSet.getInt("Homesid")).thenReturn(1).thenReturn(2);
//        when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
//        when(resultSet.getInt("price")).thenReturn(100).thenReturn(200);
//
//        // Call the method under test
//        List<Price> prices = priceDAO.getPrices(homes);
//
//        // Assertions to verify the expected output
//        assertEquals(2, prices.size());
//        assertEquals(1, prices.get(0).getHomesId());
//        assertEquals(1, prices.get(0).getId());
//        assertEquals(100, prices.get(0).getPrice());
//        assertEquals(2, prices.get(1).getHomesId());
//        assertEquals(2, prices.get(1).getId());
//        assertEquals(200, prices.get(1).getPrice());
//
//        // Verify that the statement was executed with the correct parameters
//        verify(preparedStatement).setInt(1, home1.getPriceId());
//        verify(preparedStatement).setInt(2, home2.getPriceId());
//    }

    @Test
    public void testGetPrices_EmptyHomeList() {
        // Call the method with an empty list
        List<Price> prices = priceDAO.getPrices(new ArrayList<>());

        // Assert that the returned list is empty
        assertTrue(prices.isEmpty());
    }

//    @Test
//    public void testGetPrices_SQLException() throws Exception {
//        // Set up mock data
//        Home home1 = new Home();
//        home1.setPriceId(1);
//        List<Home> homes = List.of(home1);
//
//        // Mock the behavior of prepareStatement to return the preparedStatement
//        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
//
//        // Mock the execution of the query to throw an SQLException
//        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));
//
//        // Call the method under test and verify that it throws the expected exception
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            priceDAO.getPrices(homes);
//        });
//
//        // Verify that the exception message is correct
//        assertEquals("Error retrieving prices from the database: Database error", exception.getMessage());
//
//        // Verify that the statement was executed with the correct parameter
//        verify(preparedStatement).setInt(1, home1.getPriceId());
//    }

    @Test
    void testGetMinPrice_ReturnsMinPrice() throws Exception {
        // Arrange
        int expectedMinPrice = 2;

        // Mock result set to return the expected value
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("price")).thenReturn(expectedMinPrice);

        // Act
        int actualMinPrice = priceDAO.getMinPrice();

        // Assert
        assertEquals(expectedMinPrice, actualMinPrice);

        // Verify interactions
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt("price");
    }

    @Test
    void testGetMinPrice_ReturnZero_WhenNoResult() throws Exception {
        int expectedMinPrice = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        int actualMinPrice = priceDAO.getMinPrice();
        assertEquals(expectedMinPrice, actualMinPrice);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetMinPrice_ThrowsGeneralException_WhenSQLException() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> priceDAO.getMinPrice());
        assertEquals("Error get min price in the database: Database error", exception.getMessage());
    }

    @Test
    void testGetMaxPrice_ReturnsMaxPrice() throws Exception {
        // Arrange
        int expectedMaxPrice = 2;

        // Mock result set to return the expected value
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("price")).thenReturn(expectedMaxPrice);

        // Act
        int actualMinPrice = priceDAO.getMaxPrice();

        // Assert
        assertEquals(expectedMaxPrice, actualMinPrice);

        // Verify interactions
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getInt("price");
    }

    @Test
    void testGetMaxPrice_ReturnZero_WhenNoResult() throws Exception {
        int expectedMaxPrice = 0;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        int actualMinPrice = priceDAO.getMaxPrice();
        assertEquals(expectedMaxPrice, actualMinPrice);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetMaxPrice_ThrowsGeneralException_WhenSQLException() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> priceDAO.getMaxPrice());
        assertEquals("Error get max price in the database: Database error", exception.getMessage());
    }


}
