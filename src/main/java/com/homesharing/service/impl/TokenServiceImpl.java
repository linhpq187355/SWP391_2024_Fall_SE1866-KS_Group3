/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.service.impl;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.TokenService;
import com.homesharing.util.SecureRandomCode;
import com.homesharing.util.SendingEmail;
import jakarta.mail.MessagingException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This class implements the TokenService interface to handle
 * email verification token-related logic, including checking
 * token validity and sending verification emails.
 * @author ManhNC
 */
public class TokenServiceImpl implements TokenService {

    private final TokenDAO tokenDao;
    private UserDAO userDao;
    /**
     * Constructor that initializes the TokenDAO.
     *
     * @param tokenDao The data access object used for token operations.
     */
    public TokenServiceImpl(TokenDAO tokenDao) {
        this.tokenDao = tokenDao;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Checks if a given token is valid and updates the verification status if necessary.
     *
     * @param tokenCode The token code provided by the user.
     * @param userID    The ID of the user whose token is being checked.
     * @return True if the token is valid, false otherwise.
     */
    @Override
    public boolean checkToken(String tokenCode, int userID, LocalDateTime requestedTime) throws RuntimeException, SQLException {
        // Attempt to find the token for the given userID
        Token token = tokenDao.findToken(userID);

        // Check if the token exists
        if (token == null) {
            return false; // Token not found
        }

        // Check if the provided tokenCode matches the stored token
        if (token.getToken().equals(tokenCode)) {
            // Calculate the time difference between time input and the requested time
            Duration duration = Duration.between(token.getRequestedTime(), requestedTime);
            // Check if the time difference is less than 5 minutes
            if (duration.toMinutes() < 1) {
                // If the token is not verified, update its status
                if (!token.isVerified()) {
                    tokenDao.updateTokenVerification(userID);
                }
                return true; // Token is valid within the 5-minute window
            } else {
                return false; // Token has expired (more than 5 minutes)
            }
        } else {
            return false; // Token code does not match
        }
    }

    /**
     * Sends a verification email to the user with a token link.
     *
     * @param email  The user's email address.
     * @param userId The ID of the user receiving the email.
     */
    @Override
    public void sendToken(String email, int userId) throws SQLException {
        Token oldToken = tokenDao.findToken(userId);
        String tokenCode;
        LocalDateTime requestedTime;
        tokenCode = SecureRandomCode.generateCode();
        requestedTime = LocalDateTime.now();
        if (oldToken != null) {
            tokenDao.updateToken(userId, tokenCode, requestedTime);
        } else {
            // Create a new token
            Token newToken = new Token(userId, tokenCode, requestedTime, false);
            tokenDao.insertToken(newToken);
        }
        // Get base URL from properties
        String subject = "Xac nhan email!!!";
        String content = "<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset='UTF-8'></head>"
                + "<body>"
                + "<h2>Xác nhận email</h2>"
                + "<p>Xin chào,</p>"
                + "<p>Đây là mã OTP để xác thực email của bạn. Mã này có hiệu lực trong vòng 5 phút.</p>"
                + "<p><strong>Mã OTP của bạn: " + tokenCode + "</strong></p>"
                + "<p>Lưu ý: Không chia sẻ mã này với bất kỳ ai.</p>"
                + "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>"
                + "</body>"
                + "</html>";
        try {
            SendingEmail.sendMail(email, subject, content);
        } catch (MessagingException e) {
            throw new GeneralException("Error while sending email");
        }
    }

    /**
     * ReSends a verification token to the user's email address.
     * The token is used to verify the user's email as part of the registration or email verification process.
     *
     * @param userId The ID of the user to whom the token belongs.
     */
    @Override
    public void reSendToken(int userId) throws SQLException {
        User user = userDao.getUser(userId);
        if (user == null) {
            throw new GeneralException("User not found");
        }
        sendToken(user.getEmail(), userId);
    }
}
