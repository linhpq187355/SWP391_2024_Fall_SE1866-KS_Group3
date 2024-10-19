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

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    void testRegisterUserSuccess() throws SQLException {
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
        int result = userService.registerUser(firstName, lastName, email, password, role);

        // Then
        assertTrue(result > 0);
        verify(tokenService).sendToken(email, user.getId());
    }

    @Test
    void testRegisterUserEmailExists() throws SQLException {
        // Given
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "jane.doe@example.com";
        String password = "password123";
        String role = "postRoom";

        when(userDao.emailExists(email)).thenReturn(true);

        // When
        int result = userService.registerUser(firstName, lastName, email, password, role);

        // Then
        assertEquals(0, result);
    }

    @Test
    void testRegisterUserInvalidInput() throws SQLException {
        // Given invalid input
        String firstName = "John";
        String lastName = "Doe";
        String email = "invalid-email";
        String password = "short";
        String role = "findRoommate";

        // When
        int result = userService.registerUser(firstName, lastName, email, password, role);

        // Then
        assertTrue(result < 0);
    }

    @Test
    void testValidateUserInput_ValidInput() {
        assertTrue(userService.validateUserInput("John", "Doe", "john.doe@example.com", "Password123", "Password123", "findRoommate"));
    }

    @Test
    void testValidateUserInput_InvalidFirstName() {
        assertFalse(userService.validateUserInput("John123", "Doe", "john.doe@example.com", "Password123", "Password123", "findRoommate"));
    }

    @Test
    void testValidateUserInput_InvalidEmail() {
        assertFalse(userService.validateUserInput("John", "Doe", "john.doe@com", "Password123", "Password123", "findRoommate"));
    }

    @Test
    void testValidateUserInput_PasswordTooShort() {
        assertFalse(userService.validateUserInput("John", "Doe", "john.doe@example.com", "Pass", "Pass", "findRoommate"));
    }

    @Test
    void testValidateUserInput_PasswordMismatch() {
        assertFalse(userService.validateUserInput("John", "Doe", "john.doe@example.com", "Password123", "Password", "findRoommate"));
    }

    @Test
    void testValidateUserInput_InvalidRole() {
        assertFalse(userService.validateUserInput("John", "Doe", "john.doe@example.com", "Password123", "Password123", "invalidRole"));
    }

    @Test
    void testLoginSuccess() throws SQLException {
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
    void testLogin_UnverifiedToken() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setHashedPassword(PasswordUtil.hashPassword("Password123"));
        user.setStatus("active");
        user.setRolesId(3);
        user.setEmail("user@example.com");

        when(userDao.findUserByEmail("user@example.com")).thenReturn(user);
        // Mock tokenDao to return a verified token
        Token mockToken = new Token();
        mockToken.setVerified(false);
        when(tokenDao.findToken(1)).thenReturn(mockToken);

        String result = userService.login("user@example.com", "Password123",false, response);

        assertEquals("not-verify", result);
        verify(tokenService).sendToken("user@example.com", 1); // Should send token
    }

    @Test
    void testLoginUserNotFound() throws SQLException {
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
    void testLogin_UserHasNoPassword() throws SQLException {
        String email = "john.doe@example.com";
        String password = null;
        User user = new User();
        user.setId(1);
        user.setHashedPassword(null);
        user.setStatus("active");
        when(userDao.findUserByEmail(email)).thenReturn(user);

        String result = userService.login(email, password, false, response);
        assertEquals("Tài khoản này chưa có mật khẩu, vui lòng đăng nhập bằng Google và cập nhật mật khẩu.", result);
    }

    @Test
    void testLoginInvalidPassword() throws SQLException {
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
    void testLoginUserNotActive() throws SQLException {
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
    void testLogin_UserHasNoPermission() throws SQLException {
        // Given
        String email = "john.doe@example.com";
        String password = "wrongPassword";
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword("wrongPassword"));
        user.setStatus("active");
        user.setRolesId(1);
        when(userDao.findUserByEmail(email)).thenReturn(user);

        String result = userService.login(email, password, false, response);
        assertEquals("Bạn không có quyền đăng nhập ở đây.", result);
    }

    @Test
    void testLogin_FindUserByEmailThrowsException() throws SQLException {
        String email = "user@example.com";
        String password = "password";

        when(userDao.findUserByEmail(email)).thenThrow(new SQLException("Database error"));

        assertThrows(SQLException.class, () -> {
            userService.login(email, password, false, response);
        });
    }

    @Test
    void testLogin_FindTokenThrowsException() throws SQLException {
        // Given
        String email = "john.doe@example.com";
        String password = "wrongPassword";
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword("wrongPassword"));
        user.setStatus("active");
        when(userDao.findUserByEmail(email)).thenReturn(user);
        when(tokenDao.findToken(user.getId())).thenThrow(new SQLException("Database error"));

        assertThrows(SQLException.class, () -> {
            userService.login(email, password, false, response);
        });
    }

    @Test
    void testLogin_SendTokenThrowsException() throws SQLException {
        // Given
        String email = "john.doe@example.com";
        String password = "wrongPassword";
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword("wrongPassword"));
        user.setStatus("active");
        when(userDao.findUserByEmail(email)).thenReturn(user);
        Token mockToken = new Token(1,"123456", LocalDateTime.now(),false);
        when(tokenDao.findToken(user.getId())).thenReturn(mockToken);
        doThrow(new GeneralException("Token error")).when(tokenService).sendToken(email, user.getId());

        assertThrows(GeneralException.class, () -> {
            userService.login(email, password, false, response);
        });
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
    void testLoginStaff_Success() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword("Password123"));
        user.setStatus("active");
        user.setRolesId(2); // Assuming 2 is a valid role

        // Mock tokenDao to return a verified token
        Token mockToken = new Token();
        mockToken.setVerified(true);
        when(userDao.findUserByEmail("admin@example.com")).thenReturn(user);
        when(tokenDao.findToken(1)).thenReturn(mockToken);

        String result = userService.loginStaff("admin@example.com", "Password123", response);

        assertEquals("success", result);
        verify(tokenService, never()).sendToken(any(), eq(1)); // Should not send token
    }

    @Test
    void testLoginStaff_UserNotFound() throws SQLException {
        when(userDao.findUserByEmail("unknown@example.com")).thenReturn(null);

        String result = userService.loginStaff("unknown@example.com", "Password123", response);

        assertEquals("Email hoặc mật khẩu không đúng", result);
    }


    @Test
    void testLoginStaff_IncorrectPassword() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword("WrongPassword"));
        user.setStatus("active");
        user.setRolesId(2);

        when(userDao.findUserByEmail("admin@example.com")).thenReturn(user);

        String result = userService.loginStaff("admin@example.com", "Password123", response);

        assertEquals("Email hoặc mật khẩu không đúng", result);
    }

    @Test
    void testLoginStaff_UserNotAdminOrModerator() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword("Password123"));
        user.setStatus("active");
        user.setRolesId(3); // Assuming 3 is not allowed

        when(userDao.findUserByEmail("admin@example.com")).thenReturn(user);

        String result = userService.loginStaff("admin@example.com", "Password123", response);

        assertEquals("Bạn không có quyền đăng nhập ở đây.", result);
    }

    @Test
    void testLoginStaff_UserInactive() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setHashedPassword(PasswordUtil.hashPassword("Password123"));
        user.setStatus("inactive");
        user.setRolesId(2);

        when(userDao.findUserByEmail("admin@example.com")).thenReturn(user);

        String result = userService.loginStaff("admin@example.com", "Password123", response);

        assertEquals("Tài khoản này đã bị khóa", result);
    }

    @Test
    void testLoginStaff_UnverifiedToken() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setHashedPassword(PasswordUtil.hashPassword("Password123"));
        user.setStatus("active");
        user.setRolesId(2);
        user.setEmail("admin@example.com");

        when(userDao.findUserByEmail("admin@example.com")).thenReturn(user);
        // Mock tokenDao to return a verified token
        Token mockToken = new Token();
        mockToken.setVerified(false);
        when(tokenDao.findToken(1)).thenReturn(mockToken);

        String result = userService.loginStaff("admin@example.com", "Password123", response);

        assertEquals("Tài khoản này chưa được xác thực, liên hệ với quản trị viên để xác thực lại tài khoản.", result);
        verify(tokenService).sendToken("admin@example.com", 1); // Should send token
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

    @Test
    void testUpdateMatchingProfile_success() {
        // Arrange
        String dob = "1990-01-01";
        String gender = "Male";
        String rawHowLong = "6 months";
        String emvdate = "2024-01-01";
        String lmvdate = "2024-06-01";
        String rawMinBudget = "500";
        String rawMaxBudget = "1000";
        String userId = "1";

        User user = new User();
        user.setId(1);
        user.setDob(LocalDate.parse(dob));
        user.setGender(gender);
        user.setDuration(rawHowLong);
        user.setEarliestMoveIn(LocalDate.parse(emvdate));
        user.setLatestMoveIn(LocalDate.parse(lmvdate));
        user.setMinBudget(Integer.parseInt(rawMinBudget));
        user.setMaxBudget(Integer.parseInt(rawMaxBudget));

        when(userDao.updateMatchingProfile(any(User.class))).thenReturn(1);

        // Act
        int result = userService.updateMatchingProfile(dob, gender, rawHowLong, emvdate, lmvdate, rawMinBudget, rawMaxBudget, userId);

        // Assert
        assertEquals(1, result);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).updateMatchingProfile(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals(1, capturedUser.getId());
        assertEquals(LocalDate.parse(dob), capturedUser.getDob());
        assertEquals(gender, capturedUser.getGender());
        assertEquals(rawHowLong, capturedUser.getDuration());
        assertEquals(LocalDate.parse(emvdate), capturedUser.getEarliestMoveIn());
        assertEquals(LocalDate.parse(lmvdate), capturedUser.getLatestMoveIn());
        assertEquals(Integer.parseInt(rawMinBudget), capturedUser.getMinBudget());
        assertEquals(Integer.parseInt(rawMaxBudget), capturedUser.getMaxBudget());
    }

    @Test
    void testUpdateMatchingProfile_generalException() {
        // Arrange
        String dob = "1990-01-01";
        String gender = "Male";
        String rawHowLong = "6 months";
        String emvdate = "2024-01-01";
        String lmvdate = "2024-06-01";
        String rawMinBudget = "500";
        String rawMaxBudget = "1000";
        String userId = "1";

        when(userDao.updateMatchingProfile(any(User.class))).thenThrow(new GeneralException("Error"));

        // Act & Assert
        GeneralException thrown = assertThrows(GeneralException.class, () -> {
            userService.updateMatchingProfile(dob, gender, rawHowLong, emvdate, lmvdate, rawMinBudget, rawMaxBudget, userId);
        });

        assertEquals("Failed to update user matching profile", thrown.getMessage());
    }

    @Test
    void testAccountValidation_ValidInput() {
        String result = userService.validateAccount("John", "Doe", "john.doe@example.com", "password123", "password123", 1, "male", "1234567890", "2000-01-01");
        assertEquals("Valid", result);
    }

    @Test
    void testAccountValidation_InvalidFirstName_Numbers() {
        String result = userService.validateAccount("John123", "Doe", "john.doe@example.com", "password123", "password123", 1, "male", "1234567890", "2000-01-01");
        assertEquals("First name can only contain letters and spaces.", result);
    }

    @Test
    void testAccountValidation_InvalidFirstName_Empty() {
        String result = userService.validateAccount("", "Doe", "john.doe@example.com", "password123", "password123", 1, "male", "1234567890", "2000-01-01");
        assertEquals("First name can only contain letters and spaces.", result);
    }

    @Test
    void testAccountValidation_InvalidLastName_Numbers() {
        String result = userService.validateAccount("John", "Doe123", "john.doe@example.com", "password123", "password123", 1, "male", "1234567890", "2000-01-01");
        assertEquals("Last name can only contain letters and spaces.", result);
    }

    @Test
    void testAccountValidation_InvalidLastName_Empty() {
        String result = userService.validateAccount("John", "", "john.doe@example.com", "password123", "password123", 1, "male", "1234567890", "2000-01-01");
        assertEquals("Last name can only contain letters and spaces.", result);
    }

    @Test
    void testAccountValidation_InvalidEmail() {
        String result = userService.validateAccount("John", "Doe", "invalidEmail@", "password123", "password123", 1, "male", "1234567890", "2000-01-01");
        assertEquals("Invalid email format.", result);
    }

    @Test
    void testAccountValidation_InvalidPassword_Length() {
        String result = userService.validateAccount("John", "Doe", "john.doe@example.com", "pass", "pass", 1, "male", "1234567890", "2000-01-01");
        assertEquals("Password must be at least 8 characters long.", result);
    }

    @Test
    void testAccountValidation_InvalidPassword_Mismatch() {
        String result = userService.validateAccount("John", "Doe", "john.doe@example.com", "password123", "password321", 1, "male", "1234567890", "2000-01-01");
        assertEquals("Password and confirm password do not match.", result);
    }

    @Test
    void testAccountValidation_InvalidDob_Empty() {
        String result = userService.validateAccount("John", "Doe", "john.doe@example.com", "password123", "password123", 1, "male", "1234567890", "");
        assertEquals("Date of birth cannot be empty.", result);
    }

    @Test
    void testAccountValidation_InvalidGender_Null() {
        String result = userService.validateAccount("John", "Doe", "john.doe@example.com", "password123", "password123", 1, null, "1234567890", "2000-01-01");
        assertEquals("Gender must be either 'male' or 'female'.", result);
    }

    @Test
    void testAccountValidation_InvalidGender_InvalidValue() {
        String result = userService.validateAccount("John", "Doe", "john.doe@example.com", "password123", "password123", 1, "other", "1234567890", "2000-01-01");
        assertEquals("Gender must be either 'male' or 'female'.", result);
    }

    @Test
    void testAccountValidation_InvalidGender_Empty() {
        String result = userService.validateAccount("John", "Doe", "john.doe@example.com", "password123", "password123", 1, "", "1234567890", "2000-01-01");
        assertEquals("Gender must be either 'male' or 'female'.", result);
    }

    @Test
    void testUpdatePassword_passwordIsNull() throws GeneralException {
        int result = userService.updatePassword(1, 1, "oldPass", null);
        assertEquals(-2, result);
    }

    @Test
    void testUpdatePassword_passwordIsEmpty() throws GeneralException {
        int result = userService.updatePassword(1, 1, "oldPass", "");
        assertEquals(-2, result);
    }

    @Test
    void testUpdatePassword_hadPassIsZero_oldPassIsNull() throws GeneralException {
        int result = userService.updatePassword(1, 0, null, "newPass");
        assertEquals(-2, result);
    }

    @Test
    void testUpdatePassword_hadPassIsZero_oldPassIsEmpty() throws GeneralException {
        int result = userService.updatePassword(1, 0, "", "newPass");
        assertEquals(-2, result);
    }

    @Test
    void testUpdatePassword_hadPassIsZero_hashedPasswordIsNull() throws GeneralException {
        User user = new User();
        user.setHashedPassword(null);
        when(userDao.getUserById(1)).thenReturn(user);

        int result = userService.updatePassword(1, 0, "oldPass", "newPass");
        assertEquals(-1, result);
    }

    @Test
    void testUpdatePassword_hadPassIsZero_hashedPasswordIsEmpty() throws GeneralException {
        User user = new User();
        user.setHashedPassword("");
        when(userDao.getUserById(1)).thenReturn(user);

        int result = userService.updatePassword(1, 0, "oldPass", "newPass");
        assertEquals(-1, result);
    }

    @Test
    void testUpdatePassword_hadPassIsZero_wrongOldPassword() throws GeneralException {
        User user = new User();
        user.setHashedPassword(PasswordUtil.hashPassword("correctOldPass"));
        when(userDao.getUserById(1)).thenReturn(user);

        int result = userService.updatePassword(1, 0, "wrongOldPass", "newPass");
        assertEquals(-1, result);
    }

    @Test
    void testUpdatePassword_hadPassIsZero_correctOldPassword() throws GeneralException {
        User user = new User();
        user.setHashedPassword(PasswordUtil.hashPassword("oldPass"));
        when(userDao.getUserById(1)).thenReturn(user);
        when(userDao.resetPassword("newPass", 1)).thenReturn(1);

        int result = userService.updatePassword(1, 0, "oldPass", "newPass");
        assertEquals(1, result);
    }

    @Test
    void testUpdatePassword_hadPassIsOne() throws GeneralException {
        when(userDao.resetPassword("newPass", 1)).thenReturn(1);

        int result = userService.updatePassword(1, 1, null, "newPass");
        assertEquals(1, result);
    }

    @Test
    void testUpdatePassword_sqlExceptionFromResetPassword() throws GeneralException {
        when(userDao.resetPassword("newPass", 1)).thenThrow(new GeneralException("Error in server."));

        assertThrows(GeneralException.class, () -> userService.updatePassword(1, 1, null, "newPass"));
    }

    @Test
    void testUpdatePassword_sqlExceptionFromGetUserById() throws GeneralException {
        when(userDao.getUserById(1)).thenThrow(new GeneralException("Error in server."));

        assertThrows(GeneralException.class, () -> userService.updatePassword(1, 0, "oldPass", "newPass"));
    }

}
