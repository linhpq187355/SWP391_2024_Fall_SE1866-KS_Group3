package com.homesharing.service;

import com.homesharing.conf.Config;
import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.impl.ForgotPasswordServiceImpl;
import com.homesharing.util.SendingEmail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ForgotPasswordServiceImplTest {
    private UserDAO userDAO;
    private TokenDAO tokenDAO;
    private ForgotPasswordServiceImpl forgotPasswordService;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class); // Mô phỏng UserDAO
        tokenDAO = mock(TokenDAO.class); // Mô phỏng TokenDAO
        forgotPasswordService = new ForgotPasswordServiceImpl(userDAO, tokenDAO); // Tạo instance với các mock object
    }

    @Test
    void testSendResetPasswordToken_Success() throws MessagingException, SQLException {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setId(1);
        Token token = new Token();
        token.setToken("mockToken123");

        when(userDAO.findUserByEmail(email)).thenReturn(user); // Mô phỏng tìm thấy user
        when(tokenDAO.findToken(user.getId())).thenReturn(token); // Mô phỏng tìm thấy token

        // Mock Config's static method and SendingEmail's static method
        try (var configMock = Mockito.mockStatic(Config.class);
             var emailMock = Mockito.mockStatic(SendingEmail.class)) {

            configMock.when(Config::getBaseUrl).thenReturn("http://localhost");

            // Mock phương thức tĩnh sendMail để không làm gì khi được gọi
            emailMock.when(() -> SendingEmail.sendMail(eq(email), anyString(), anyString()))
                    .thenAnswer(invocation -> null); // Cách mock void method trong static mocking

            // Act
            boolean result = forgotPasswordService.sendResetPasswordToken(email);

            // Assert
            assertTrue(result); // Kết quả mong đợi là true
            verify(userDAO, times(1)).findUserByEmail(email); // Kiểm tra xem phương thức đã được gọi đúng
            verify(tokenDAO, times(1)).findToken(user.getId()); // Xác nhận rằng token đã được tìm

            // Verify that Config.getBaseUrl was called once
            configMock.verify(Config::getBaseUrl, times(1));
            // Verify that SendingEmail.sendMail was called once
            emailMock.verify(() -> SendingEmail.sendMail(eq(email), anyString(), anyString()), times(1));
        }
    }

    @Test
    void testSendResetPasswordToken_UserNotFound() throws SQLException {
        // Arrange
        String email = "nonexistent@example.com";

        // Mô phỏng không tìm thấy user
        when(userDAO.findUserByEmail(email)).thenReturn(null);

        // Act
        boolean result = forgotPasswordService.sendResetPasswordToken(email);

        // Assert
        assertFalse(result); // Kết quả mong đợi là false vì không tìm thấy user
        verify(userDAO, times(1)).findUserByEmail(email); // Kiểm tra xem phương thức đã được gọi đúng
        verify(tokenDAO, never()).findToken(anyInt()); // Không bao giờ gọi đến tokenDAO
    }

    @Test
    void testSendResetPasswordToken_EmailSendingFailure() throws MessagingException, SQLException {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setId(1);
        Token token = new Token();
        token.setToken("mockToken123");

        when(userDAO.findUserByEmail(email)).thenReturn(user); // Mô phỏng tìm thấy user
        when(tokenDAO.findToken(user.getId())).thenReturn(token); // Mô phỏng tìm thấy token

        // Mock Config's static method and SendingEmail's static method
        try (var configMock = Mockito.mockStatic(Config.class);
             var emailMock = Mockito.mockStatic(SendingEmail.class)) {

            configMock.when(Config::getBaseUrl).thenReturn("http://localhost");

            // Mô phỏng việc gửi email thất bại
            emailMock.when(() -> SendingEmail.sendMail(eq(email), anyString(), anyString()))
                    .thenThrow(new MessagingException("Failed to send email"));

            // Act & Assert
            GeneralException exception = assertThrows(GeneralException.class, () -> {
                forgotPasswordService.sendResetPasswordToken(email);
            });

            // Assert rằng exception được ném ra với thông điệp đúng
            assertEquals("Error while sending email", exception.getMessage());
            verify(userDAO, times(1)).findUserByEmail(email); // Xác nhận rằng userDAO đã được gọi
            verify(tokenDAO, times(1)).findToken(user.getId()); // Xác nhận rằng tokenDAO đã được gọi

            // Verify that Config.getBaseUrl was called once
            configMock.verify(Config::getBaseUrl, times(1));
            // Verify that SendingEmail.sendMail was called once
            emailMock.verify(() -> SendingEmail.sendMail(eq(email), anyString(), anyString()), times(1));
        }
    }
}
