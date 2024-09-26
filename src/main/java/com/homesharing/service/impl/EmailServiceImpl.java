package com.homesharing.service.impl;

import com.homesharing.dao.TokenDao;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailServiceImpl implements EmailService {

    private final TokenDao tokenDao;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(TokenDao tokenDao) {
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
}
