package com.homesharing.service;

import com.homesharing.dao.TokenDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.util.SecureRandomCode;
import com.homesharing.util.SendingEmail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockedStatic;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private TokenDAO tokenDao;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private Token token;

    private MockedStatic<SecureRandomCode> mockedSecureRandomCode;
    private MockedStatic<SendingEmail> mockedSendingEmail;

    @BeforeEach
    void setUp() {
        mockedSecureRandomCode = Mockito.mockStatic(SecureRandomCode.class);
        mockedSecureRandomCode.when(SecureRandomCode::generateCode).thenReturn("manhdz");
        mockedSendingEmail = Mockito.mockStatic(SendingEmail.class);
        token = new Token(1, "123456", LocalDateTime.now(), false);
    }

    @Test
    void testCheckTokenTokenNotFound() throws SQLException {
        when(tokenDao.findToken(1)).thenReturn(null);

        boolean result = tokenService.checkToken("123456", 1, LocalDateTime.now());

        assertFalse(result);
    }

    @Test
    void testCheckTokenTokenMismatch() throws SQLException {
        when(tokenDao.findToken(1)).thenReturn(token);

        boolean result = tokenService.checkToken("654321", 1, LocalDateTime.now());

        assertFalse(result);
    }

    @Test
    void testTokenExpired() throws SQLException {
        Token token2 = new Token(1,"tokenCode", LocalDateTime.now().minusMinutes(2), false);
        when(tokenDao.findToken(anyInt())).thenReturn(token2);
        // Act
        boolean result = tokenService.checkToken("tokenCode", 1, LocalDateTime.now());
        // Assert
        assertFalse(result);
    }

    @Test
    void testTokenValidNotVerified() throws SQLException {
        // Arrange
        when(tokenDao.findToken(anyInt())).thenReturn(token);

        // Act
        boolean result = tokenService.checkToken("123456", 1, LocalDateTime.now());

        // Assert
        assertTrue(result);
        verify(tokenDao).updateTokenVerification(1); // Check if verification update is called
    }

    @Test
    void testTokenValidAlreadyVerified() throws SQLException {
        // Arrange
        Token token2 = new Token(1, "tokenCode", LocalDateTime.now(), true);
        when(tokenDao.findToken(anyInt())).thenReturn(token2);

        // Act
        boolean result = tokenService.checkToken("tokenCode", 1, LocalDateTime.now());

        // Assert
        assertTrue(result);
        verify(tokenDao, never()).updateTokenVerification(anyInt()); // Check if no update is called
    }

    @Test
    void testTokenVerificationUpdateFails() throws SQLException {
        // Arrange
        when(tokenDao.findToken(anyInt())).thenReturn(token);
        doThrow(SQLException.class).when(tokenDao).updateTokenVerification(anyInt());

        // Act & Assert
        assertThrows(SQLException.class, () -> tokenService.checkToken("123456", 1, LocalDateTime.now()));
    }

    @Test
    void testFindTokenThrowsSQLException() throws SQLException {
        // Arrange
        when(tokenDao.findToken(anyInt())).thenThrow(SQLException.class);

        // Act & Assert
        assertThrows(SQLException.class, () -> tokenService.checkToken("123456", 1, LocalDateTime.now()));
    }

    @Test
    void testCheckTokenThrowsRuntimeException() throws SQLException {
        // Arrange
        when(tokenDao.findToken(anyInt())).thenThrow(RuntimeException.class);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> tokenService.checkToken("123456", 1, LocalDateTime.now()));
    }

    @Test
    void testSendToken_EmailIsNull_ShouldThrowException() {
        // Precondition
        int userId = 1;

        // Execute and confirm
        assertThrows(GeneralException.class, () -> tokenService.sendToken(null, userId));
    }

    @Test
    void testSendToken_UserIdLessEqual0_ShouldThrowException() {
        // Execute and confirm
        assertThrows(GeneralException.class, () -> tokenService.sendToken("user@gmail.com", 0));
    }

    @Test
    void testSendToken_EmailIsEmpty_ShouldThrowException() {
        // Precondition
        int userId = 1;

        // Execute and confirm
        assertThrows(GeneralException.class, () -> tokenService.sendToken("", userId));
    }

    @Test
    void testSendToken_DatabaseErrorDuringTokenUpdate_ShouldThrowSQLException() throws SQLException {
        when(tokenDao.findToken(1)).thenReturn(token);
        doThrow(SQLException.class).when(tokenDao).updateToken(anyInt(), anyString(), any(LocalDateTime.class));

        // Execute and confirm
        assertThrows(SQLException.class, () -> tokenService.sendToken("user@gmail.com", 1));
    }

    @Test
    void testSendToken_DatabaseErrorDuringTokenInsert_ShouldThrowSQLException() throws SQLException {
        // Precondition
        int userId = 1;
        String email = "user@example.com";

        when(tokenDao.findToken(userId)).thenReturn(null);
        doThrow(SQLException.class).when(tokenDao).insertToken(any(Token.class));

        // Execute and confirm
        assertThrows(SQLException.class, () -> tokenService.sendToken(email, userId));
    }

    @Test
    void testSendTokenNewToken() throws SQLException {
        // Mock static methods
        when(tokenDao.findToken(1)).thenReturn(null);
            mockedSecureRandomCode.when(SecureRandomCode::generateCode).thenReturn("generated-code");

            tokenService.sendToken("test@example.com", 1);

            verify(tokenDao, times(1)).insertToken(any(Token.class));
            mockedSendingEmail.verify(() -> SendingEmail.sendMail(eq("test@example.com"), anyString(), anyString()), times(1));
    }

    @Test
    void testSendTokenExistingToken() throws SQLException {
            when(tokenDao.findToken(1)).thenReturn(token);

            tokenService.sendToken("test@example.com", 1);
            verify(tokenDao).updateToken(eq(1), anyString(), any(LocalDateTime.class));
            verify(tokenDao, never()).insertToken(any(Token.class));
            mockedSendingEmail.verify(() -> SendingEmail.sendMail(eq("test@example.com"), anyString(), anyString()), times(1));
    }

    @Test
    void testSendTokenThrowsExceptionWhenEmailFails() throws SQLException {
            when(tokenDao.findToken(1)).thenReturn(token);
            mockedSendingEmail.when(() -> SendingEmail.sendMail(anyString(), anyString(), anyString()))
                    .thenThrow(new MessagingException());

            assertThrows(GeneralException.class, () -> tokenService.sendToken("test@example.com", 1));

            verify(tokenDao, never()).insertToken(any(Token.class));
    }
}
