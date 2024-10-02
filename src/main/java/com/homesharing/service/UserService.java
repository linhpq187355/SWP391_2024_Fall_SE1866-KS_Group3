package com.homesharing.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

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

    String loginStaff(String email, String password, HttpServletResponse response);

    String logout(HttpServletResponse response);

}
