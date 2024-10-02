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

/**
 * Implementation of the TokenService interface that handles email verification token-related logic.
 */
public class TokenServiceImpl implements TokenService {

    private final TokenDAO tokenDao;
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    /**
     * Constructor that initializes the TokenDAO.
     *
     * @param tokenDao The data access object used for token operations.
     */
    public TokenServiceImpl(TokenDAO tokenDao) {
        this.tokenDao = tokenDao;
    }

    /**
     * Checks if a given token is valid and updates the verification status if necessary.
     *
     * @param tokenCode The token code provided by the user.
     * @param userID    The ID of the user whose token is being checked.
     * @return True if the token is valid, false otherwise.
     */
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

    /**
     * Sends a verification email to the user with a token link.
     *
     * @param email  The user's email address.
     * @param userId The ID of the user receiving the email.
     */
    @Override
    public void sendToken(String email, int userId) {
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
        String subject = "Xac nhan email!!!";
        String content = "<!DOCTYPE html>"
                + "<html>"
                + "<head><meta charset='UTF-8'></head>"
                + "<body>"
                + "<h2>Xác nhận email</h2>"
                + "<p>Xin chào,</p>"
                + "<p>Vui lòng nhấn vào nút bên dưới để xác thực email của bạn:</p>"
                + "<a href='" + baseUrl + "/verify?code=" + tokenCode + "&userId=" + userId + "' "
                + "style='text-decoration: none;'>"
                + "<button style='padding: 10px 20px; background-color: blue; color: white; border: none;'>"
                + "Xác thực Email"
                + "</button>"
                + "</a>"
                + "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>"
                + "</body>"
                + "</html>";
        try {
            SendingEmail.sendMail(email, subject, content);
        } catch (MessagingException e) {
            throw new GeneralException("Error while sending email");
        }
    }
}
