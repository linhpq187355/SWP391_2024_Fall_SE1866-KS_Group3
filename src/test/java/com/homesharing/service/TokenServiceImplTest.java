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

    @BeforeEach
    void setUp() {
        token = new Token(1, "123456", LocalDateTime.now(), false);
    }

    @Test
    void testCheckTokenSuccess() throws SQLException {
        when(tokenDao.findToken(1)).thenReturn(token);

        boolean result = tokenService.checkToken("123456", 1, LocalDateTime.now());

        assertTrue(result);
        verify(tokenDao, times(1)).updateTokenVerification(1);
    }

    @Test
    void testCheckTokenTokenNotFound() throws SQLException {
        when(tokenDao.findToken(1)).thenReturn(null);

        boolean result = tokenService.checkToken("123456", 1, LocalDateTime.now());

        assertFalse(result);
        verify(tokenDao, never()).updateTokenVerification(anyInt());
    }

    @Test
    void testCheckTokenTokenMismatch() throws SQLException {
        when(tokenDao.findToken(1)).thenReturn(token);

        boolean result = tokenService.checkToken("654321", 1, LocalDateTime.now());

        assertFalse(result);
        verify(tokenDao, never()).updateTokenVerification(anyInt());
    }

    @Test
    void testSendTokenNewToken() throws SQLException {
        // Mock static methods
        try (MockedStatic<SecureRandomCode> mockedSecureRandomCode = Mockito.mockStatic(SecureRandomCode.class);
             MockedStatic<SendingEmail> mockedSendingEmail = Mockito.mockStatic(SendingEmail.class)) {

            when(tokenDao.findToken(1)).thenReturn(null);
            mockedSecureRandomCode.when(SecureRandomCode::generateCode).thenReturn("generated-code");

            tokenService.sendToken("test@example.com", 1);

            verify(tokenDao, times(1)).insertToken(any(Token.class));
            mockedSendingEmail.verify(() -> SendingEmail.sendMail(eq("test@example.com"), anyString(), anyString()), times(1));
        }
    }

    @Test
    void testSendTokenExistingToken() throws SQLException {
        // Mock static method for SendingEmail.sendMail
        try (MockedStatic<SendingEmail> mockedSendingEmail = Mockito.mockStatic(SendingEmail.class)) {

            when(tokenDao.findToken(1)).thenReturn(token);

            tokenService.sendToken("test@example.com", 1);

            verify(tokenDao, never()).insertToken(any(Token.class));
            mockedSendingEmail.verify(() -> SendingEmail.sendMail(eq("test@example.com"), anyString(), anyString()), times(1));
        }
    }

    @Test
    void testSendTokenThrowsExceptionWhenEmailFails() throws SQLException {
        // Mock static method for SendingEmail.sendMail
        try (MockedStatic<SendingEmail> mockedSendingEmail = Mockito.mockStatic(SendingEmail.class)) {

            when(tokenDao.findToken(1)).thenReturn(token);
            mockedSendingEmail.when(() -> SendingEmail.sendMail(anyString(), anyString(), anyString()))
                    .thenThrow(new MessagingException());

            assertThrows(GeneralException.class, () -> tokenService.sendToken("test@example.com", 1));

            verify(tokenDao, never()).insertToken(any(Token.class));
        }
    }
}
