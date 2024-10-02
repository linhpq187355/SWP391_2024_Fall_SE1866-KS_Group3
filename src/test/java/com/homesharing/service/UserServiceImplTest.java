package com.homesharing.service;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.PasswordUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserDAO userDao;
    private TokenDAO tokenDao;
    private TokenService tokenService;
    private UserServiceImpl userService;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDAO.class);
        tokenDao = mock(TokenDAO.class);
        tokenService = mock(TokenService.class);
        response = mock(HttpServletResponse.class);
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,null);
    }

    @Test
    void testRegisterUserSuccess() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        String role = "findRoommate";
        User user = new User();
        user.setId(1); // Assume user ID is 1

        when(userDao.emailExists(email)).thenReturn(false);
        when(userDao.saveUser(any(User.class))).thenReturn(user.getId());

        // When
        String result = userService.registerUser(firstName, lastName, email, password, role);

        // Then
        assertEquals("success", result);
        verify(tokenService).sendToken(email, user.getId());
    }

    @Test
    void testRegisterUserEmailExists() {
        // Given
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "jane.doe@example.com";
        String password = "password123";
        String role = "postRoom";

        when(userDao.emailExists(email)).thenReturn(true);

        // When
        String result = userService.registerUser(firstName, lastName, email, password, role);

        // Then
        assertEquals("Email đã được sử dụng!", result);
    }

    @Test
    void testRegisterUserInvalidInput() {
        // Given invalid input
        String firstName = "John";
        String lastName = "Doe";
        String email = "invalid-email";
        String password = "short";
        String role = "findRoommate";

        // When
        String result = userService.registerUser(firstName, lastName, email, password, role);

        // Then
        assertEquals("Invalid input!", result);
    }

    @Test
    void testLoginSuccess() {
        // Given
        String email = "john.doe@example.com";
        String password = "password123";
        boolean rememberMe = true;
        User user = new User();
        user.setId(1);
        user.setEmail(email);
        user.setRolesId(4);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setHashedPassword(PasswordUtil.hashPassword(password));
        user.setStatus("active");
        when(userDao.findUserByEmail(email)).thenReturn(user);
        // Mock tokenDao to return a verified token
        Token mockToken = new Token();
        mockToken.setVerified(true);
        when(tokenDao.findToken(1)).thenReturn(mockToken);

        // When
        String result = userService.login(email, password, rememberMe, response);

        // Then
        assertEquals("success", result);
        verify(tokenService, never()).sendToken(any(), eq(1)); // Token should not be sent again
    }

    @Test
    void testLoginUserNotFound() {
        // Given
        String email = "unknown@example.com";
        String password = "password123";

        when(userDao.findUserByEmail(email)).thenReturn(null);

        // When
        String result = userService.login(email, password, false, response);

        // Then
        assertEquals("Email hoặc mật khẩu không đúng", result);
    }

    @Test
    void testLoginInvalidPassword() {
        // Given
        String email = "john.doe@example.com";
        String password = "wrongPassword";
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword("password123"));
        user.setStatus("active");
        when(userDao.findUserByEmail(email)).thenReturn(user);

        // When
        String result = userService.login(email, password, false, response);

        // Then
        assertEquals("Email hoặc mật khẩu không đúng", result);
    }

    @Test
    void testLoginUserNotActive() {
        // Given
        String email = "john.doe@example.com";
        String password = "password123";
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword(password));
        user.setStatus("inactive");
        when(userDao.findUserByEmail(email)).thenReturn(user);

        // When
        String result = userService.login(email, password, false, response);

        // Then
        assertEquals("Tài khoản này đã bị khóa", result);
    }

    @Test
    void testLogout_Success() {
        // Test the logout method
        String result = userService.logout(response);

        // Verify the expected outcome
        assertEquals("logout success", result);

        // Verify that cookies were removed
        verify(response, times(5)).addCookie(any());
    }

    @Test
    void testUpdateUserProfile_success() {
        // Arrange
        String userId = "1";
        String firstName = "John";
        String lastName = "Doe";
        String address = "123 Main St";
        String gender = "Male";
        String dob = "1990-01-01";
        String avatarFileName = "avatar.png";

        User user = new User();
        user.setId(1);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setGender(gender);
        user.setDob(LocalDate.parse(dob));
        user.setAvatar(avatarFileName);

        when(userDao.updateUserProfile(any(User.class))).thenReturn(1); // Mocking the update method

        // Act
        int result = userService.updateUserProfile(userId, firstName, lastName, address, gender, dob, avatarFileName);

        // Assert
        assertEquals(1, result);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).updateUserProfile(userCaptor.capture());
        assertEquals(firstName, userCaptor.getValue().getFirstName());
        assertEquals(lastName, userCaptor.getValue().getLastName());
        assertEquals(address, userCaptor.getValue().getAddress());
        assertEquals(gender, userCaptor.getValue().getGender());
        assertEquals(LocalDate.parse(dob), userCaptor.getValue().getDob());
        assertEquals(avatarFileName, userCaptor.getValue().getAvatar());
    }

    @Test
    void testUpdateUserProfile_generalException() {
        // Arrange
        String userId = "1";
        when(userDao.updateUserProfile(any(User.class))).thenThrow(new GeneralException("Error"));

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            userService.updateUserProfile(userId, "John", "Doe", "123 Main St", "Male", "1990-01-01", "avatar.png");
        });

        assertEquals("Failed to update user profile", thrown.getMessage());
    }

    @Test
    void testResetUserPassword_success() {
        // Arrange
        int userId = 1;
        String newPassword = "newPassword123";
        when(userDao.resetPassword(newPassword, userId)).thenReturn(1); // Mocking the resetPassword method

        // Act
        int result = userService.resetUserPassword(userId, newPassword);

        // Assert
        assertEquals(1, result);
        verify(userDao).resetPassword(newPassword, userId);
    }

    @Test
    void testResetUserPassword_generalException() {
        // Arrange
        int userId = 1;
        String newPassword = "newPassword123";
        when(userDao.resetPassword(newPassword, userId)).thenThrow(new GeneralException("Error"));

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            userService.resetUserPassword(userId, newPassword);
        });

        assertEquals("Error resetting password for user ID: 1", thrown.getMessage());
    }

}
