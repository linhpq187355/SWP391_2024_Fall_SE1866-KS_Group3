package com.homesharing.service.impl;

import com.homesharing.dao.TokenDao;
import com.homesharing.model.Token;
import com.homesharing.service.EmailService;

public class EmailServiceImpl implements EmailService {

    private final TokenDao tokenDao;

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

        } catch (RuntimeException e) {
            // Handle any errors from methods in tokenDao
            throw new RuntimeException("Error while checking token for userId: " + userID + ": " +  e.getMessage());
        }
    }
}
