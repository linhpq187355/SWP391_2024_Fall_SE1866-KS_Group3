package com.homesharing.service;

import java.sql.SQLException;

/**
 * Interface for the forgot password functionality.
 * Provides a method to send a reset password token to a user's email.
 */
public interface ForgotPasswordService {

    /**
     * Sends a reset password token to the provided email address.
     *
     * @param email the email address of the user requesting a password reset
     * @return true if the email exists and the token was sent successfully, false otherwise
     */
    boolean sendResetPasswordToken(String email) throws SQLException;
}