package com.homesharing.dao;


import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PriceDAOImplTest {
    private PriceDAOImpl priceDAO;
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

        priceDAO = new PriceDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    void testGetPrice_Success() throws SQLException {
        // Create sample Home list
        List<Home> homes = new ArrayList<>();
        Home home1 = new Home();
        home1.setPriceId(1);
        homes.add(home1);

        Home home2 = new Home();
        home2.setPriceId(2);
        homes.add(home2);

        // Define SQL behavior
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Define ResultSet behavior
        when(resultSet.next()).thenReturn(true, true, false); // Two results
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getInt("price")).thenReturn(1000, 2000);
        when(resultSet.getInt("Homesid")).thenReturn(10, 20);

        // Call the method
        List<Price> prices = priceDAO.getPrice(homes);

        // Validate results
        assertEquals(2, prices.size());
        assertEquals(1, prices.get(0).getId());
        assertEquals(1000, prices.get(0).getPrice());
        assertEquals(10, prices.get(0).getHomesId());

        assertEquals(2, prices.get(1).getId());
        assertEquals(2000, prices.get(1).getPrice());
        assertEquals(20, prices.get(1).getHomesId());

        // Verify SQL statement was executed
        Mockito.verify(preparedStatement, times(1)).executeQuery();
    }

}
