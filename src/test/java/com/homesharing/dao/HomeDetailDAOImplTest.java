package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.HomeDetailDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeDetailDAOImplTest {
    private HomeDetailDAOImpl homeDetailDAO;

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
        homeDetailDAO = new HomeDetailDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    public void testGetHomeById_Success() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 1;
        Home expectedHome = new Home();
        expectedHome.setId(homeId);
        expectedHome.setName("Beautiful Home");
        expectedHome.setAddress("123 Main St");
        expectedHome.setArea(BigDecimal.valueOf(120.5));
        expectedHome.setCreatedDate(LocalDateTime.now());

        String sql = "SELECT [id], [name], [address], [area], [createdDate] FROM [dbo].[Homes] WHERE [id] = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(expectedHome.getId());
        when(resultSet.getString("name")).thenReturn(expectedHome.getName());
        when(resultSet.getString("address")).thenReturn(expectedHome.getAddress());
        when(resultSet.getBigDecimal("area")).thenReturn(expectedHome.getArea());
        when(resultSet.getTimestamp("createdDate")).thenReturn(Timestamp.valueOf(expectedHome.getCreatedDate()));

        Home home = homeDetailDAO.getHomeById(homeId);

        assertEquals(expectedHome.getId(), home.getId());
        assertEquals(expectedHome.getName(), home.getName());
        assertEquals(expectedHome.getAddress(), home.getAddress());
        assertEquals(expectedHome.getArea(), home.getArea());
        assertNotNull(home.getCreatedDate());
    }

    @Test
    public void testGetHomeById_NoResults() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 2;

        String sql = "SELECT [id], [name], [address], [area], [createdDate] FROM [dbo].[Homes] WHERE [id] = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Home home = homeDetailDAO.getHomeById(homeId);

        assertNull(home);
    }

    @Test
    public void testGetHomeById_SQLException() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 6;

        String sql = "SELECT [id], [name], [address], [area], [createdDate] FROM [dbo].[Homes] WHERE [id] = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            homeDetailDAO.getHomeById(homeId);
        });

        assertTrue(exception.getMessage().contains("Error retrieving home by ID from the database"));
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void testGetHomePricesByHomeId_Success() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 1;
        List<Price> expectedPrices = new ArrayList<>();
        Price price1 = new Price();
        price1.setId(1);
        price1.setPrice(2000);
        price1.setCreatedDate(LocalDateTime.now());
        expectedPrices.add(price1);

        String sql = "SELECT [id], [price], [createdDate] FROM [Prices] WHERE [Homesid] = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(price1.getId());
        when(resultSet.getInt("price")).thenReturn(price1.getPrice());
        when(resultSet.getTimestamp("createdDate")).thenReturn(Timestamp.valueOf(price1.getCreatedDate()));

        List<Price> prices = homeDetailDAO.getHomePricesByHomeId(homeId);

        assertEquals(1, prices.size());
        assertEquals(price1.getId(), prices.get(0).getId());
        assertEquals(price1.getPrice(), prices.get(0).getPrice());
    }

    @Test
    public void testGetHomePricesByHomeId_NoResults() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 2;

        String sql = "SELECT [id], [price], [createdDate] FROM [Prices] WHERE [Homesid] = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Price> prices = homeDetailDAO.getHomePricesByHomeId(homeId);

        assertTrue(prices.isEmpty());
    }

    @Test
    public void testGetHomePricesByHomeId_SQLException() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 6;

        String sql = "SELECT [id], [price], [createdDate] FROM [Prices] WHERE [Homesid] = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            homeDetailDAO.getHomePricesByHomeId(homeId);
        });

        assertTrue(exception.getMessage().contains("Error retrieving home prices from the database"));
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void testGetCreatorByHomeId_Success() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 1;
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstName("John");
        expectedUser.setLastName("Doe");
        expectedUser.setEmail("john.doe@example.com");
        expectedUser.setPhoneNumber("123456789");

        String sql = "SELECT u.id, u.firstName, u.lastName, u.email, u.phoneNumber " +
                "FROM [HSS_Users] u " +
                "JOIN Homes h ON u.id = h.createdBy " +
                "WHERE h.id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(expectedUser.getId());
        when(resultSet.getString("firstName")).thenReturn(expectedUser.getFirstName());
        when(resultSet.getString("lastName")).thenReturn(expectedUser.getLastName());
        when(resultSet.getString("email")).thenReturn(expectedUser.getEmail());
        when(resultSet.getString("phoneNumber")).thenReturn(expectedUser.getPhoneNumber());

        User user = homeDetailDAO.getCreatorByHomeId(homeId);

        assertEquals(expectedUser.getId(), user.getId());
        assertEquals(expectedUser.getFirstName(), user.getFirstName());
        assertEquals(expectedUser.getLastName(), user.getLastName());
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getPhoneNumber(), user.getPhoneNumber());
    }

    @Test
    public void testGetCreatorByHomeId_NoResults() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 2;

        String sql = "SELECT u.id, u.firstName, u.lastName, u.email, u.phoneNumber " +
                "FROM [HSS_Users] u " +
                "JOIN Homes h ON u.id = h.createdBy " +
                "WHERE h.id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        User user = homeDetailDAO.getCreatorByHomeId(homeId);

        assertNull(user);
    }

    @Test
    public void testGetCreatorByHomeId_SQLException() throws SQLException, IOException, ClassNotFoundException {
        int homeId = 6;

        String sql = "SELECT u.id, u.firstName, u.lastName, u.email, u.phoneNumber " +
                "FROM [HSS_Users] u " +
                "JOIN Homes h ON u.id = h.createdBy " +
                "WHERE h.id = ?";
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        GeneralException exception = assertThrows(GeneralException.class, () -> {
            homeDetailDAO.getCreatorByHomeId(homeId);
        });

        assertTrue(exception.getMessage().contains("Error retrieving creator by home ID from the database"));
        verify(preparedStatement).executeQuery();
    }
}
