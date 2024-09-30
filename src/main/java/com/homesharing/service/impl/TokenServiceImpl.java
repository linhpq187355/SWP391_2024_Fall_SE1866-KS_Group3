package com.homesharing.service.impl;

import com.homesharing.conf.Config;
import com.homesharing.dao.TokenDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.service.TokenService;
import com.homesharing.util.SecureRandomCode;
import com.homesharing.util.SendingEmail;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class TokenServiceImpl implements TokenService {

    private final TokenDAO tokenDao;
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    public TokenServiceImpl(TokenDAO tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    public boolean checkToken(String tokenCode, int userID) {
        try {
            // Attempt to find the token for the given userID
            Token token = tokenDao.findToken(userID);

            // Check if the token exists
            if (token == null) {
                return false; // Token not found
            }

            // Check if the provided tokenCode matches the stored token
            if (token.getToken().equals(tokenCode)) {
                // If the token is not verified, update its status
                if (!token.isVerified()) {
                    tokenDao.updateTokenVerification(userID);
                }
                return true; // Token is valid
            } else {
                return false; // Token code does not match
            }
        } catch (GeneralException e) {
            // Log the exception with contextual information
            logger.error("Error while checking token for userId {}: {}", userID, e.getMessage());
            return false; // Return false when an error occurs
        } catch (RuntimeException e) {
            // Log unexpected exceptions
            logger.error("Unexpected error occurred for userId {}: {}", userID, e.getMessage());
            return false; // Return false when an error occurs
        }
    }

    @Override
    public boolean sendToken(String email, int userId) {
        Token oldToken = tokenDao.findToken(userId);
        String tokenCode;
        LocalDateTime requestedTime;

        if (oldToken != null) {
            // Use the existing token code and requested time
            tokenCode = oldToken.getToken();
        } else {
            // Create a new token
            tokenCode = SecureRandomCode.generateCode();
            requestedTime = LocalDateTime.now();
            Token newToken = new Token(userId, tokenCode, requestedTime, false);
            tokenDao.insertToken(newToken);
        }
        // Get base URL from properties
        String baseUrl = Config.getBaseUrl();
        String subject = "Xác nhận email";
        String content = "Click vào link sau để xác thực email: "
                + baseUrl + "/verify?code=" + tokenCode
                + "&userId=" + userId;
        try {
            SendingEmail.sendMail(email, subject, content);
        } catch (MessagingException e) {
            throw new GeneralException("Error while sending email");
        }
        return true;
    }
}
