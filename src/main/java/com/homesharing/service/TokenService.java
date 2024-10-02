package com.homesharing.service;

/**
 * TokenService is an interface that defines the operations for handling tokens,
 * which are primarily used for email verification in the system.
 * It provides methods for checking the validity of tokens and sending tokens to users.
 *
 * @version 1.0
 * @since 2024-10-02
 */
public interface TokenService {

    /**
     * Checks the validity of a token associated with a specific user.
     * The token is used to verify a user's email address.
     *
     * @param token  The verification token to be checked.
     * @param userId The ID of the user to whom the token belongs.
     * @return true if the token is valid, false otherwise.
     */
    boolean checkToken(String token, int userId);

    /**
     * Sends a verification token to the user's email address.
     * The token is used to verify the user's email as part of the registration or email verification process.
     *
     * @param email  The email address to which the token should be sent.
     * @param userId The ID of the user to whom the token belongs.
     */
    void sendToken(String email, int userId);

}
