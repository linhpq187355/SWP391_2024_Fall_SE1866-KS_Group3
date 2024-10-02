package com.homesharing.service.impl;

import com.homesharing.conf.Config;
import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.ForgotPasswordService;
import com.homesharing.util.SendingEmail;
import jakarta.mail.MessagingException;

/**
 * Implementation of the ForgotPasswordService.
 * This class handles the logic for sending password reset tokens via email.
 */
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserDAO userDao;  // DAO for accessing User data
    private final TokenDAO tokenDao;  // DAO for accessing Token data

    /**
     * Constructor for ForgotPasswordServiceImpl.
     * Initializes the UserDAO and TokenDAO.
     *
     * @param userDao the data access object for users
     * @param tokenDao the data access object for tokens
     */
    public ForgotPasswordServiceImpl(UserDAO userDao, TokenDAO tokenDao) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
    }

    /**
     * Sends a password reset token to the user's email if the email is registered.
     *
     * @param email the email address of the user
     * @return true if the email was sent successfully, false otherwise
     * @throws GeneralException if there is an error sending the email
     */
    @Override
    public boolean sendResetPasswordToken(String email) {
        User user = userDao.findUserByEmail(email);  // Find user by email
        if (user != null) {
            Token oldToken = tokenDao.findToken(user.getId());  // Retrieve the existing token for the user
            String tokenCode = oldToken.getToken();  // Get the token code

            // Retrieve base URL from configuration file
            String baseUrl = Config.getBaseUrl();
            String subject = "Xác nhận email";
            String content = "Click vào link sau để đặt lại mật khẩu: "
                    + baseUrl + "/reset-password?code=" + tokenCode
                    + "&userId=" + user.getId();

            try {
                // Attempt to send the reset email
                SendingEmail.sendMail(email, subject, content);
                return true;
            } catch (MessagingException e) {
                // Handle any errors that occur during email sending
                throw new GeneralException("Error while sending email");
            }
        }
        // Return false if no user is found with the provided email
        return false;
    }
}