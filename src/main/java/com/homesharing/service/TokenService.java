package com.homesharing.service;

/**
 * Service interface for handling token-related operations such as
 * verifying and sending tokens for user email verification.
 */
public interface TokenService {

    /**
     * Checks whether the provided token is valid for the given user.
     *
     * @param token The token to be verified.
     * @param userId The ID of the user associated with the token.
     * @return True if the token is valid, false otherwise.
     */
    boolean checkToken(String token, int userId);

    /**
     * Sends a token to the user's email for verification purposes.
     *
     * @param email The email address to which the token will be sent.
     * @param userId The ID of the user to whom the token belongs.
     * @return True if the token was successfully sent, false otherwise.
     */
    boolean sendToken(String email, int userId);

}
