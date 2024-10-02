package com.homesharing.service;

import com.homesharing.model.User;
import jakarta.servlet.http.HttpServletResponse;


/**
 * UserService interface defines methods for user registration and validation.
 * It contains methods to register a user and validate user input data.
 */
public interface UserService {

    /**
     * Registers a new user with the provided details.
     *
     * @param firstName The user's first name.
     * @param lastName  The user's last name.
     * @param email     The user's email address.
     * @param password  The user's password.
     * @param role      The role of the user (e.g., findRoommate, postRoom).
     * @return A string indicating the result of the registration (e.g., success message, error).
     */
    String registerUser(String firstName, String lastName, String email, String password, String role);

    /**
     * Validates the user input for registration.
     * Checks the first name and last name for valid characters, validates the email format,
     * ensures the password meets the length requirement and matches the confirm password,
     * and verifies the role is one of the accepted values.
     *
     * @param firstName       The user's first name.
     * @param lastName        The user's last name.
     * @param email           The user's email address.
     * @param password        The user's password.
     * @param confirmPassword The confirmation of the user's password.
     * @param role            The user's role (e.g., findRoommate, postRoom).
     * @return True if all inputs are valid, false otherwise.
     */
    boolean validateUserInput(String firstName, String lastName, String email, String password, String confirmPassword, String role);

    String login(String email, String password, boolean rememberMe, HttpServletResponse response);

    String logout(HttpServletResponse response);
    /**
     * Updates the user's profile with the provided details.
     *
     * @param userId          The user's ID.
     * @param firstName       The updated first name of the user.
     * @param lastName        The updated last name of the user.
     * @param address         The updated address of the user.
     * @param gender          The updated gender of the user.
     * @param dob             The updated date of birth of the user.
     * @param avatarFileName  The name of the updated avatar file.
     * @return An integer indicating the result of the update operation (e.g., number of rows affected).
     */
    int updateUserProfile(String userId, String firstName, String lastName, String address, String gender, String dob, String avatarFileName);
    /**
     * Retrieves the user details based on the provided user ID.
     *
     * @param userId The ID of the user to be retrieved.
     * @return The User object containing the user's details, or null if not found.
     */
    User getUser(int userId);
    /**
     * Resets the password for a user with the given ID.
     *
     * @param userId The ID of the user whose password is to be reset.
     * @param newPassword The new password to be set for the user.
     * @return The number of rows affected by the password reset (1 if successful, 0 if unsuccessful).
     */
    int resetUserPassword(int userId, String newPassword);
}
