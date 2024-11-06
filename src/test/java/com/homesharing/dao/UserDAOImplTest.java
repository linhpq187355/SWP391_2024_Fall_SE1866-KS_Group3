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
    void testGetUserAvatar_SQLException() throws SQLException {
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

    @Test
    public void testUpdateMatchingProfile_Success() throws SQLException {
        // Set up user data
        User user = new User();
        user.setId(1);
        user.setDob(LocalDate.of(1990, 1, 1));
        user.setGender("Male");
        user.setDuration("6 months");
        user.setMinBudget(500);
        user.setMaxBudget(1000);
        user.setEarliestMoveIn(LocalDate.of(2024, 1, 15));
        user.setLatestMoveIn(LocalDate.of(2024, 3, 15));

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the execution of the update
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Call the method under test
        int rowsUpdated = userDAO.updateMatchingProfile(user);

        // Verify that the statement was executed with correct parameters
        verify(preparedStatement).setDate(1, java.sql.Date.valueOf(user.getDob()));
        verify(preparedStatement).setString(2, user.getGender());
        verify(preparedStatement).setString(3, user.getDuration());
        verify(preparedStatement).setInt(4, user.getMinBudget());
        verify(preparedStatement).setInt(5, user.getMaxBudget());
        verify(preparedStatement).setDate(6, java.sql.Date.valueOf(user.getEarliestMoveIn()));
        verify(preparedStatement).setDate(7, java.sql.Date.valueOf(user.getLatestMoveIn()));
        verify(preparedStatement).setInt(8, user.getId());

        // Assert that the number of rows updated is correct
        assertEquals(1, rowsUpdated);
    }

    @Test
    public void testUpdateMatchingProfile_SQLException() throws SQLException {
        // Set up user data
        User user = new User();
        user.setId(1);
        user.setDob(LocalDate.of(1990, 1, 1));
        user.setGender("Male");
        user.setDuration("6 months");
        user.setMinBudget(500);
        user.setMaxBudget(1000);
        user.setEarliestMoveIn(LocalDate.of(2024, 1, 15));
        user.setLatestMoveIn(LocalDate.of(2024, 3, 15));

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the behavior to throw an SQLException
        doThrow(new SQLException("Database update error")).when(preparedStatement).executeUpdate();

        // Call the method under test and verify that it throws the expected exception
        Exception exception = assertThrows(GeneralException.class, () -> {
            userDAO.updateMatchingProfile(user);
        });

        // Verify that the exception message is correct
        assertEquals("Error updating user matching profile: Database update error", exception.getMessage());

        // Verify that the statement was executed with correct parameters
        verify(preparedStatement).setDate(1, java.sql.Date.valueOf(user.getDob()));
        verify(preparedStatement).setString(2, user.getGender());
        verify(preparedStatement).setString(3, user.getDuration());
        verify(preparedStatement).setInt(4, user.getMinBudget());
        verify(preparedStatement).setInt(5, user.getMaxBudget());
        verify(preparedStatement).setDate(6, java.sql.Date.valueOf(user.getEarliestMoveIn()));
        verify(preparedStatement).setDate(7, java.sql.Date.valueOf(user.getLatestMoveIn()));
        verify(preparedStatement).setInt(8, user.getId());
    }

    @Test
    public void testGetMatchingUserProfile_Success() throws SQLException {
        // Set up the user ID and expected user data
        int userId = 1;
        int minBudget = 500;
        int maxBudget = 1500;
        String duration = "6 months";
        LocalDate earliestMoveIn = LocalDate.of(2024, 1, 1);
        LocalDate latestMoveIn = LocalDate.of(2024, 12, 31);

        // Mock the behavior of prepareStatement to return the preparedStatement
        String sql = "SELECT [id], [minBudget], [maxBudget], [earliestMoveIn], [latestMoveIn], [duration] FROM [dbo].[HSS_Users] WHERE [id] = ?";
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the execution of the query
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(userId);
        when(resultSet.getInt("minBudget")).thenReturn(minBudget);
        when(resultSet.getInt("maxBudget")).thenReturn(maxBudget);
        when(resultSet.getString("duration")).thenReturn(duration);
        when(resultSet.getDate("earliestMoveIn")).thenReturn(Date.valueOf(earliestMoveIn));
        when(resultSet.getDate("latestMoveIn")).thenReturn(Date.valueOf(latestMoveIn));

        // Call the method under test
        User user = userDAO.getMatchingUserProfile(userId);

        // Assert that the returned user is correct
        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals(minBudget, user.getMinBudget());
        assertEquals(maxBudget, user.getMaxBudget());
        assertEquals(duration, user.getDuration());
        assertEquals(earliestMoveIn, user.getEarliestMoveIn());
        assertEquals(latestMoveIn, user.getLatestMoveIn());

        // Verify that the statement was executed with the correct parameter
        verify(preparedStatement).setInt(1, userId);
    }

    @Test
    public void testGetMatchingUserProfile_UserNotFound() throws SQLException {
        // Set up the user ID for which no user is found
        int userId = 1;

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the execution of the query to return an empty result set
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        // Call the method under test
        User user = userDAO.getMatchingUserProfile(userId);

        // Assert that the returned user is null (indicating no user was found)
        assertNull(user);

        // Verify that the statement was executed with the correct parameter
        verify(preparedStatement).setInt(1, userId);
    }

    @Test
    public void testGetMatchingUserProfile_SQLException() throws SQLException {
        // Set up the user ID for which an exception is thrown
        int userId = 1;

        // Mock the behavior of prepareStatement to return the preparedStatement
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Mock the execution of the query to throw an SQLException
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database query error"));

        // Call the method under test and verify that it throws the expected exception
        Exception exception = assertThrows(GeneralException.class, () -> {
            userDAO.getMatchingUserProfile(userId);
        });

        // Verify that the exception message is correct
        assertEquals("Error gettinh user matching profile: Database query error", exception.getMessage());

        // Verify that the statement was executed with the correct parameter
        verify(preparedStatement).setInt(1, userId);
    }


    @Test
    void testUpdateGoogleId_Success() throws SQLException {
        // Arrange
        String googleId = "newGoogleId";
        String email = "test@example.com";
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Giả lập có một hàng được cập nhật

        // Act
        int result = userDAO.updateGoogleId(googleId, email);

        // Assert
        assertEquals(1, result); // Kiểm tra số hàng đã cập nhật
        verify(preparedStatement).setString(1, googleId); // Kiểm tra rằng googleId đã được thiết lập đúng
        verify(preparedStatement).setString(2, email); // Kiểm tra rằng email đã được thiết lập đúng
        verify(preparedStatement).executeUpdate(); // Kiểm tra phương thức executeUpdate đã được gọi
    }

    @Test
    void testUpdateGoogleId_SQLException() throws SQLException {
        // Arrange
        String googleId = "newGoogleId";
        String email = "test@example.com";
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> {
            userDAO.updateGoogleId(googleId, email);
        });

        assertTrue(exception.getMessage().contains("Error updating user googleId: Database error")); // Kiểm tra thông điệp lỗi
    }

    @Test
    void testUpdateGoogleId_ResourceClosing() throws SQLException {
        // Arrange
        String googleId = "newGoogleId";
        String email = "test@example.com";
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act
        userDAO.updateGoogleId(googleId, email);

        // Assert
        verify(preparedStatement).close(); // Kiểm tra rằng preparedStatement đã được đóng
        verify(connection).close(); // Kiểm tra rằng connection đã được đóng
    }

    @Test
    void testGetGoogleId_Success() throws SQLException {
        // Arrange
        String email = "test@example.com";
        String expectedGoogleId = "testGoogleId";
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Giả lập có kết quả
        when(resultSet.getString("googleID")).thenReturn(expectedGoogleId);

        // Act
        String googleId = userDAO.getGoogleId(email);

        // Assert
        assertEquals(expectedGoogleId, googleId); // Kiểm tra kết quả trả về
        verify(preparedStatement).setString(1, email); // Kiểm tra rằng email đã được thiết lập đúng
        verify(preparedStatement).executeQuery(); // Kiểm tra phương thức executeQuery đã được gọi
    }

    @Test
    void testGetGoogleId_NoUserFound() throws SQLException {
        // Arrange
        String email = "test@example.com";
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Giả lập không có kết quả

        // Act
        String googleId = userDAO.getGoogleId(email);

        // Assert
        assertNull(googleId); // Kiểm tra rằng kết quả là null
    }

    @Test
    void testGetGoogleId_SQLException() throws SQLException {
        // Arrange
        String email = "test@example.com";
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> {
            userDAO.getGoogleId(email);
        });

        assertTrue(exception.getMessage().contains("Error finding googleID by email in the database: Database error")); // Kiểm tra thông điệp lỗi
    }

    @Test
    void testGetGoogleId_ResourceClosing() throws SQLException {
        // Arrange
        String email = "test@example.com";
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Giả lập có kết quả
        when(resultSet.getString("googleID")).thenReturn("testGoogleId");

        // Act
        userDAO.getGoogleId(email);

        // Assert
        verify(resultSet).close(); // Kiểm tra rằng ResultSet đã được đóng
        verify(preparedStatement).close(); // Kiểm tra rằng PreparedStatement đã được đóng
        verify(connection).close(); // Kiểm tra rằng Connection đã được đóng
    }

    @Test
    void testUpdateEmail_Success() throws SQLException {
        // Arrange
        String email = "newEmail@example.com";
        int id = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Giả lập có một hàng được cập nhật

        // Act
        int result = userDAO.updateEmail(email, id);

        // Assert
        assertEquals(1, result); // Kiểm tra số hàng đã cập nhật
        verify(preparedStatement).setString(1, email); // Kiểm tra rằng email đã được thiết lập đúng
        verify(preparedStatement).setInt(2, id); // Kiểm tra rằng id đã được thiết lập đúng
        verify(preparedStatement).executeUpdate(); // Kiểm tra phương thức executeUpdate đã được gọi
    }

    @Test
    void testUpdateEmail_SQLException() throws SQLException {
        // Arrange
        String email = "newEmail@example.com";
        int id = 1;
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> {
            userDAO.updateEmail(email, id);
        });

        assertTrue(exception.getMessage().contains("Error updating user profile: Database error")); // Kiểm tra thông điệp lỗi
    }

    @Test
    void testUpdateEmail_ResourceClosing() throws SQLException {
        // Arrange
        String email = "newEmail@example.com";
        int id = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Act
        userDAO.updateEmail(email, id);

        // Assert
        verify(preparedStatement).close(); // Kiểm tra rằng PreparedStatement đã được đóng
        verify(connection).close(); // Kiểm tra rằng Connection đã được đóng
    }

    @Test
    void testPassWordExists_PasswordIsNull() throws SQLException {
        // Arrange
        int userId = 1;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Giả lập có kết quả
        when(resultSet.getInt(1)).thenReturn(1); // Giả lập rằng mật khẩu là null

        // Act
        int result = userDAO.passWordExists(userId);

        // Assert
        assertEquals(1, result); // Kiểm tra rằng kết quả là 1 (mật khẩu là null)
        verify(preparedStatement).setInt(1, userId); // Kiểm tra rằng userId đã được thiết lập đúng
        verify(preparedStatement).executeQuery(); // Kiểm tra rằng executeQuery đã được gọi
    }

    @Test
    void testPassWordExists_PasswordExists() throws SQLException {
        // Arrange
        int userId = 2;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Giả lập có kết quả
        when(resultSet.getInt(1)).thenReturn(0); // Giả lập rằng mật khẩu tồn tại

        // Act
        int result = userDAO.passWordExists(userId);

        // Assert
        assertEquals(0, result); // Kiểm tra rằng kết quả là 0 (mật khẩu tồn tại)
    }

    @Test
    void testPassWordExists_NoUserFound() throws SQLException {
        // Arrange
        int userId = 3;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Giả lập không có kết quả

        // Act
        int result = userDAO.passWordExists(userId);

        // Assert
        assertEquals(-1, result); // Kiểm tra rằng kết quả là -1 (không có người dùng)
    }

    @Test
    void testPassWordExists_SQLException() throws SQLException {
        // Arrange
        int userId = 4;
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(GeneralException.class, () -> {
            userDAO.passWordExists(userId);
        });

        assertTrue(exception.getMessage().contains("Error checking email existence in the database")); // Kiểm tra thông điệp lỗi
    }

    @Test
    void testPassWordExists_ResourceClosing() throws SQLException {
        // Arrange
        int userId = 5;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Giả lập có kết quả
        when(resultSet.getInt(1)).thenReturn(-1); // Giả lập rằng mật khẩu là null

        // Act
        userDAO.passWordExists(userId);

        // Assert
        verify(resultSet).close(); // Kiểm tra rằng ResultSet đã được đóng
        verify(preparedStatement).close(); // Kiểm tra rằng PreparedStatement đã được đóng
        verify(connection).close(); // Kiểm tra rằng Connection đã được đóng
    }

    
}