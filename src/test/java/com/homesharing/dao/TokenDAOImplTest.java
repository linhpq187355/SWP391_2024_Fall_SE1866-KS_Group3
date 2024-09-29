package com.homesharing.dao;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TokenDAOImplTest {

    private TokenDAOImpl tokenDAO;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private MockedStatic<DBContext> mockedDBContext;

    @BeforeEach
    public void setUp() {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        mockedDBContext = Mockito.mockStatic(DBContext.class);
        mockedDBContext.when(DBContext::getConnection).thenReturn(connection);
        tokenDAO = new TokenDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    void testInsertToken() throws SQLException {
        Token token = new Token(1, "123456", LocalDateTime.now(), false);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        tokenDAO.insertToken(token); // Call method to test

        verify(preparedStatement).setInt(1, token.getUserId());
        verify(preparedStatement).setString(2, token.getToken());
        verify(preparedStatement).setTimestamp(3, Timestamp.valueOf(token.getRequestedTime()));
        verify(preparedStatement).setBoolean(4, token.isVerified());
        verify(preparedStatement).executeUpdate(); // Ensure executeUpdate was called
    }

    @Test
    void testFindToken() throws SQLException {
        Token expectedToken = new Token(1, "123456", LocalDateTime.now(), false);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("otp")).thenReturn(expectedToken.getToken());
        when(resultSet.getTimestamp("requestedTime")).thenReturn(Timestamp.valueOf(expectedToken.getRequestedTime()));
        when(resultSet.getBoolean("isVerified")).thenReturn(expectedToken.isVerified());

        Token actualToken = tokenDAO.findToken(1); // Call method to test

        assertNotNull(actualToken);
        assertEquals(expectedToken.getUserId(), actualToken.getUserId());
        assertEquals(expectedToken.getToken(), actualToken.getToken());
        assertEquals(expectedToken.getRequestedTime(), actualToken.getRequestedTime());
        assertEquals(expectedToken.isVerified(), actualToken.isVerified());
    }

    @Test
    void testFindTokenReturnsNull() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // No token found

        Token actualToken = tokenDAO.findToken(1); // Call method to test

        assertNull(actualToken); // Expect null since no token is found
    }

    @Test
    void testUpdateTokenVerification() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful update

        tokenDAO.updateTokenVerification(1); // Call method to test

        verify(preparedStatement).setBoolean(1, true);
        verify(preparedStatement).setInt(2, 1);
        verify(preparedStatement).executeUpdate(); // Ensure executeUpdate was called
    }

    @Test
    void testUpdateTokenVerificationThrowsException() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0); // Simulate no rows affected

        assertThrows(GeneralException.class, () -> tokenDAO.updateTokenVerification(1));
    }
}
