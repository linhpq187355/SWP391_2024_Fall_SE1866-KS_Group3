package com.homesharing.dao;


import com.homesharing.conf.DBContext;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.User;
import com.homesharing.util.PasswordUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserDAOImplTest {

    private UserDAOImpl userDAO;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private MockedStatic<DBContext> mockedDBContext;
    private MockedStatic<PasswordUtil> mockedPasswordUtil;

    @BeforeEach
    public void setUp() {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        // Mock DBContext.getConnection() to return the mock connection
        mockedDBContext = Mockito.mockStatic(DBContext.class);
        mockedDBContext.when(DBContext::getConnection).thenReturn(connection);
        userDAO = new UserDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        // Close static mock after each test
        mockedDBContext.close();
    }

    @Test
    void testSaveUser() throws SQLException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setRolesId(1);
        user.setStatus("active");
        user.setHashedPassword("hashedPassword");

        when(connection.prepareStatement(any(String.class), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(5);

        int userId = userDAO.saveUser(user);
        assertEquals(5, userId);
    }

    @Test
    void testEmailExists() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        boolean exists = userDAO.emailExists("john@example.com");
        assertTrue(exists);
    }

    @Test
    void testGetUser() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("email")).thenReturn("john@example.com");

        User user = userDAO.getUser(1);
        assertNotNull(user);
        assertEquals(1, user.getId());
    }

    @Test
    void testFindUserByEmail() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("email")).thenReturn("john@example.com");
        when(resultSet.getString("firstName")).thenReturn("John");
        when(resultSet.getString("lastName")).thenReturn("Doe");
        when(resultSet.getInt("Rolesid")).thenReturn(1);
        when(resultSet.getString("status")).thenReturn("active");
        when(resultSet.getString("hashedPassword")).thenReturn("hashedPassword");

        when(resultSet.getTimestamp("createdAt")).thenReturn(null);

        User user = userDAO.findUserByEmail("john@example.com");
        assertNotNull(user);
        assertEquals("john@example.com", user.getEmail());
        assertNull(user.getCreatedAt());
    }

    @Test
    void testSaveUserThrowsException() throws SQLException {
        when(connection.prepareStatement(any(String.class), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenThrow(SQLException.class);
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        assertThrows(GeneralException.class, () -> userDAO.saveUser(user));
    }

    @Test
    public void testUpdateUserProfile_Success() throws SQLException {
        // Set up user data
        User user = new User();
        user.setId(1);
        user.setAddress("123 Street");
        user.setGender("Male");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAvatar("avatar.png");
        user.setDob(LocalDate.of(1990, 1, 1));

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the execution of the update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Call the method under test
        int rowsUpdated = userDAO.updateUserProfile(user);

        // Verify that the statement was executed with correct parameters
        verify(preparedStatement).setString(1, user.getAddress());
        verify(preparedStatement).setString(2, user.getGender());
        verify(preparedStatement).setString(3, user.getFirstName());
        verify(preparedStatement).setString(4, user.getLastName());
        verify(preparedStatement).setString(5, user.getAvatar());
        verify(preparedStatement).setDate(6, Date.valueOf(user.getDob()));
        verify(preparedStatement).setInt(7, user.getId());

        // Assert that the number of rows updated is correct
        assertEquals(1, rowsUpdated);
    }

    @Test
    public void testUpdateUserProfile_SQLException() throws SQLException {
        // Set up user data
        User user = new User();
        user.setId(1);
        user.setAddress("123 Street");
        user.setGender("Male");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAvatar("avatar.png");
        user.setDob(LocalDate.of(1990, 1, 1));

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the behavior to throw an SQLException
        doThrow(new SQLException("Database error")).when(preparedStatement).executeUpdate();

        // Assert that the method throws a GeneralException
        assertThrows(GeneralException.class, () -> {
            userDAO.updateUserProfile(user);
        });
    }

    @Test
    public void testGetUserAvatar_Success() throws SQLException {
        // Set up the user ID for which the avatar is to be retrieved
        int userId = 1;
        String expectedAvatar = "avatar.png";

        // Mock the behavior of prepareStatement to return the preparedStatement
        String sql = "SELECT u.avatar FROM [HSS_Users] u WHERE u.id = ?";
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the execution of the query
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("avatar")).thenReturn(expectedAvatar);

        // Call the method under test
        String actualAvatar = userDAO.getUserAvatar(userId);

        // Verify that the statement was executed with the correct parameter
        verify(preparedStatement).setInt(1, userId);

        // Assert that the returned avatar is correct
        assertEquals(expectedAvatar, actualAvatar);
    }

    @Test
    public void testGetUserAvatar_SQLException() throws SQLException {
        // Set up the user ID for which the avatar is to be retrieved
        int userId = 1;

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the execution of the query to throw an SQLException
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Call the method under test and verify that it throws the expected exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userDAO.getUserAvatar(userId);
        });

        // Verify that the exception message is correct
        assertEquals("Error retrieving user avatar from the database", exception.getMessage());

        // Verify that the statement was executed with the correct parameter
        verify(preparedStatement).setInt(1, userId);
    }


    @Test
    public void testResetPassword_Success() throws SQLException {
        // Set up user data
        String newPassword = "newPassword123";
        int userId = 1;

        // Mock the behavior of prepareStatement to return the preparedStatement
        String sql = "UPDATE [dbo].[HSS_Users] SET [hashedPassword] = ? WHERE id = ?";
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock PasswordUtil to return a hashed password
        mockedPasswordUtil = Mockito.mockStatic(PasswordUtil.class);
        mockedPasswordUtil.when(() -> PasswordUtil.hashPassword(any(String.class))).thenReturn("hashedPassword123");

        // Mock the execution of the update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Call the method under test
        int rowsUpdated = userDAO.resetPassword(newPassword, userId);

        // Verify that the statement was executed with correct parameters
        verify(preparedStatement).setString(1, "hashedPassword123");
        verify(preparedStatement).setInt(2, userId);

        // Assert that the number of rows updated is correct
        assertEquals(1, rowsUpdated);
    }

    @Test
    public void testResetPassword_SQLException() throws SQLException {
        // Set up user data
        String newPassword = "newPassword123";
        int userId = 1;

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock PasswordUtil to return a hashed password
        mockedPasswordUtil = Mockito.mockStatic(PasswordUtil.class);
        mockedPasswordUtil.when(() -> PasswordUtil.hashPassword(any(String.class))).thenReturn("hashedPassword123");

        // Mock the execution of the update to throw an SQLException
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database update error"));

        // Call the method under test and verify that it throws the expected exception
        Exception exception = assertThrows(GeneralException.class, () -> {
            userDAO.resetPassword(newPassword, userId);
        });

        // Verify that the exception message is correct
        assertEquals("Error updating user password: Database update error", exception.getMessage());

        // Verify that the statement was executed with correct parameters
        verify(preparedStatement).setString(1, "hashedPassword123");
        verify(preparedStatement).setInt(2, userId);
    }

}