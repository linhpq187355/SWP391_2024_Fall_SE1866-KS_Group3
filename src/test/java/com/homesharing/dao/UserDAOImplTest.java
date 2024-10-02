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
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UserDAOImplTest {

    private UserDAOImpl userDAO;
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
    void testUpdateUserProfile_Success() throws SQLException {
        // Create a mock User object
        User user = new User();
        user.setId(1);
        user.setEmail("john@example.com");
        user.setPhoneNumber("0123456789");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAvatar("avatar.png");
        user.setDob(java.time.LocalDate.of(1990, 1, 1));

        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                "   SET [email] = ?\n" +
                "      ,[phoneNumber] = ?\n" +
                "      ,[firstName] = ?\n" +
                "      ,[lastName] = ?\n" +
                "      ,[avatar] = ?\n" +
                "      ,[dob] = ?\n" +
                "      ,[lastModified] = GETDATE()\n" +
                " WHERE id = ?";

        // Mock the behavior of the connection and prepared statement
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Call the method under test
        int rowsUpdated = userDAO.updateUserProfile(user);

        // Verify the results
        assertEquals(1, rowsUpdated);

        // Verify that the prepared statement was set with the correct parameters
        Mockito.verify(preparedStatement).setString(1, user.getEmail());
        Mockito.verify(preparedStatement).setString(2, user.getPhoneNumber());
        Mockito.verify(preparedStatement).setString(3, user.getFirstName());
        Mockito.verify(preparedStatement).setString(4, user.getLastName());
        Mockito.verify(preparedStatement).setString(5, user.getAvatar());
        Mockito.verify(preparedStatement).setDate(6, Date.valueOf(user.getDob()));
        Mockito.verify(preparedStatement).setInt(7, user.getId());

        // Verify executeUpdate() was called once
        Mockito.verify(preparedStatement, Mockito.times(1)).executeUpdate();
    }

    @Test
    void testUpdateUserProfile_SQLException() throws SQLException {
        // Create a mock User object
        User user = new User();
        user.setId(1);
        user.setEmail("john@example.com");

        String sql = "UPDATE [dbo].[HSS_Users]\n" +
                "   SET [email] = ?\n" +
                "      ,[phoneNumber] = ?\n" +
                "      ,[firstName] = ?\n" +
                "      ,[lastName] = ?\n" +
                "      ,[avatar] = ?\n" +
                "      ,[dob] = ?\n" +
                "      ,[lastModified] = GETDATE()\n" +
                " WHERE id = ?";

        // Mock the behavior of the connection to throw SQLException when executing the update
        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

        // Verify if GeneralException is thrown when SQLException occurs
        GeneralException thrown = assertThrows(GeneralException.class, () -> userDAO.updateUserProfile(user));
        assertEquals("Error update user profile: Database error", thrown.getMessage());
    }

    @Test
    public void testGetUserAvatar_Success() throws SQLException {
        int userId = 1;
        String expectedAvatar = "avatar.png";

        // Thiết lập hành vi cho các đối tượng mô phỏng
        when(preparedStatement.executeQuery()).thenReturn(mock(ResultSet.class));
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true); // Có dòng dữ liệu
        when(resultSet.getString("avatar")).thenReturn(expectedAvatar); // Trả về giá trị avatar
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Thiết lập hành vi cho connection
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        String actualAvatar = userDAO.getUserAvatar(userId);

        // Kiểm tra kết quả
        assertEquals(expectedAvatar, actualAvatar);

        // Xác minh rằng các phương thức được gọi đúng cách
        Mockito.verify(preparedStatement).setInt(1, userId);
        Mockito.verify(preparedStatement).executeQuery();
    }

    @Test
    public void testGetUserAvatar_NoResult() throws SQLException {
        int userId = 2;

        // Thiết lập hành vi cho các đối tượng mô phỏng
        when(preparedStatement.executeQuery()).thenReturn(mock(ResultSet.class));
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false); // Không có dòng dữ liệu
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Thiết lập hành vi cho connection
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        String actualAvatar = userDAO.getUserAvatar(userId);

        // Kiểm tra kết quả
        assertNull(actualAvatar);

        // Xác minh rằng các phương thức được gọi đúng cách
        Mockito.verify(preparedStatement).setInt(1, userId);
        Mockito.verify(preparedStatement).executeQuery();
    }

    @Test
    public void testResetPassword_Success() throws SQLException {
        String password = "newPassword";
        int userId = 1;
        int expectedRowsUpdated = 1; // Giả định rằng có 1 dòng đã được cập nhật

        // Thiết lập hành vi cho các đối tượng mô phỏng
        when(preparedStatement.executeUpdate()).thenReturn(expectedRowsUpdated);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Gọi phương thức cần kiểm tra
        int actualRowsUpdated = userDAO.resetPassword(password, userId);

        // Kiểm tra kết quả
        assertEquals(expectedRowsUpdated, actualRowsUpdated);

        // Xác minh rằng các phương thức được gọi đúng cách
        Mockito.verify(preparedStatement).setString(1, PasswordUtil.hashPassword(password)); // Chỉ cần xác minh rằng nó đã được gọi
        Mockito.verify(preparedStatement).setInt(2, userId); // Chỉ cần xác minh rằng nó đã được gọi
        Mockito.verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testResetPassword_SQLException() throws SQLException {
        String password = "newPassword";
        int userId = 2;

        // Thiết lập hành vi cho các đối tượng mô phỏng để ném ra SQLException
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        // Kiểm tra xem ngoại lệ GeneralException có được ném ra không
        GeneralException exception = assertThrows(GeneralException.class, () -> {
            userDAO.resetPassword(password, userId);
        });

        // Kiểm tra thông điệp ngoại lệ
        assertTrue(exception.getMessage().contains("Error update user profile"));

        // Xác minh rằng các phương thức được gọi đúng cách
        Mockito.verify(preparedStatement).setString(1, PasswordUtil.hashPassword(password)); // Chỉ cần xác minh rằng nó đã được gọi
        Mockito.verify(preparedStatement).setInt(2, userId); // Chỉ cần xác minh rằng nó đã được gọi
        Mockito.verify(preparedStatement).executeUpdate();
    }


    @Test
    public void getAllUsers() throws SQLException, GeneralException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        //create simulated data
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("email")).thenReturn("john@example.com", "nick@example.com");
        when(resultSet.getString("hashedPassword")).thenReturn("password", "password2");
        when(resultSet.getString("firstName")).thenReturn("John", "Nick");
        when(resultSet.getString("lastName")).thenReturn("Doe", "Vujicic");
        when(resultSet.getString("avatar")).thenReturn("avatar.png", "avatar2.png");
        when(resultSet.getString("dob")).thenReturn("2000-01-01", "2000-02-02");
        when(resultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getString("status")).thenReturn("active", "inactive");
        when(resultSet.getTimestamp("lastModified")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getInt("rolesid")).thenReturn(1, 3);
        //actual list
        List<User> actualUsers = userDAO.getAllUsers();
        //check result
        assertEquals(1, actualUsers.get(0).getId());
        assertEquals(2, actualUsers.get(1).getId());
        assertEquals("john@example.com", actualUsers.get(0).getEmail());
        assertEquals("nick@example.com", actualUsers.get(1).getEmail());
        assertEquals("password", actualUsers.get(0).getHashedPassword());
        assertEquals("password2", actualUsers.get(1).getHashedPassword());
        assertEquals("active", actualUsers.get(0).getStatus());
        assertEquals("inactive", actualUsers.get(1).getStatus());
        assertEquals(1, actualUsers.get(0).getRolesId());
        assertEquals(3, actualUsers.get(1).getRolesId());
        //check verify
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getInt("id");
        verify(resultSet, times(2)).getString("email");
        verify(resultSet, times(2)).getString("hashedPassword");
        verify(resultSet, times(2)).getInt("rolesid");
    }

    @Test
    void getUserById() {
    }
}