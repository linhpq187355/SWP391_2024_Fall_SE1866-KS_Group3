package com.homesharing.service;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.PasswordUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        userService = new UserServiceImpl(userDao, tokenDao, tokenService);
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

}
