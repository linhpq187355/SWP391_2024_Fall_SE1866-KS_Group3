package com.homesharing.service;

import com.homesharing.dao.TokenDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.service.impl.TokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceImplTest {

    @Mock
    private TokenDAO tokenDao;

    @InjectMocks
    private TokenService tokenService = new TokenServiceImpl(tokenDao);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckTokenValid() {
        // Given
        int userId = 1;
        String tokenCode = "validToken";
        Token token = new Token(userId, tokenCode, LocalDateTime.now(), false);
        when(tokenDao.findToken(userId)).thenReturn(token);

        // When
        boolean result = tokenService.checkToken(tokenCode, userId);

        // Then
        assertTrue(result);
        verify(tokenDao, times(1)).updateTokenVerification(userId);
    }

    @Test
    void testCheckTokenInvalid() {
        // Given
        int userId = 1;
        String tokenCode = "invalidToken";
        Token token = new Token(userId, "validToken", LocalDateTime.now(), false);
        when(tokenDao.findToken(userId)).thenReturn(token);

        // When
        boolean result = tokenService.checkToken(tokenCode, userId);

        // Then
        assertFalse(result);
        verify(tokenDao, never()).updateTokenVerification(anyInt());
    }

    @Test
    void testSendTokenNewToken() {
        // Given
        String email = "user@example.com";
        int userId = 1;
        when(tokenDao.findToken(userId)).thenReturn(null); // No existing token

        // When
        boolean result = tokenService.sendToken(email, userId);

        // Then
        assertTrue(result);
        verify(tokenDao, times(1)).insertToken(any(Token.class));
        // Additional assertions can be made to verify the sending of email
    }

    @Test
    void testSendTokenAlreadyVerified() {
        // Given
        String email = "user@example.com";
        int userId = 1;
        Token existingToken = new Token(userId, "existingToken", LocalDateTime.now(), true);
        when(tokenDao.findToken(userId)).thenReturn(existingToken);

        // When
        boolean result = tokenService.sendToken(email, userId);

        // Then
        assertFalse(result);
        verify(tokenDao, never()).insertToken(any(Token.class));
        // Additional assertions can be made to verify the sending of the verification success email
    }

    @Test
    void testCheckTokenThrowsException() {
        // Given
        int userId = 1;
        String tokenCode = "validToken";
        when(tokenDao.findToken(userId)).thenThrow(new GeneralException("Database error"));

        // When
        boolean result = tokenService.checkToken(tokenCode, userId);

        // Then
        assertFalse(result);
        verify(tokenDao, never()).updateTokenVerification(anyInt());
    }
}

